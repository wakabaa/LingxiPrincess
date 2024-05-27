package com.gbf.kukuru.runner;

import javax.annotation.Resource;

import org.jetbrains.skija.Data;
import org.jetbrains.skija.EncodedImageFormat;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.gbf.kukuru.entity.BilibiliDynamic;
import com.gbf.kukuru.service.ClientDaemonService;
import com.gbf.kukuru.service.image.IImageService;
import com.gbf.kukuru.service.image.impl.BilibiliDynamicImageServiceImpl;
import com.gbf.kukuru.util.ImgUtils;
import com.gbf.kukuru.util.PathUtils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;

/**
 * Kukuru机器人启动成功的回调接口
 *
 * @author ginoko
 * @since 2022-06-02
 */
@Component
public class KukuruRunner implements ApplicationRunner {

    @Resource
    private ClientDaemonService daemonService;

    @SneakyThrows
    private void test() {
        BilibiliDynamic dynamic = new BilibiliDynamic();
        dynamic.setType("DYNAMIC_TYPE_WORD");
        dynamic.setAuthor("Mr.Quin");
        dynamic.setPublishTime(DateUtil.current());
        dynamic.setAuthorAvatar("http://i0.hdslb.com/bfs/face/42eb05e354476c2b22b5c512c4a484d93650020c.jpg");
        dynamic.setAuthorPendant("http://i1.hdslb.com/bfs/garb/item/656119de5098823514b5473f4af7b4f4b44464d0.png");
        BilibiliDynamicImageServiceImpl imageService = new BilibiliDynamicImageServiceImpl(
                IImageService.DEFAULT_WIDTH,
                dynamic
        );
        Data pngData = imageService.generateImage(EncodedImageFormat.PNG);
        String imagePath = PathUtils.addPath(ImgUtils.getAbsoluteImageStorePath(), "output.png");
        if (!ObjectUtils.isEmpty(pngData)) {
            byte[] pngBytes = pngData.getBytes();
            java.nio.file.Files.write(java.nio.file.Path.of(imagePath), pngBytes);
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        /* Go-Mirai-Client守护进程 */
        ThreadUtil.execute(() -> daemonService.startClientDaemon());
    }
}
