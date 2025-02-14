package com.gbf.kukuru.service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gbf.kukuru.util.PathUtils;
import com.gbf.kukuru.util.SendMsgUtils;
import com.mikuac.shiro.common.utils.MsgUtils;

@Component
@EnableScheduling
public class ScheduleService {

	@Autowired
	private SendMsgUtils sendMsgUtils;

    private static final LocalDate INITIAL_REFRESH_DATE = LocalDate.of(2024, 6, 3);
    
    private static final int REFRESH_INTERVAL_DAYS = 4; // 刷新间隔天数
    
	private void sendGroupMessage(String message) throws InterruptedException {
		List<Long> groupIdList = sendMsgUtils.getGroupList();
		if (groupIdList != null && !groupIdList.isEmpty()) {
			for (long groupId : groupIdList) {
				sendMsgUtils.sendGroupMsgForMsg(groupId, message);
			}
		}
	}

	private boolean isNthWeekOfMonth(int n) {
		Calendar calendar = Calendar.getInstance();
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		return weekOfMonth == n;
	}
	
	@Scheduled(cron = "0 0 20 * * *") // 每天晚上8点触发
    public void remindUsers() throws InterruptedException {
        LocalDate currentDate = LocalDate.now();
        long daysToNextRefresh = calculateDaysToNextRefresh(currentDate);
        String reminderMessage = generateReminderMessage(daysToNextRefresh);
        sendGroupMessage(reminderMessage);
    }
	
	@Scheduled(cron = "0 30 14 ? * SAT")
	public void xiangyaofumo() throws InterruptedException {

		String message = MsgUtils.builder().text("降妖伏魔活动即将开始。")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif")).build();
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 28 15 ? * SAT")
	public void qiannian1530() throws InterruptedException {
		String message = MsgUtils.builder().text("冲破符咒封印的妖魔怒了更厉害的千年魔灵和万年魔灵即将来到，各路英雄速速前往齐心协力共除妖魔!")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif")).build();
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 58 15 ? * SAT")
	public void qiannian1600() throws InterruptedException {
		String message = MsgUtils.builder().text("冲破符咒封印的妖魔怒了更厉害的千年魔灵和万年魔灵即将来到，各路英雄速速前往齐心协力共除妖魔!")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif")).build();
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 28 16 ? * SAT")
	public void qiannian1630() throws InterruptedException {
		String message = MsgUtils.builder().text("冲破符咒封印的妖魔怒了更厉害的千年魔灵和万年魔灵即将来到，各路英雄速速前往齐心协力共除妖魔!")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif")).build();
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 38 16 ? * SAT")
	public void qiannian1640() throws InterruptedException {
		String message = MsgUtils.builder().text("冲破符咒封印的妖魔怒了更厉害的千年魔灵和万年魔灵即将来到，各路英雄速速前往齐心协力共除妖魔!")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif")).build();
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 48 16 ? * SAT")
	public void qiannian1650() throws InterruptedException {
		String message = MsgUtils.builder().text("冲破符咒封印的妖魔怒了更厉害的千年魔灵和万年魔灵即将来到，各路英雄速速前往齐心协力共除妖魔!")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif")).build();
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 58 16 ? * SAT")
	public void qiannian1700() throws InterruptedException {
		String message = MsgUtils.builder().text("妖魔已经在做最后的挣扎，最后一批千年魔灵和万年灵即将来到，各路英雄速速前往齐心协力共除妖魔!")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif")).build();
		sendGroupMessage(message);
	}
	
	@Scheduled(cron = "0 30 19 ? * THU")
	public void fish() throws InterruptedException {

		String message = MsgUtils.builder().text("钓鱼大赛活动即将开始。")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif")).build();
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 30 14 ? * SUN")
	public void menpaichuangguan() throws InterruptedException {
		if (isNthWeekOfMonth(1)) {
			String message = MsgUtils.builder().text("门派闯关活动即将开始。")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif")).build();
			sendGroupMessage(message);
		}
	}
	
	@Scheduled(cron = "0 00 16 ? * SUN")
	public void menpaichuangguan1600() throws InterruptedException {
		if (isNthWeekOfMonth(1)) {
			String message = MsgUtils.builder().text("活动已经过半，少侠一定已经收获不少二药了吧。记得使用第二颗回梦丹")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/47.gif")).build();
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 30 11 ? * SUN")
	public void kejudasai() throws InterruptedException {
		if (isNthWeekOfMonth(2)) {
			String message = MsgUtils.builder().text("科举大赛活动即将开始。")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/104.gif")).build();
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 00 14 ? * SUN")
	public void wenyunmoxiang() throws InterruptedException {
		if (isNthWeekOfMonth(2)) {
			String message = MsgUtils.builder().text("文韵墨香活动即将开始。")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/42.gif")).build();
			sendGroupMessage(message);
		}
	}
	
	@Scheduled(cron = "0 00 16 ? * SUN")
	public void wenyunmoxiang1600() throws InterruptedException {
		if (isNthWeekOfMonth(2)) {
			String message = MsgUtils.builder().text("活动已经过半，少侠包裹里一定是空空的吧。记得使用第二颗回梦丹")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/47.gif")).build();
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 30 14 ? * SUN")
	public void caihongzhengbasai() throws InterruptedException {
		if (isNthWeekOfMonth(3)) {
			if (isNthWeekOfMonth(2)) {
				String message = MsgUtils.builder().text("彩虹争霸赛活动即将开始。")
						.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/90.gif")).build();
				sendGroupMessage(message);
			}
		}
	}

	@Scheduled(cron = "0 30 13 ? * SUN")
	public void changanbaoweizhan() throws InterruptedException {
		if (isNthWeekOfMonth(5)) {
			if (isNthWeekOfMonth(2)) {
				String message = MsgUtils.builder().text("长安保卫战即将开始。")
						.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/101.gif")).build();
				sendGroupMessage(message);
			}
		}
	}

	@Scheduled(cron = "0 0 20 ? * FRI")
	public void bangzhanzhijianshe() throws InterruptedException {
		if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
			String message = MsgUtils.builder().text("帮战之建设活动即将开始。")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif")).build();
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 0 20 ? * SAT")
	public void bangzhanzhijingsai() throws InterruptedException {
		if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
			String message = MsgUtils.builder().text("帮战之竞赛活动即将开始。")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif")).build();
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 0 20 ? * SUN")
	public void bangzhanzhiduoqi() throws InterruptedException {
		if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
			String message = MsgUtils.builder().text("帮战之夺旗活动即将开始。")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif")).build();
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 30 14 ? * TUE")
	public void chunsemanyuan() throws InterruptedException {
		String message = MsgUtils.builder().text("春色满园活动即将开始。")
				.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/42.gif")).build();
		sendGroupMessage(message);
	}
	
    public long calculateDaysToNextRefresh(LocalDate currentDate) {
        long daysSinceInitialRefresh = currentDate.toEpochDay() - INITIAL_REFRESH_DATE.toEpochDay();
        return REFRESH_INTERVAL_DAYS - (daysSinceInitialRefresh % REFRESH_INTERVAL_DAYS);
    }
    
	public String generateReminderMessage(long daysToNextRefresh) {
		if (daysToNextRefresh == REFRESH_INTERVAL_DAYS) {
			return MsgUtils.builder().text("副本已经刷新")
					.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/50.gif")).build();
		} else {
			// 根据剩余天数定制消息内容
			if (daysToNextRefresh == 1) {
				return MsgUtils.builder().text("副本明天就解刷新了")
						.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/51.gif")).build();
			} else {
				return MsgUtils.builder().text(String.format("副本还差%d天刷新了", daysToNextRefresh - 1))
						.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/99.gif")).build();
			}
		}
	}
}
