package com.gbf.kukuru.plugin;

import org.springframework.stereotype.Component;

import com.mikuac.shiro.core.BotPlugin;

@Component
public class ShopPlugin extends BotPlugin {

//	@Autowired
//	private SpiritService spiritService;
//
//	@Resource
//	private SendMsgUtils sendMsgUtils;
//	
//	@Override
//	public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
//		String msg = event.getRawMessage();
//		long userId = event.getUserId();
//		long groupId = event.getGroupId();
//		StringBuilder sb = new StringBuilder();
//		if (msg.startsWith(CommandConstant.COMMAND_GET_SHOP)) {
//			sb.append("你群指定店铺：" + "\n");
//			List<SpiritShopEntity> selectSpiritShop = spiritService.selectSpiritShop();
//			for (SpiritShopEntity shop : selectSpiritShop) {
//				sb.append(shop.getShopId() + "  " + shop.getType() + "  " + shop.getDescription() + "\n");
//			}
//			bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(sb.toString()), false);
//			return MESSAGE_BLOCK;
//		} else if (msg.startsWith(CommandConstant.COMMAND_ADD_SHOP)) {
//			// 正则表达式匹配指令格式
//			Pattern pattern = Pattern.compile(CommandConstant.COMMAND_ADD_SHOP + " (\\d+) (\\S+) (.+)$");
//			Matcher matcher = pattern.matcher(msg);
//			if (matcher.matches()) {
//				int id = Integer.parseInt(matcher.group(1));
//				String type = matcher.group(2);
//				String description = matcher.group(3);
//				SpiritShopEntity spiritShopEntity = new SpiritShopEntity();
//				spiritShopEntity.setShopId(id);
//				spiritShopEntity.setType(type);
//				spiritShopEntity.setDescription(description);
//				int spiritShop = spiritService.addSpiritShop(spiritShopEntity);
//				if (spiritShop > 0) {
//					String result = "添加成功";
//					bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(result)
//							.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/02.gif")), false);
//				} else {
//					String result = "添加失败";
//					bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(result)
//							.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/17.gif")), false);
//				}
//			} else {
//				String result = "输入格式错误，请使用正确的格式: 添加店铺 <店铺号> <店铺类型> <推荐理由>";
//				bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(result), false);
//			}
//			return MESSAGE_BLOCK;
//		} else if (msg.startsWith(CommandConstant.COMMAND_DEL_SHOP)) {
//			// 正则表达式匹配指令格式
//			Pattern pattern = Pattern.compile(CommandConstant.COMMAND_DEL_SHOP + " (\\d+)$");
//			Matcher matcher = pattern.matcher(msg);
//			if (matcher.matches()) {
//				int id = Integer.parseInt(matcher.group(1));
//				SpiritShopEntity spiritShopEntity = new SpiritShopEntity();
//				spiritShopEntity.setShopId(id);
//				int deleteSpiritShop = spiritService.deleteSpiritShop(spiritShopEntity);
//				if (deleteSpiritShop > 0) {
//					String result = "删除成功";
//					bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(result)
//							.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/02.gif")), false);
//				} else {
//					String result = "删除失败";
//					bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(result)
//							.image(PathUtils.getRealPathFromResource("/static/img/baozi_old/17.gif")), false);
//				}
//			} else {
//				String result = "输入格式错误，请使用正确的格式: 删除店铺 <店铺号>";
//				bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(result), false);
//			}
//			return MESSAGE_BLOCK;
//		}
//		return MESSAGE_IGNORE;
//	}

}
