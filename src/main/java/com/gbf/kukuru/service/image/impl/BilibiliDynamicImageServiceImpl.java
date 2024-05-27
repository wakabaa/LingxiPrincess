package com.gbf.kukuru.service.image.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gbf.kukuru.constant.color.BilibiliDynamicColorConstant;
import com.gbf.kukuru.constant.resolution.BilibiliDynamicResolutionConstant;
import com.gbf.kukuru.entity.BilibiliDynamic;
import com.gbf.kukuru.service.image.IImageService;
import com.gbf.kukuru.util.ImgUtils;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.skija.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * B站动态图片 服务实现类
 *
 * @author ginoko
 * @since 2022-06-09
 */
@Slf4j
public class BilibiliDynamicImageServiceImpl implements IImageService {

    private final int width;
    private final int height;
    private final int leftPadding;
    private final int leftPaddingAvatar;
    private final int leftPaddingContent;
    private final int topPadding;
    private final int topPaddingAvatar;
    private final int topPaddingName;
    private final int topPaddingDate;
    private final int topPaddingContent;
    private final int topPaddingBetweenContentAndStatusIcon;
    private final int topPaddingBetweenContentAndStatusNumber;
    private final int avatarRadius;
    private final int avatarDiameter;
    private final int pendantRadius;
    private final int pendantDiameter;
    private final int nameFontSize;
    private final int dateFontSize;
    private final int contentFontSize;
    private final int backgroundRadius;
    private final BilibiliDynamic dynamic;
    private final Surface mainSurface;
    private final Canvas mainCanvas;

    /**
     * 根据分辨率计算出对应数值
     *
     * @param value 基准数值
     * @return 整型数值
     */
    private int calculateByResolution(int value, Fraction resolution) {
        BigDecimal valueDecimal = new BigDecimal(value).setScale(8, RoundingMode.UP);
        BigDecimal resolutionDecimal = BigDecimal.valueOf(resolution.doubleValue()).setScale(8, RoundingMode.UP);
        return Integer.parseInt(
                valueDecimal.multiply(resolutionDecimal)
                        .setScale(0, RoundingMode.UP)
                        .toString()
        );
    }

    /**
     * 根据分辨率计算出对应数值
     *
     * @return 整型数值
     */
    private int calculateByResolution(Fraction resolution) {
        BigDecimal valueDecimal = new BigDecimal(this.width).setScale(8, RoundingMode.UP);
        BigDecimal resolutionDecimal = BigDecimal.valueOf(resolution.doubleValue()).setScale(8, RoundingMode.UP);
        return Integer.parseInt(
                valueDecimal.multiply(resolutionDecimal)
                        .setScale(0, RoundingMode.UP)
                        .toString()
        );
    }

    /**
     * 构造函数
     * <p>
     * 主要功能是计算出布局所需所有参数
     *
     * @param width   宽度
     * @param dynamic 动态对象
     */
    public BilibiliDynamicImageServiceImpl(int width, @NonNull BilibiliDynamic dynamic) {
        if (width < BilibiliDynamicResolutionConstant.MIN_WIDTH) {
            width = BilibiliDynamicResolutionConstant.MIN_WIDTH;
        } else if (width > BilibiliDynamicResolutionConstant.MAX_WIDTH) {
            width = BilibiliDynamicResolutionConstant.MAX_WIDTH;
        }
        this.width = width;
        this.height = calculateByResolution(BilibiliDynamicResolutionConstant.BACKGROUND);
        this.nameFontSize = calculateByResolution(BilibiliDynamicResolutionConstant.NAME_FONT);
        this.dateFontSize = calculateByResolution(BilibiliDynamicResolutionConstant.DATE_FONT);
        this.contentFontSize = calculateByResolution(BilibiliDynamicResolutionConstant.CONTENT_FONT);
        this.leftPadding = calculateByResolution(BilibiliDynamicResolutionConstant.LEFT_PADDING);
        this.leftPaddingAvatar = calculateByResolution(BilibiliDynamicResolutionConstant.LEFT_PADDING_AVATAR);
        this.leftPaddingContent = calculateByResolution(BilibiliDynamicResolutionConstant.LEFT_PADDING_CONTENT);
        this.topPadding = calculateByResolution(BilibiliDynamicResolutionConstant.TOP_PADDING);
        this.topPaddingAvatar = calculateByResolution(BilibiliDynamicResolutionConstant.TOP_PADDING_AVATAR);
        this.topPaddingName = calculateByResolution(this.nameFontSize, BilibiliDynamicResolutionConstant.FONT_HEIGHT)
                + calculateByResolution(BilibiliDynamicResolutionConstant.TOP_PADDING_NAME);
        this.topPaddingDate = calculateByResolution(this.dateFontSize, BilibiliDynamicResolutionConstant.FONT_HEIGHT)
                + calculateByResolution(BilibiliDynamicResolutionConstant.TOP_PADDING_DATE);
        this.topPaddingContent = calculateByResolution(this.contentFontSize, BilibiliDynamicResolutionConstant.FONT_HEIGHT)
                + calculateByResolution(BilibiliDynamicResolutionConstant.TOP_PADDING_CONTENT);
        this.topPaddingBetweenContentAndStatusIcon = calculateByResolution(
                BilibiliDynamicResolutionConstant.TOP_PADDING_BETWEEN_CONTENT_AND_STATUS_ICON
        );
        this.topPaddingBetweenContentAndStatusNumber = calculateByResolution(
                BilibiliDynamicResolutionConstant.TOP_PADDING_BETWEEN_CONTENT_AND_STATUS_NUMBER
        );
        this.avatarRadius = calculateByResolution(BilibiliDynamicResolutionConstant.AVATAR_RADIUS);
        this.avatarDiameter = this.avatarRadius * 2;
        this.pendantRadius = calculateByResolution(BilibiliDynamicResolutionConstant.PENDANT_RADIUS);
        this.pendantDiameter = this.pendantRadius * 2;
        this.backgroundRadius = calculateByResolution(BilibiliDynamicResolutionConstant.BACKGROUND_RADIUS);
        this.dynamic = dynamic;
        this.mainSurface = Surface.makeRasterN32Premul(this.width, this.height);
        this.mainCanvas = this.mainSurface.getCanvas();
    }

    @SneakyThrows
    private Typeface getTypeface() {
        Resource fontResource = new ClassPathResource(DEFAULT_FONT);
        return Typeface.makeFromFile(fontResource.getFile().getAbsolutePath());
    }

    private BilibiliDynamicImageServiceImpl generateBackground() {
        try (Paint paintBackGround = new Paint()) {
            paintBackGround.setColor(BilibiliDynamicColorConstant.BACKGROUND);
            this.mainCanvas.drawRRect(
                    RRect.makeXYWH(0, 0, this.width, this.height,
                            this.backgroundRadius, this.backgroundRadius, this.backgroundRadius, this.backgroundRadius),
                    paintBackGround
            );
        }
        return this;
    }

    private BilibiliDynamicImageServiceImpl generateText() {
//        if (StrUtil.isBlank(this.dynamic.getText())) {
//            if (CollectionUtils.isEmpty(this.dynamic.getImages())) {
//                this.dynamic.setText("发布动态");
//            } else {
//                /* 只展示图片 */
//                return this;
//            }
//        }
//        try (Paint paintText = new Paint()) {
//            paintText.setColor(BilibiliDynamicColorConstant.CONTENT_FONT);
//            this.mainCanvas.drawString(
//                    this.dynamic.getText(),
//                    this.leftPaddingContent,
//                    this.topPaddingContent,
//                    new Font(getTypeface(), this.contentFontSize),
//                    paintText
//            );
//        }
        return this;
    }

    private BilibiliDynamicImageServiceImpl generateAvatar() {
        if (StrUtil.isBlank(this.dynamic.getAuthorAvatar())) {
            log.info("[B站动态图片生成]: 忽略头像...");
            return this;
        }
        try (Surface avatarSurface = Surface.makeRasterN32Premul(
                this.leftPaddingAvatar + this.avatarDiameter,
                this.topPaddingAvatar + this.avatarDiameter);
             Paint paintAvatar = new Paint();
             Image avatarImage = Image.makeFromEncoded(ImgUtils.getImageBytesByUrl(this.dynamic.getAuthorAvatar()))) {
            Canvas avatarCanvas = avatarSurface.getCanvas();
            RRect rRect = RRect.makeXYWH(
                    this.leftPaddingAvatar,
                    this.topPaddingAvatar,
                    this.avatarDiameter,
                    this.avatarDiameter,
                    this.avatarRadius
            );
            paintAvatar.setColor(BilibiliDynamicColorConstant.BACKGROUND);
            paintAvatar.setAlpha(160);
            avatarCanvas.drawCircle(
                    rRect.getLeft() + rRect.getWidth() / 2,
                    rRect.getTop() + rRect.getWidth() / 2,
                    rRect.getWidth() / 2,
                    paintAvatar
            );
            avatarCanvas.clipRRect(rRect);
            avatarCanvas.drawImageRect(
                    avatarImage,
                    Rect.makeXYWH(0, 0, avatarImage.getWidth(), avatarImage.getHeight()),
                    rRect,
                    new FilterMipmap(FilterMode.LINEAR, MipmapMode.NEAREST),
                    null,
                    true
            );
            this.mainCanvas.drawImage(avatarSurface.makeImageSnapshot(), 0, 0, null);
        }
        return this;
    }

    private BilibiliDynamicImageServiceImpl generatePendant() {
        if (StrUtil.isBlank(this.dynamic.getAuthorPendant())) {
            log.info("[B站动态图片生成]: 忽略头像挂件...");
            return this;
        }
        try (Surface pendantSurface = Surface.makeRasterN32Premul(
                this.leftPadding + this.pendantDiameter,
                this.topPadding + this.pendantDiameter);
             Image pendantImage = Image.makeFromEncoded(ImgUtils.getImageBytesByUrl(this.dynamic.getAuthorPendant()))) {
            Canvas pendantCanvas = pendantSurface.getCanvas();
            Rect srcPendantRect = Rect.makeXYWH(0, 0, pendantImage.getWidth(), pendantImage.getHeight());
            Rect dstPendantRect = Rect.makeXYWH(
                    this.leftPadding,
                    this.topPadding,
                    this.pendantDiameter,
                    this.pendantDiameter
            );
            pendantCanvas.drawImageRect(
                    pendantImage,
                    srcPendantRect,
                    dstPendantRect,
                    new FilterMipmap(FilterMode.LINEAR, MipmapMode.NEAREST),
                    null,
                    true
            );
            this.mainCanvas.drawImage(pendantSurface.makeImageSnapshot(), 0, 0, null);
        }
        return this;
    }

    private BilibiliDynamicImageServiceImpl generateName() {
        if (StrUtil.isBlank(this.dynamic.getAuthor())) {
            this.dynamic.setAuthor("?未知用户?");
        }
        try (Paint paintText = new Paint()) {
            paintText.setColor(BilibiliDynamicColorConstant.NAME_FONT);
            this.mainCanvas.drawString(
                    this.dynamic.getAuthor(),
                    this.leftPaddingContent,
                    this.topPaddingName,
                    new Font(getTypeface(), this.nameFontSize),
                    paintText
            );
        }
        return this;
    }

    private BilibiliDynamicImageServiceImpl generateDate() {
        if (ObjectUtils.isEmpty(this.dynamic.getPublishTime())) {
            this.dynamic.setPublishTime(0L);
        }
        try (Paint paintText = new Paint()) {
            paintText.setColor(BilibiliDynamicColorConstant.DATE_FONT);
            this.mainCanvas.drawString(
                    DateUtil.format(new Date(this.dynamic.getPublishTime()), DateTimeFormatter.ISO_DATE),
                    this.leftPaddingContent,
                    this.topPaddingDate,
                    new Font(getTypeface(), this.dateFontSize),
                    paintText
            );
        }
        return this;
    }

    @Override
    public Data generateImage(EncodedImageFormat format) {
        generateBackground()
                .generateAvatar()
                .generatePendant()
                .generateName()
                .generateDate()
                .generateText();
        return this.mainSurface.makeImageSnapshot().encodeToData(format);
    }
}
