package com.gbf.kukuru.plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import com.gbf.kukuru.constant.CommandConstant;
import com.gbf.kukuru.util.PathUtils;
import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;

@Shiro
@Component
public class JewelryPlugin extends BotPlugin {

	private static final double EXCHANGE_RATE = 193.0 / 3000.0;

	@GroupMessageHandler
	public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
		String msg = event.getRawMessage();
		long groupId = event.getGroupId();
		if (msg.startsWith(CommandConstant.COMMAND_GET_JEWELRY_PROCESSING)) {
			// 正则表达式匹配指令格式
			Pattern pattern = Pattern.compile("宝石合成 (\\d+) (\\d+(\\.\\d+)?)");
			Matcher matcher = pattern.matcher(msg);

			if (matcher.matches()) {
				// 提取目标等级和一级宝石单价
				int targetLevel = Integer.parseInt(matcher.group(1));
				double unitPrice = Double.parseDouble(matcher.group(2));

				if (targetLevel < 1) {
					bot.sendGroupMsg(groupId, MsgUtils.builder().text("请输入1级或1级以上目标宝石等级").build(), false);
					return MESSAGE_BLOCK;
				}

				if (targetLevel > 12) {
					bot.sendGroupMsg(groupId,
							MsgUtils.builder().text("太有实力了，V50看看实力")
									.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/24.gif")).build(),
							false);
					return MESSAGE_BLOCK;
				}

				if (unitPrice > 20) {
					bot.sendGroupMsg(groupId,
							MsgUtils.builder().text("你这宝石是金子做的还是银子做的")
									.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/24.gif")).build(),
							false);
					return MESSAGE_BLOCK;
				}

				// 计算总价格
				double totalCost = calculateTotalCost(targetLevel, unitPrice);

				// 计算总价格对应的人民币
				double totalCostRMB = totalCost * EXCHANGE_RATE;

				String result = String.format("合成一个%d级宝石的总价格是: %.1f 游戏币 (约 %.1f 人民币)", targetLevel, totalCost,
						totalCostRMB);
				bot.sendGroupMsg(groupId, MsgUtils.builder().text(result).build(), true);
			} else {
				// 返回格式错误提醒
				String result = "输入格式错误，请使用正确的格式: 宝石合成 <目标等级> <一级宝石单价>";
				bot.sendGroupMsg(groupId, MsgUtils.builder().text(result).build(), true);
			}
			return MESSAGE_BLOCK;
		} else if (msg.startsWith(CommandConstant.COMMAND_GET_STAR_JEWELRY_PROCESSING)) {
			// 正则表达式匹配指令格式
			Pattern pattern = Pattern.compile("星辉石合成 (\\d+) (\\d+(\\.\\d+)?)");
			Matcher matcher = pattern.matcher(msg);
			if (matcher.matches()) {
				// 提取目标等级和一级宝石单价
				int targetLevel = Integer.parseInt(matcher.group(1));
				double unitPrice = Double.parseDouble(matcher.group(2));

				if (targetLevel < 1) {
					bot.sendGroupMsg(groupId, MsgUtils.builder().text("请输入1级或1级以上目标星辉石等级").build(), false);
					return MESSAGE_BLOCK;
				}

				if (targetLevel > 9) {
					bot.sendGroupMsg(groupId,
							MsgUtils.builder().text("太有实力了，V50看看实力")
									.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/24.gif")).build(),
							false);
					return MESSAGE_BLOCK;
				}

				if (unitPrice > 30) {
					bot.sendGroupMsg(groupId,
							MsgUtils.builder().text("你这宝石是金子做的还是银子做的")
									.img(PathUtils.getRealPathFromResource("/static/img/baozi_old/24.gif")).build(),
							false);
					return MESSAGE_BLOCK;
				}

				// 计算总价格
				double totalCost = calculateTotalCostRule3(targetLevel, unitPrice);

				// 计算总价格对应的人民币
				double totalCostRMB = totalCost * EXCHANGE_RATE;

				String result = String.format("合成一个%d级星辉石宝石的总价格是: %.1f 游戏币 (约 %.1f 人民币)", targetLevel, totalCost,
						totalCostRMB);
				bot.sendGroupMsg(groupId, MsgUtils.builder().text(result).build(), true);
			} else {
				// 返回格式错误提醒
				String result = "输入格式错误，请使用正确的格式: 星辉石合成 <目标等级> <一级星辉石单价>";
				bot.sendGroupMsg(groupId, MsgUtils.builder().text(result).build(), true);
			}
			return MESSAGE_BLOCK;
		}
		return MESSAGE_IGNORE;
	}

	public static double calculateTotalCost(int targetLevel, double unitPrice) {
		// 初始一级宝石的数量
		int totalGems = 1;
		// 计算合成目标等级宝石所需的总一级宝石数量
		for (int level = 1; level < targetLevel; level++) {
			totalGems *= 2;
		}
		// 计算总价格
		return totalGems * unitPrice;
	}

	public static double calculateTotalCostRule3(int targetLevel, double unitPrice) {
		// 初始一级宝石的数量
		int totalGems = 1;

		// 计算合成目标等级宝石所需的总一级宝石数量
		for (int level = 1; level < targetLevel; level++) {
			totalGems *= 3;
		}
		// 计算总价格
		return totalGems * unitPrice;
	}

}
