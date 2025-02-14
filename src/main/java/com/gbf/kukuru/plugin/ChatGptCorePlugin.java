package com.gbf.kukuru.plugin;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.MessageHandlerFilter;
import com.mikuac.shiro.annotation.PrivateMessageHandler;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import com.mikuac.shiro.enums.AtEnum;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Shiro
@Component
public class ChatGptCorePlugin {
	// 每个群最多保存20条消息记录
	private static final int MAX_HISTORY_SIZE = 20;
	// DEEPSEEK API
	private static final String HOST = "https://api.deepseek.com/chat/completions";
	// 硅基 API (备用)
	private static final String HOST_SUB = "https://api.siliconflow.cn/v1/chat/completions";
	// DEEPSEEK apiKey
	private String apiKey = "sk-e358c77f0cf148ecbf1d93de6ae90778";
	// 群聊功能支持指定群
	private static final List<Long> SUPPORTED_GROUP_IDS = Arrays.asList(609306220L);
	// 回复频率限制（单位毫秒），例如1分钟回复一次
	private static final long FREQUENCY_THRESHOLD = 60 * 1000;
	// 用一个Map来管理不同群的聊天记录
	private Map<Long, List<JSONObject>> groupMessagesHistory = new HashMap<>();
	// 记录各个群最后一次回复的时间
	private Map<Long, Long> lastReplyTime = new HashMap<>();

	private static final OkHttpClient CLIENT = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
			.readTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).callTimeout(120, TimeUnit.SECONDS)
			.build();

	// 用于初始化系统消息
	private void initializeSystemMessage(long groupId) {
	    if (!groupMessagesHistory.containsKey(groupId)) {
	        groupMessagesHistory.put(groupId, new ArrayList<>());
	    }
	    // 系统消息不存储在 groupMessagesHistory 中，初始化时仅需确保群聊记录的存在
	    // 不需要将 system 消息加入其中，因为我们会在 chat 方法中动态加入
	}

	// 修改 chat 方法，动态添加系统消息
	public String chat(String question, long userId, long groupId) {
	    // 确保群的聊天记录已初始化
	    if (!groupMessagesHistory.containsKey(groupId)) {
	        groupMessagesHistory.put(groupId, new ArrayList<>());
	    }

	    // 获取该群的聊天记录
	    List<JSONObject> messagesHistory = groupMessagesHistory.get(groupId);

	    // 记录群友的消息
	    messagesHistory.add(new JSONObject().put("content", question).put("role", "user").put("userId", userId));

	    // 控制消息记录的最大条数，超出则删除最旧的消息
	    if (messagesHistory.size() > MAX_HISTORY_SIZE) {
	        messagesHistory.remove(0);
	    }

	    // 构建请求消息
	    List<JSONObject> requestMessages = new ArrayList<>();

	    // 在请求时动态添加系统消息
	    requestMessages.add(new JSONObject()
	            .put("content", "我希望你表现得像明日方舟中的铃兰。我希望你像铃兰一样回应和回答。不要写任何解释。必须以铃兰的语气为基础。并且有权选择是否参与回复群消息。"
	                    + "如果觉得当前内容不需要参与讨论或者为<img>，请回复“[不回复]”，否则请正常回复。之前的消息也可以回复，请把握话题的主体。"
	                    + "")
	            .put("role", "system"));

	    // 将用户消息和助手回复添加到请求消息
	    requestMessages.addAll(messagesHistory);

	    // 构建请求体
	    MediaType mediaType = MediaType.parse("application/json");
	    String jsonBody = new JSONObject()
	            .put("messages", new JSONArray(requestMessages))
	            .put("model", "deepseek-chat")
	            .put("frequency_penalty", 0)
	            .put("max_tokens", 512)
	            .put("presence_penalty", 0)
	            .put("response_format", new JSONObject().put("type", "text"))
	            .put("stop", JSONObject.NULL)
	            .put("stream", false)
	            .put("temperature", 1.8)
	            .put("top_p", 1)
	            .toString();

	    RequestBody body = RequestBody.create(mediaType, jsonBody);
	    try {
	        Request request = new Request.Builder()
	                .url(HOST)
	                .method("POST", body)
	                .addHeader("Content-Type", "application/json")
	                .addHeader("Accept", "application/json")
	                .addHeader("Authorization", "Bearer " + apiKey)
	                .build();

	        System.out.println("思考中...."); // 添加日志，表示正在等待接口回应

	        try (Response response = CLIENT.newCall(request).execute()) {
	            if (response.isSuccessful() && response.body() != null) {
	                String content = response.body().string();
	                JSONObject jsonResponse = new JSONObject(content);
	                String assistantResponse = jsonResponse.getJSONArray("choices").getJSONObject(0)
	                        .getJSONObject("message").getString("content");

	                // 检查助手的回复是否为[不回复]
	                if (assistantResponse.trim().startsWith("[不回复]")) {
	                    System.out.println("助手回复：[不回复]"); // 添加日志，表示助手不想参与回复
	                    return assistantResponse;
	                }

	                // 将助手的回复添加到消息历史中
	                messagesHistory.add(new JSONObject().put("content", assistantResponse).put("role", "assistant"));
	                if (messagesHistory.size() > MAX_HISTORY_SIZE) {
	                    messagesHistory.remove(0);
	                }
	                return assistantResponse;
	            } else {
	                System.err.println("请求失败，请稍后再试。 Response code: " + response.code());
	                return "";
	            }
	        }
	    } catch (SocketTimeoutException e) {
	        System.err.println("请求超时，请稍后再试。 Exception: " + e.getMessage());
	        return "";
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("法宝言灵珠灵气已耗尽，请尽快为法宝补充灵气。 Exception: " + e.getMessage());
	        return "";
	    }
	}

	// 群消息处理入口
	@GroupMessageHandler
	public void onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
	    long groupId = event.getGroupId();
	    long userId = event.getUserId();
	    String msg = event.getRawMessage();

	    // 如果当前群号不在支持列表，则不处理消息
	    if (!SUPPORTED_GROUP_IDS.contains(groupId)) {
	        return;
	    }

	    // 判断回复频率：如果上次回复距离现在不足阈值，则仅记录消息，不回复
	    long now = System.currentTimeMillis();
	    if (lastReplyTime.containsKey(groupId) && (now - lastReplyTime.get(groupId) < FREQUENCY_THRESHOLD)) {
	        // 确保群消息记录已初始化，并记录该条消息
	        if (!groupMessagesHistory.containsKey(groupId)) {
	            initializeSystemMessage(groupId);
	        }

	        List<JSONObject> messagesHistory = groupMessagesHistory.get(groupId);

	        // 记录消息时，不删除 system 消息
	        messagesHistory.add(new JSONObject().put("content", msg).put("role", "user"));

	        // 控制消息记录的最大条数，超出则删除最旧的非 system 消息
	        if (messagesHistory.size() > MAX_HISTORY_SIZE) {
	            // 删除最旧的非 system 消息
	            while (messagesHistory.size() > MAX_HISTORY_SIZE) {
	                messagesHistory.remove(0);
	            }
	        }
	        return;
	    }

	    // 更新最后回复时间
	    lastReplyTime.put(groupId, now);

	    // 调用 chat 方法获取 AI 的回复
	    String chatResponse = chat(msg, userId, groupId);

	    // 根据 AI 的回复格式判断是否参与回复
	    if (chatResponse.trim().startsWith("[不回复]")) {
	        return;
	    }

	    // 否则，构建并发送回复消息
	    String sendMsg = MsgUtils.builder().text(chatResponse).build();
	    if (!chatResponse.isBlank()) {
	        bot.sendGroupMsg(groupId, sendMsg, false);
	    }
	}

//	@GroupMessageHandler
//	@MessageHandlerFilter(at = AtEnum.NEED)
//	public void onGroupMessageAt(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
//		String msg = event.getRawMessage();
//		long groupId = event.getGroupId();
//		long userId = event.getUserId();
//		String chatResponse = chat(msg, userId, groupId);
//		String sendMsg = MsgUtils.builder().text(chatResponse).build();
//		bot.sendGroupMsg(groupId, sendMsg, false);
//	}

	@PrivateMessageHandler
	public void onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
		String msg = event.getRawMessage();
		long userId = event.getUserId();
		String chatResponse = "暂时不支持私聊";
		String sendMsg = MsgUtils.builder().text(chatResponse).build();
		bot.sendPrivateMsg(userId, sendMsg, false);
	}
}
