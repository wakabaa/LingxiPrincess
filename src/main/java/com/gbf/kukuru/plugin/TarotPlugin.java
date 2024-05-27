package com.gbf.kukuru.plugin;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.gbf.kukuru.constant.CommandConstant;
import com.gbf.kukuru.entity.Tarot;
import com.gbf.kukuru.entity.TarotAlgorithm;
import com.gbf.kukuru.util.CommandUtils;
import com.gbf.kukuru.util.PathUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class TarotPlugin extends BotPlugin {

    private List<Msg> buildTarotMessageList(long userId) {
        List<Msg> msgList = new ArrayList<>();
        JSONArray tarotJsonArray = JSONUtil.readJSONArray(
                new File(PathUtils.getRealPathFromResource("/tarot.json")),
                Charset.defaultCharset()
        );
        JSONArray tarotAlgorithmJsonArray = JSONUtil.readJSONArray(
                new File(PathUtils.getRealPathFromResource("/tarotAlgorithm.json")),
                Charset.defaultCharset()
        );
        List<Tarot> tarotList = tarotJsonArray.toList(Tarot.class);
        for (TarotAlgorithm item : tarotAlgorithmJsonArray.toList(TarotAlgorithm.class)) {
            Tarot tarot = tarotList.get(RandomUtil.randomInt(tarotList.size()));
            Msg message;
            if (item.isSupportReverse() && StrUtil.isNotBlank(tarot.getReverseMeaning())) {
                /* 如果支持逆位且有逆位, 则随机选取位序 */
                if (RandomUtil.randomBoolean()) {
                    message = Msg.builder()
                            .at(userId)
                            .text(item.getPosition() + ": " + item.getMeaning()
                                    + "\n"
                                    + tarot.getName() + "[逆位]: " + tarot.getReverseMeaning()
                            )
                            .image(PathUtils.getRealPathFromResource("static/img/arcana/reverse/" + tarot.getName() + ".jpg"));
                } else {
                    message = Msg.builder()
                            .at(userId)
                            .text(item.getPosition() + ": " + item.getMeaning()
                                    + "\n"
                                    + tarot.getName() + "[正位]: " + tarot.getMeaning()
                            )
                            .image(PathUtils.getRealPathFromResource("static/img/arcana/obverse/" + tarot.getName() + ".jpg"));
                }
            } else {
                /* 默认选取正位 */
                message = Msg.builder()
                        .at(userId)
                        .text(item.getPosition() + ": " + item.getMeaning()
                                + "\n"
                                + tarot.getName() + ": " + tarot.getMeaning()
                        )
                        .image(PathUtils.getRealPathFromResource("static/img/arcana/obverse/" + tarot.getName() + ".jpg"));
            }
            msgList.add(message);
        }
        return msgList;
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        String msg = event.getRawMessage();
        long userId = event.getUserId();
        long groupId = event.getGroupId();
        if (CommandUtils.isCommand(msg, CommandConstant.COMMAND_ARCANA_DIVINATION)) {
            for (Msg message : buildTarotMessageList(userId)) {
                bot.sendGroupMsg(groupId, message, false);
                ThreadUtil.sleep(1000);
            }
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onPrivateMessage(@NotNull Bot bot, OnebotEvent.PrivateMessageEvent event) {
        String msg = event.getRawMessage();
        long userId = event.getUserId();
        if (CommandUtils.isCommand(msg, CommandConstant.COMMAND_ARCANA_DIVINATION)) {
            for (Msg message : buildTarotMessageList(userId)) {
                bot.sendPrivateMsg(userId, message, false);
                ThreadUtil.sleep(1000);
            }
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }
}
