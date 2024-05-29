package com.gbf.kukuru.service.image.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.gbf.kukuru.code.KukuruExceptionCode;
import com.gbf.kukuru.entity.PcrDivinationDesc;
import com.gbf.kukuru.entity.PcrDivinationDescInner;
import com.gbf.kukuru.exception.KukuruException;
import com.gbf.kukuru.service.image.IImageService;
import com.gbf.kukuru.util.PathUtils;
import lombok.SneakyThrows;
import org.jetbrains.skija.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * PCR图片占卜 接口实现类
 *
 * @author ginoko
 * @since 2022-08-19
 */
public class SpiritImageServiceImpl implements IImageService {

    private final String imageName;
    private final String title;
    private final String content;
    private final Surface mainSurface;
    private final Canvas mainCanvas;

    public SpiritImageServiceImpl(long userId) {
        userId = Math.abs(userId);
        long randomNumber = RandomUtil.randomLong(DateUtil.current() + 114514L + userId);
        this.mainSurface = Surface.makeRasterN32Premul(480, 480);
        this.mainCanvas = this.mainSurface.getCanvas();
        List<PcrDivinationDesc> descList = JSONUtil.readJSONArray(
                new File(PathUtils.getRealPathFromResource("/luckDescList.json")),
                StandardCharsets.UTF_8
        ).toList(PcrDivinationDesc.class);
        PcrDivinationDesc desc = descList.get(Math.toIntExact(randomNumber % descList.size()));
        this.imageName = "frame_" + desc.getCharaIds().get(Math.toIntExact(randomNumber % desc.getCharaIds().size())) + ".jpg";
        PcrDivinationDescInner inner = desc.getTypes().get(Math.toIntExact(randomNumber % desc.getTypes().size()));
        this.title = JSONUtil.readJSONObject(
                new File(PathUtils.getRealPathFromResource("/luckExplain.json")),
                StandardCharsets.UTF_8
        ).getStr(String.valueOf(inner.getLuckPoint()));
        this.content = inner.getContent();
    }

    @Override
    @SneakyThrows
    public Data generateImage(EncodedImageFormat format) {
        /* 生成背景 */
        Image background = Image.makeFromEncoded(new FileReader(PathUtils.getRealPathFromResource("/static/img/pcrDailyLuck/" + this.imageName)).readBytes());
        this.mainCanvas.drawImage(background, 0, 0);
        /* 生成标题 */
        int[] xOfTitle = {117, 94, 72};
        try (Paint paintText = new Paint()) {
            paintText.setColor(0xFFF5F5F5);
            this.mainCanvas.drawString(
                    this.title,
                    xOfTitle[this.title.length() - 1],
                    117,
                    new Font(Typeface.makeFromFile(PathUtils.getRealPathFromResource("/static/font/Mamelon.otf")), 45),
                    paintText
            );
        }
        /* 生成内容 */
        int maxLineTextNumber = 9;
        if (this.content.length() > 4 * maxLineTextNumber) {
            throw new KukuruException(KukuruExceptionCode.TOO_LONG_CONTENT, this.content);
        }
        int[] xOfContent = {128, 144, 155, 165};
        int[] lineSpaceOfContent = {0, 31, 28, 25};
        try (Paint paintText = new Paint();
             Font contentFont = new Font(Typeface.makeFromFile(PathUtils.getRealPathFromResource("/static/font/sakura.ttf")), 25)) {
            paintText.setColor(0xFF323232);
            String[] contentSingleWord = this.content.split("");
            int xIndex = (contentSingleWord.length - 1) / maxLineTextNumber;
            for (int i = 0; i < contentSingleWord.length; i++) {
                this.mainCanvas.drawString(
                        contentSingleWord[i],
                        (float) (xOfContent[xIndex] - lineSpaceOfContent[xIndex] * (i / maxLineTextNumber)),
                        (float) (186 + (i % maxLineTextNumber) * 23.5),
                        contentFont,
                        paintText
                );
            }
        }
        /* 生成图片并返回 */
        return this.mainSurface.makeImageSnapshot().encodeToData(format);
    }
}
