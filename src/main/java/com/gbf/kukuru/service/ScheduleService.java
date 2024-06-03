package com.gbf.kukuru.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gbf.kukuru.service.gbf.impl.UniteAndFightInfoServiceImpl;
import com.gbf.kukuru.service.qq.impl.BlockQqGroupServiceImpl;
import com.gbf.kukuru.util.PathUtils;
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

	private void sendGroupMessage(Msg message) throws InterruptedException {
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

	@Scheduled(cron = "0 30 14 ? * SAT")
	public void xiangyaofumo() throws InterruptedException {

		Msg message = Msg.builder().text("降妖伏魔活动即将开始。")
				.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif"));
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 28 15 ? * SAT")
	public void qiannian1530() throws InterruptedException {
		Msg message = Msg.builder().text("千年魔灵和万年魔灵即将现身开始。")
				.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif"));
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 58 15 ? * SAT")
	public void qiannian1600() throws InterruptedException {
		Msg message = Msg.builder().text("千年魔灵和万年魔灵即将现身开始。")
				.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif"));
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 28 16 ? * SAT")
	public void qiannian1630() throws InterruptedException {
		Msg message = Msg.builder().text("千年魔灵和万年魔灵即将现身开始。")
				.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif"));
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 38 16 ? * SAT")
	public void qiannian1640() throws InterruptedException {
		Msg message = Msg.builder().text("千年魔灵和万年魔灵即将现身开始。")
				.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif"));
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 48 16 ? * SAT")
	public void qiannian1650() throws InterruptedException {
		Msg message = Msg.builder().text("千年魔灵和万年魔灵即将现身开始。")
				.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif"));
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 58 16 ? * SAT")
	public void qiannian1700() throws InterruptedException {
		Msg message = Msg.builder().text("千年魔灵和万年魔灵即将现身开始。")
				.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/106.gif"));
		sendGroupMessage(message);
	}

	@Scheduled(cron = "0 30 14 ? * SUN")
	public void menpaichuangguan() throws InterruptedException {
		if (isNthWeekOfMonth(1)) {
			Msg message = Msg.builder().text("门派闯关活动即将开始。")
					.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif"));
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 30 11 ? * SUN")
	public void kejudasai() throws InterruptedException {
		if (isNthWeekOfMonth(2)) {
			Msg message = Msg.builder().text("科举大赛活动即将开始。")
					.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/104.gif"));
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 00 14 ? * SUN")
	public void wenyunmoxiang() throws InterruptedException {
		if (isNthWeekOfMonth(2)) {
			Msg message = Msg.builder().text("文韵墨香活动即将开始。")
					.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/42.gif"));
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 30 14 ? * SUN")
	public void caihongzhengbasai() throws InterruptedException {
		if (isNthWeekOfMonth(3)) {
			if (isNthWeekOfMonth(2)) {
				Msg message = Msg.builder().text("彩虹争霸赛活动即将开始。")
						.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/90.gif"));
				sendGroupMessage(message);
			}
		}
	}

	@Scheduled(cron = "0 30 13 ? * SUN")
	public void changanbaoweizhan() throws InterruptedException {
		if (isNthWeekOfMonth(5)) {
			if (isNthWeekOfMonth(2)) {
				Msg message = Msg.builder().text("彩虹争霸赛活动即将开始。")
						.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/101.gif"));
				sendGroupMessage(message);
			}
		}
	}

	@Scheduled(cron = "0 0 20 ? * FRI")
	public void bangzhanzhijianshe() throws InterruptedException {
		if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
			Msg message = Msg.builder().text("帮战之建设活动即将开始。")
					.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif"));
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 0 20 ? * SAT")
	public void bangzhanzhijingsai() throws InterruptedException {
		if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
			Msg message = Msg.builder().text("帮战之竞赛活动即将开始。")
					.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif"));
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 0 20 ? * SUN")
	public void bangzhanzhiduoqi() throws InterruptedException {
		if (isNthWeekOfMonth(2) || isNthWeekOfMonth(3) || isNthWeekOfMonth(4)) {
			Msg message = Msg.builder().text("帮战之夺旗活动即将开始。")
					.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/107.gif"));
			sendGroupMessage(message);
		}
	}

	@Scheduled(cron = "0 30 14 ? * TUE")
	public void chunsemanyuan() throws InterruptedException {
		Msg message = Msg.builder().text("春色满园活动即将开始。")
				.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/42.gif"));
		sendGroupMessage(message);
	}
}
