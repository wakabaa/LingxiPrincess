package com.gbf.kukuru.service.image;

import org.jetbrains.skija.Data;
import org.jetbrains.skija.EncodedImageFormat;

/**
 * 图片服务接口类
 *
 * @author ginoko
 * @since 2022-06-09
 */
public interface IImageService {

    String DEFAULT_FONT = "static/font/思源黑体.ttf";
    int DEFAULT_WIDTH = 600;
    int DEFAULT_HEIGHT = 400;
    EncodedImageFormat DEFAULT_FORMAT = EncodedImageFormat.PNG;

    /**
     * 生成图片
     *
     * @param format 图片格式
     * @return 图片数据
     */
    Data generateImage(EncodedImageFormat format);
}
