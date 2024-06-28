package com.gbf.kukuru.service.image.impl;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.skija.Canvas;
import org.jetbrains.skija.Data;
import org.jetbrains.skija.EncodedImageFormat;
import org.jetbrains.skija.Image;
import org.jetbrains.skija.Paint;
import org.jetbrains.skija.PaintMode;
import org.jetbrains.skija.Rect;
import org.jetbrains.skija.Surface;

import com.gbf.kukuru.service.image.IImageService;
import com.gbf.kukuru.util.PathUtils;

import cn.hutool.core.io.file.FileReader;

/**
 * PCR图片占卜 接口实现类
 *
 * @author ginoko
 * @since 2022-08-19
 */
public class GhostRangeDrawer implements IImageService {

	private final String mapName;
	private final Surface mainSurface;
	private final Canvas mainCanvas;
	private final int gameX;
	private final int gameY;
	private static final Map<String, int[]> mapCoordinates;
	
    static {
        mapCoordinates = new HashMap<>();
        mapCoordinates.put("西凉女国", new int[]{0, 0});
        mapCoordinates.put("宝象国", new int[]{0, 0});
        mapCoordinates.put("长寿村", new int[]{0,0});
        mapCoordinates.put("女儿村", new int[]{0,0});
        mapCoordinates.put("普陀山", new int[]{0,0});
        mapCoordinates.put("五庄观", new int[]{0,0});
        mapCoordinates.put("大唐境外", new int[]{0,0});
        mapCoordinates.put("建邺城", new int[]{0,0});
        mapCoordinates.put("傲来国", new int[]{221, 148});
        // 添加其他地图及其最大坐标
    }
    
	public GhostRangeDrawer(String mapName, int gameX, int gameY) {
		this.mapName = mapName;
		Image background = Image.makeFromEncoded(
				new FileReader(PathUtils.getRealPathFromResource("/static/img/map/" + this.mapName + ".jpg"))
						.readBytes());
		this.mainSurface = Surface.makeRasterN32Premul(background.getWidth(), background.getHeight());
		this.mainCanvas = this.mainSurface.getCanvas();
		this.gameX = gameX;
		this.gameY = gameY;
	}

	@Override
	public Data generateImage(EncodedImageFormat format) {
		/* 生成背景 */
		Image background = Image.makeFromEncoded(
				new FileReader(PathUtils.getRealPathFromResource("/static/img/map/" + this.mapName + ".jpg"))
						.readBytes());
		this.mainCanvas.drawImage(background, 0, 0);

		// 地图的最大游戏坐标
		int gameMaxX = 221;
		int gameMaxY = 148;

		// 计算比例尺
		double xRatio = (double) mainSurface.getWidth() / gameMaxX;
		double yRatio = (double) mainSurface.getHeight() / gameMaxY;

		// 计算矩形范围的边界
		int radius = 50;
		int left = (int) ((gameX - radius) * xRatio);
		int right = (int) ((gameX + radius) * xRatio);
		int bottom = (int) (mainSurface.getHeight() - (gameY * yRatio - radius * yRatio));
        int top = (int) (mainSurface.getHeight() - (gameY * yRatio + radius * yRatio));

		left = Math.max(0, left);
		right = Math.min(mainSurface.getWidth(), right);
		top = Math.max(0, top);
        bottom = Math.min(mainSurface.getHeight(), bottom);

		// 绘制矩形范围
		try (Paint paint = new Paint()) {
			paint.setColor(0xFFFF0000); // 红色
			paint.setMode(PaintMode.STROKE);
			paint.setStrokeWidth(5); // 设置线条宽度
			mainCanvas.drawRect(Rect.makeLTRB(left, top, right, bottom), paint);
		}

		// 返回生成的图片
		return this.mainSurface.makeImageSnapshot().encodeToData(format);
	}
}
