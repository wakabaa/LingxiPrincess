package com.gbf.kukuru.constant.resolution;

import org.apache.commons.lang3.math.Fraction;

/**
 * B站动态的分辨率 常量类
 * <pre>
 * 分辨率以动态的 背景宽度 为基准
 *
 * @author ginoko
 * @since 2022-06-17
 */
public interface BilibiliDynamicResolutionConstant {

    /**
     * 最小背景宽度
     */
    int MIN_WIDTH = 200;
    /**
     * 最大背景宽度
     */
    int MAX_WIDTH = 4096;
    /**
     * 一行最多汉字数
     */
    int MAX_LINE_LENGTH_CH = 27;

    /**
     * 背景(高:宽)
     */
    Fraction BACKGROUND = Fraction.getFraction(1153, 1000);
    /**
     * 背景矩形圆角半径(半径:背景宽)
     */
    Fraction BACKGROUND_RADIUS = Fraction.getFraction(20, 1000);
    /**
     * 顶间距(间距:背景宽)
     */
    Fraction TOP_PADDING = Fraction.getFraction(30, 1000);
    /**
     * 头像顶间距(间距:背景宽)
     */
    Fraction TOP_PADDING_AVATAR = Fraction.getFraction(60, 1000);
    /**
     * 名字顶间距(间距:背景宽)
     */
    Fraction TOP_PADDING_NAME = Fraction.getFraction(60, 1000);
    /**
     * 日期顶间距(间距:背景宽)
     */
    Fraction TOP_PADDING_DATE = Fraction.getFraction(110, 1000);
    /**
     * 内容顶间距(间距:背景宽)
     */
    Fraction TOP_PADDING_CONTENT = Fraction.getFraction(160, 1000);
    /**
     * 状态图标与内容的顶间距(间距:背景宽)
     */
    Fraction TOP_PADDING_BETWEEN_CONTENT_AND_STATUS_ICON = Fraction.getFraction(54, 1000);
    /**
     * 状态数字与内容的顶间距(间距:背景宽)
     */
    Fraction TOP_PADDING_BETWEEN_CONTENT_AND_STATUS_NUMBER = Fraction.getFraction(56, 1000);
    /**
     * 左间距(间距:背景宽)
     */
    Fraction LEFT_PADDING = Fraction.getFraction(30, 1000);
    /**
     * 头像左间距(间距:背景宽)
     */
    Fraction LEFT_PADDING_AVATAR = Fraction.getFraction(60, 1000);
    /**
     * 内容左间距(间距:背景宽)
     */
    Fraction LEFT_PADDING_CONTENT = Fraction.getFraction(200, 1000);
    /**
     * 状态图标底间距(间距:背景宽)
     */
    Fraction BOTTOM_PADDING_STATUS_ICON = Fraction.getFraction(53, 1000);
    /**
     * 状态数字底间距(间距:背景宽)
     */
    Fraction BOTTOM_PADDING_STATUS_NUMBER = Fraction.getFraction(55, 1000);
    /**
     * 挂件半径(半径:背景宽)
     */
    Fraction PENDANT_RADIUS = Fraction.getFraction(80, 1000);
    /**
     * 头像半径(半径:背景宽)
     */
    Fraction AVATAR_RADIUS = Fraction.getFraction(50, 1000);
    /**
     * 行间距比例(行间距:字体大小)
     */
    Fraction LINE_HEIGHT = Fraction.getFraction(547, 2600);
    /**
     * 字体高度比例(字体高度:字体大小)
     */
    Fraction FONT_HEIGHT = Fraction.getFraction(1, 1);
    /**
     * 作者名字字体大小(字体大小:背景宽)
     */
    Fraction NAME_FONT = Fraction.getFraction(26, 1000);
    /**
     * 时间字体大小(字体大小:背景宽)
     */
    Fraction DATE_FONT = Fraction.getFraction(17, 1000);
    /**
     * 内容宽度(内容宽:背景宽)
     */
    Fraction CONTENT = Fraction.getFraction(540, 1000);
    /**
     * 内容字体大小(字体大小:背景宽)
     */
    Fraction CONTENT_FONT = Fraction.getFraction(27, 1000);
    /**
     * 状态图标比例(图标宽:背景宽)
     */
    Fraction STATUS_ICON = Fraction.getFraction(40, 1000);
    /**
     * 状态图标自身比例(图标宽:图标高)
     */
    Fraction STATUS_ICON_SELF = Fraction.getFraction(40, 35);
    /**
     * 状态数字字体大小(字体大小:背景宽)
     */
    Fraction STATUS_NUMBER_FONT = Fraction.getFraction(26, 1000);
}
