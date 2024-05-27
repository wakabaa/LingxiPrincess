package com.gbf.kukuru.plugin;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gbf.kukuru.constant.CommandConstant;
import com.gbf.kukuru.service.image.IImageService;
import com.gbf.kukuru.service.image.impl.PcrDivinationImageServiceImpl;
import com.gbf.kukuru.util.CommandUtils;
import com.gbf.kukuru.util.ImgUtils;
import com.gbf.kukuru.util.RedisUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.skija.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PcrPlugin extends BotPlugin {

    /**
     * 获取现在距离明天0点的毫秒数
     *
     * @return 毫秒数
     */
    private long getDurationFromNowToTomorrow() {
        Date now = new Date();
        DateTime tomorrow = DateUtil.tomorrow();
        tomorrow.setField(DateField.HOUR, 0);
        tomorrow.setField(DateField.HOUR_OF_DAY, 0);
        tomorrow.setField(DateField.MINUTE, 0);
        tomorrow.setField(DateField.MILLISECOND, 0);
        tomorrow.setField(DateField.SECOND, 0);
        return tomorrow.getTime() - now.getTime();
    }

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        String msg = event.getRawMessage();
        long userId = event.getUserId();
        if (CommandUtils.isCommand(msg, CommandConstant.COMMAND_PCR_DIVINATION)) {
            String redisKey = userId + "::DAILY_LUCK";
            if (StrUtil.isNotBlank(RedisUtils.get(redisKey))) {
                bot.sendPrivateMsg(
                        userId,
                        Msg.builder().text("今天已经抽过了，明天再来吧~"),
                        false
                );
            } else {
                RedisUtils.setEx(redisKey, "1", getDurationFromNowToTomorrow());
                PcrDivinationImageServiceImpl template = new PcrDivinationImageServiceImpl(userId);
                try (Data pngData = template.generateImage(IImageService.DEFAULT_FORMAT)) {
                    bot.sendPrivateMsg(
                            userId,
                            Msg.builder()
                                    .image(ImgUtils.saveImage(pngData.getBytes(), IImageService.DEFAULT_FORMAT)),
                            false
                    );
                }
            }
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        String msg = event.getRawMessage();
        long userId = event.getUserId();
        long groupId = event.getGroupId();
        if (CommandUtils.isCommand(msg, CommandConstant.COMMAND_PCR_DIVINATION)) {
            String redisKey = userId + "::DAILY_LUCK";
            if (StrUtil.isNotBlank(RedisUtils.get(redisKey))) {
                bot.sendGroupMsg(
                        groupId,
                        Msg.builder().at(userId).text("今天已经抽过了，明天再来吧~"),
                        false
                );
            } else {
                RedisUtils.setEx(redisKey, "1", getDurationFromNowToTomorrow());
                PcrDivinationImageServiceImpl template = new PcrDivinationImageServiceImpl(userId);
                try (Data pngData = template.generateImage(IImageService.DEFAULT_FORMAT)) {
                    bot.sendGroupMsg(
                            groupId,
                            Msg.builder()
                                    .at(userId)
                                    .image(ImgUtils.saveImage(pngData.getBytes(), IImageService.DEFAULT_FORMAT)),
                            false
                    );
                }
            }
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }
}
