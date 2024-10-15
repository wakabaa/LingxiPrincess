package com.gbf.kukuru.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.gbf.kukuru.code.KukuruExceptionCode;
import com.gbf.kukuru.exception.KukuruException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.skija.EncodedImageFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ImgUtils {

    private static String imageStorePath;

    @Value("${bilibili.image-store-path}")
    public void setImageStorePath(String path) {
        imageStorePath = path;
    }

    public static String getAbsoluteImageStorePath() {
        return PathUtils.addPath(PathUtils.getProjectJarPath(), ImgUtils.imageStorePath);
    }

    public static String getImageNameFromUrl(String url) {
        List<String> urlList = Arrays.stream(url.split("/")).collect(Collectors.toList());
        return urlList.get(urlList.size() - 1);
    }

    public static String downloadImage(String url) {
        String imageName = getImageNameFromUrl(url);
        String absolutImageStorePath = ImgUtils.getAbsoluteImageStorePath();
        String realImagePath = PathUtils.addPath(absolutImageStorePath, imageName);
        if (!FileUtil.exist(absolutImageStorePath)) {
            /* 文件存储路径不存在时, 创建路径 */
            FileUtil.mkdir(absolutImageStorePath);
            log.info("[图片工具]: 创建路径\"{}\"", absolutImageStorePath);
        }
        long imageSize = HttpUtil.downloadFile(url, absolutImageStorePath);
        if (imageSize > 0) {
            RedisUtils.set(imageName, realImagePath);
            log.info("[图片工具]: 下载图片({}), 大小({})", imageName, imageSize);
        } else {
            throw new KukuruException(KukuruExceptionCode.DOWNLOAD_FILE_FAILED);
        }
        return realImagePath;
    }

    public static String getImageFormatString(EncodedImageFormat format) {
        switch (format) {
            case PNG:
            default:
                return ".png";
            case BMP:
                return ".bmp";
            case JPEG:
                return ".jpg";
            case GIF:
                return ".gif";
            case ICO:
                return ".ico";
        }
    }

    @SneakyThrows
    public static String saveImage(byte[] imageBytes, EncodedImageFormat format) {
        String imageName = UUID.randomUUID().toString(true) + getImageFormatString(format);
        String absolutImageStorePath = ImgUtils.getAbsoluteImageStorePath();
        String realImagePath = PathUtils.addPath(absolutImageStorePath, imageName);
        if (!FileUtil.exist(absolutImageStorePath)) {
            /* 文件存储路径不存在时, 创建路径 */
            FileUtil.mkdir(absolutImageStorePath);
            log.info("[图片工具]: 创建路径\"{}\"", absolutImageStorePath);
        }
        Files.write(Paths.get(realImagePath), imageBytes, StandardOpenOption.CREATE);
        log.info("[图片工具]: 保存图片({})", imageName);
        return realImagePath;
    }

    /**
     * 通过url获取图片的字节数据
     *
     * @param url url字符串
     * @return 图片的字节数据
     */
    public static byte[] getImageBytesByUrl(String url) {
        String imageName = getImageNameFromUrl(url);
        String realImagePath = RedisUtils.get(imageName);
        if (StrUtil.isBlank(realImagePath)) {
            realImagePath = downloadImage(url);
        } else if (!FileUtil.exist(realImagePath)) {
            log.warn("[图片工具]: 未找到图片\"{}\"！", realImagePath);
            realImagePath = downloadImage(url);
        }
        return new FileReader(realImagePath).readBytes();
    }
}
