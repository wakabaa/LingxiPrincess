package com.gbf.kukuru.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gbf.kukuru.service.gbf.impl.UniteAndFightInfoServiceImpl;
import com.gbf.kukuru.service.qq.impl.BlockQqGroupServiceImpl;
import com.gbf.kukuru.util.SendMsgUtils;

import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.utils.Msg;

@Slf4j
@Component
@EnableScheduling
public class ScheduleService {

    @Resource
    private SendMsgUtils sendMsgUtils;
    @Resource
    private UniteAndFightInfoServiceImpl uniteAndFightInfoService;
    @Resource
    private BlockQqGroupServiceImpl blockQqGroupService;

    private void sendGroupMessage(String message) throws InterruptedException {
        List<Long> groupIdList = sendMsgUtils.getGroupList();
        if (groupIdList != null && !groupIdList.isEmpty()) {
            for (long groupId : groupIdList) {
                sendMsgUtils.sendGroupMsgForMsg(groupId, Msg.builder().text(message));
            }
        }
    }

    private boolean isNthWeekOfMonth(int n) {
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfMonth = today.get(weekFields.weekOfMonth());
        return weekOfMonth == n;
    }

    @Scheduled(cron = "0 30 14 ? * SAT")
    public void xiangyaofumo() throws InterruptedException {
        sendGroupMessage("降妖伏魔活动即将开始。");
    }

    @Scheduled(cron = "0 30 14 ? * SUN")
    public void menpaichuangguan() throws InterruptedException {
        if (isNthWeekOfMonth(1)) {
            sendGroupMessage("门派闯关活动即将开始。");
        }
    }

    @Scheduled(cron = "0 30 11 ? * SUN")
    public void kejudasai() throws InterruptedException {
        if (isNthWeekOfMonth(2)) {
            sendGroupMessage("科举大赛活动即将开始。");
        }
    }
    
    @Scheduled(cron = "0 00 13 ? * SUN")
    public void wenyunmoxiang() throws InterruptedException {
        if (isNthWeekOfMonth(2)) {
            sendGroupMessage("文韵墨香活动即将开始。");
        }
    }

    @Scheduled(cron = "0 30 14 ? * SUN")
    public void caihongzhengbasai() throws InterruptedException {
        if (isNthWeekOfMonth(3)) {
            sendGroupMessage("彩虹争霸赛活动即将开始。");
        }
    }

    @Scheduled(cron = "0 30 13 ? * SUN")
    public void changanbaoweizhan() throws InterruptedException {
        if (isNthWeekOfMonth(5)) {
            sendGroupMessage("长安保卫战活动即将开始。");
        }
    }

    @Scheduled(cron = "0 0 20 ? * FRI")
    public void bangzhanzhijianshe() throws InterruptedException {
        if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
            sendGroupMessage("帮战之建设活动即将开始。");
        }
    }

    @Scheduled(cron = "0 0 20 ? * SAT")
    public void bangzhanzhijingsai() throws InterruptedException {
        if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
            sendGroupMessage("帮战之竞赛活动即将开始。");
        }
    }

    @Scheduled(cron = "0 0 20 ? * SUN")
    public void bangzhanzhiduoqi() throws InterruptedException {
        if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
            sendGroupMessage("帮战之夺旗活动即将开始。");
        }
    }
    
	@Scheduled(cron = "0 30 14 ? * TUE")
	public void chunsemanyuan() throws InterruptedException {
		sendGroupMessage("春色满园活动即将开始。");
	}
}
