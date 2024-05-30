package com.gbf.kukuru.plugin;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gbf.kukuru.constant.CommandConstant;
import com.gbf.kukuru.entity.SpiritEventEntity;
import com.gbf.kukuru.service.SpiritService;

import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;

@Component
public class QueryEventsPlugin extends BotPlugin {

	@Autowired
	private SpiritService spiritService;

	@Override
	public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
		String msg = event.getRawMessage();
		long userId = event.getUserId();
		long groupId = event.getGroupId();
		StringBuilder sb = new StringBuilder();
		if (msg.startsWith(CommandConstant.COMMAND_GET_DAY_EVENTS)) {

			return MESSAGE_BLOCK;
		} else if (msg.startsWith(CommandConstant.COMMAND_GET_WEEK_EVENTS)) {
			List<SpiritEventEntity> selectSpiritEventByMonth = spiritService.selectSpiritEventByMonth();
			sb.append(" 本周活动安排如下：\n");
			for (SpiritEventEntity spiritevent : selectSpiritEventByMonth) {
				sb.append(spiritevent.getName() + "\n" + spiritevent.getDescription() + "\n");
			}
			bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(sb.toString()), false);
			return MESSAGE_BLOCK;
		} else if (msg.startsWith(CommandConstant.COMMAND_GET_MONTH_EVENTS)) {

			return MESSAGE_BLOCK;
		} else if (msg.startsWith(CommandConstant.COMMAND_GET_YEAR_EVENTS)) {

			return MESSAGE_BLOCK;
		}
		return MESSAGE_IGNORE;
	}

}
