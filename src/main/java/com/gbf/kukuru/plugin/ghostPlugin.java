package com.gbf.kukuru.plugin;

import org.springframework.stereotype.Component;

import com.mikuac.shiro.core.BotPlugin;

@Component
public class ghostPlugin extends BotPlugin {

//	@Override
//	public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
//		String msg = event.getRawMessage();
//		long userId = event.getUserId();
//		long groupId = event.getGroupId();
//		String patternString = CommandConstant.COMMAND_GHOST + "\\s+(\\S+)\\s+(\\d+)\\s+(\\d+)";
//		Pattern pattern = Pattern.compile(patternString);
//		Matcher matcher = pattern.matcher(msg);
//		if (msg.startsWith(CommandConstant.COMMAND_GHOST)) {
//			if (matcher.find()) {
//				String mapName = matcher.group(1);
//				int x = Integer.parseInt(matcher.group(2));
//				int y = Integer.parseInt(matcher.group(3));
//				GhostRangeDrawer GhostRangeDrawer = new GhostRangeDrawer(mapName, x, y);
//				try (Data pngData = GhostRangeDrawer.generateImage(IImageService.DEFAULT_FORMAT)) {
//					bot.sendGroupMsg(groupId, Msg.builder().at(userId)
//							.image(ImgUtils.saveImage(pngData.getBytes(), IImageService.DEFAULT_FORMAT)), false);
//				}
//			} else {
//				System.out.println("输入格式不正确，请使用格式：抓鬼 地图名 X Y");
//			}
//			return MESSAGE_BLOCK;
//		}
//		return MESSAGE_IGNORE;
//	}

}
