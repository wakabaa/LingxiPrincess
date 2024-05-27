package com.gbf.kukuru.service.bilibili.dynamic.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gbf.kukuru.code.KukuruExceptionCode;
import com.gbf.kukuru.entity.BilibiliDynamic;
import com.gbf.kukuru.entity.BilibiliDynamicAdditional;
import com.gbf.kukuru.entity.BilibiliDynamicRichTextNode;
import com.gbf.kukuru.exception.KukuruException;
import com.gbf.kukuru.service.bilibili.dynamic.IBilibiliDynamicForwardService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * B站动态转发功能 服务实现类
 *
 * @author ginoko
 * @since 2022-05-11
 */
@Component
public class BilibiliDynamicForwardServiceImpl implements IBilibiliDynamicForwardService {

    /**
     * 构造获取动态信息的URL
     *
     * @param uid    B站用户ID
     * @param offset 动态ID偏移量 (用来分页)
     * @return url字符串
     */
    private String buildUrl(Long uid, Long offset) {
        if (offset == null || offset < 0L) {
            offset = 0L;
        }
        return "https://api.bilibili.com/x/polymer/web-dynamic/v1/feed/space?" +
                "offset=" + (offset == 0L ? "" : offset) +
                "&host_mid=" + uid;
    }

    private JSONObject getDynamicList(Long uid, Long offset) {
        String result = HttpUtil.get(this.buildUrl(uid, offset), 5000);
        return JSONUtil.parseObj(result);
    }

    /**
     * 从动态JSON中获取图片链接列表
     *
     * @param dynamic 动态JSON
     * @return 图片链接列表
     */
    private List<String> getImageList(JSONObject dynamic) {
        if (!Objects.equals(dynamic.getStr("type"), "DYNAMIC_TYPE_DRAW")) {
            throw new KukuruException(KukuruExceptionCode.UNEXPECT_BILIBILI_DYNAMIC_TYPE,
                    "[期望(DYNAMIC_TYPE_DRAW) 获得(" + dynamic.getStr("type") + ")]");
        }
        List<String> imageList = new ArrayList<>();
        for (JSONObject image : dynamic.getByPath("modules.module_dynamic.major.draw.items", JSONArray.class).jsonIter()) {
            imageList.add(image.getStr("src"));
        }
        return imageList;
    }

    /**
     * 根据富文本json数组构建富文本列表
     *
     * @param richTextNodeJsonArray 富文本json数组
     * @return 富文本列表
     */
    private List<BilibiliDynamicRichTextNode> buildRichTextNode(JSONArray richTextNodeJsonArray) {
        List<BilibiliDynamicRichTextNode> richTextNodeList = new ArrayList<>();
        richTextNodeJsonArray.forEach(obj -> {
            JSONObject richTextNodeJson = JSONUtil.parseObj(obj);
            BilibiliDynamicRichTextNode richTextNode = new BilibiliDynamicRichTextNode();
            richTextNode.setType(richTextNodeJson.getStr("type"));
            richTextNode.setOrigText(richTextNodeJson.getStr("orig_text").replace("\r\n", "\n"));
            richTextNode.setText(richTextNodeJson.getStr("text").replace("\r\n", "\n"));
            if (Objects.equals(richTextNode.getType(), "RICH_TEXT_NODE_TYPE_EMOJI")) {
                richTextNode.setEmoji(richTextNodeJson.getByPath("emoji.icon_url", String.class));
            } else if (Objects.equals(richTextNode.getType(), "RICH_TEXT_NODE_TYPE_VOTE")) {
                richTextNode.setRid(richTextNodeJson.getStr("rid"));
            }
            richTextNodeList.add(richTextNode);
        });
        return richTextNodeList;
    }

    /**
     * 根据附加相关内容json构建附加相关内容实体
     *
     * @param additionalJson 附加相关内容json
     * @return 附加相关内容实体
     */
    private BilibiliDynamicAdditional buildAdditional(JSONObject additionalJson) {
        BilibiliDynamicAdditional additional = new BilibiliDynamicAdditional();
        additional.setType(additionalJson.getStr("type"));
        if (Objects.equals(additional.getType(), "ADDITIONAL_TYPE_COMMON")) {
            /* 目前只解析通用类型 */
            additional.setCover(additionalJson.getByPath("common.cover", String.class));
            additional.setDesc1(additionalJson.getByPath("common.desc1", String.class));
            additional.setDesc2(additionalJson.getByPath("common.desc2", String.class));
            additional.setHead(additionalJson.getByPath("common.head_text", String.class));
            additional.setTitle(additionalJson.getByPath("common.title", String.class));
        }
        return additional;
    }

    /**
     * 构建动态对象
     *
     * @param dynamicJson 动态JSON
     * @return 动态对象
     */
    private BilibiliDynamic buildDynamic(JSONObject dynamicJson) {
        BilibiliDynamic dynamic = new BilibiliDynamic();
        /* 动态总体相关 */
        dynamic.setId(dynamicJson.getLong("id_str"));
        dynamic.setType(dynamicJson.getStr("type"));
        dynamic.setPublishTime(dynamicJson.getByPath("modules.module_author.pub_ts", Long.class));
        /* 动态作者相关 */
        dynamic.setAuthorUid(dynamicJson.getByPath("modules.module_author.mid", Long.class));
        dynamic.setAuthor(dynamicJson.getByPath("modules.module_author.name", String.class));
        dynamic.setAuthorAvatar(dynamicJson.getByPath("modules.module_author.face", String.class));
        dynamic.setAuthorPendant(dynamicJson.getByPath("modules.module_author.pendant.image", String.class));
        /* 动态内容相关 */
        switch (dynamic.getType()) {
            case "DYNAMIC_TYPE_DRAW":
                dynamic.setImages(getImageList(dynamicJson));
                dynamic.setRichTextNodeList(buildRichTextNode(dynamicJson.getByPath("modules.module_dynamic.desc.rich_text_nodes", JSONArray.class)));
                break;
            case "DYNAMIC_TYPE_WORD":
                dynamic.setRichTextNodeList(buildRichTextNode(dynamicJson.getByPath("modules.module_dynamic.desc.rich_text_nodes", JSONArray.class)));
                break;
            case "DYNAMIC_TYPE_AV":
                dynamic.setVideoTitle(dynamicJson.getByPath("modules.module_dynamic.major.archive.title", String.class));
                dynamic.setVideoLink(dynamicJson.getByPath("modules.module_dynamic.major.archive.jump_url", String.class));
                dynamic.setVideoCover(dynamicJson.getByPath("modules.module_dynamic.major.archive.cover", String.class));
                dynamic.setVideoDesc(dynamicJson.getByPath("modules.module_dynamic.major.archive.desc", String.class));
                break;
            case "DYNAMIC_TYPE_FORWARD":
                dynamic.setRichTextNodeList(buildRichTextNode(dynamicJson.getByPath("modules.module_dynamic.desc.rich_text_nodes", JSONArray.class)));
                dynamic.setOrigin(buildDynamic(dynamicJson.getByPath("orig", JSONObject.class)));
                break;
            case "DYNAMIC_TYPE_LIVE_RCMD":
                /* 直播动态目前不做处理 */
                break;
            default:
                throw new KukuruException(KukuruExceptionCode.UNKNOWN_BILIBILI_DYNAMIC_TYPE, "[" + dynamic.getType() + "]");
        }
        /* 动态附加信息相关 */
        JSONObject additionalJson = dynamicJson.getByPath("modules.module_dynamic.additional", JSONObject.class);
        if (!ObjectUtils.isEmpty(additionalJson)) {
            dynamic.setAdditional(buildAdditional(additionalJson));
        }
        /* 动态话题相关 */
        JSONObject topicJson = dynamicJson.getByPath("modules.module_dynamic.topic", JSONObject.class);
        if (!ObjectUtils.isEmpty(topicJson)) {
            dynamic.setTopic(topicJson.getStr("name"));
        }
        /* 动态状态相关 */
        if (dynamicJson.getByPath("modules.module_stat", JSONObject.class) != null) {
            /* 转发动态的原动态没有状态 */
            dynamic.setCommentCount(dynamicJson.getByPath("modules.module_stat.comment.count", Integer.class));
            dynamic.setForwardCount(dynamicJson.getByPath("modules.module_stat.forward.count", Integer.class));
            dynamic.setLikeCount(dynamicJson.getByPath("modules.module_stat.like.count", Integer.class));
        }
        return dynamic;
    }

    /**
     * 该动态是否忽略
     * <pre>
     * 忽略条件:
     *     置顶的动态
     *         或
     *     直播动态
     * </pre>
     *
     * @param dynamicJson 动态JSON
     * @return true 忽略; false 不忽略
     */
    private boolean isIgnoreDynamic(JSONObject dynamicJson) {
        return Objects.equals(dynamicJson.getByPath("modules.module_tag.text", String.class), "置顶")
                || Objects.equals(dynamicJson.getStr("type"), "DYNAMIC_TYPE_LIVE_RCMD");
    }

    @Override
    public BilibiliDynamic getNewestDynamic(Long uid) {
        JSONArray dynamicList = getDynamicList(uid, 0L).getByPath("data.items", JSONArray.class);
        BilibiliDynamic newestDynamic = null;
        for (JSONObject dynamicJson : dynamicList.jsonIter()) {
            if (isIgnoreDynamic(dynamicJson)) {
                continue;
            }
            newestDynamic = buildDynamic(dynamicJson);
            break;
        }
        return newestDynamic;
    }

    @Override
    public BilibiliDynamic getNextDynamic(Long uid, Long offset) {
        JSONArray dynamicList = getDynamicList(uid, offset).getByPath("data.items", JSONArray.class);
        BilibiliDynamic newestDynamic = null;
        for (JSONObject dynamicJson : dynamicList.jsonIter()) {
            if (isIgnoreDynamic(dynamicJson)) {
                continue;
            }
            newestDynamic = buildDynamic(dynamicJson);
            break;
        }
        return newestDynamic;
    }
}
