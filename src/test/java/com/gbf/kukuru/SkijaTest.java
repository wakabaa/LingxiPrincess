package com.gbf.kukuru;

import org.jetbrains.skija.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

public class SkijaTest {
    public static void main(String[] args) {
        Resource fontResource = new ClassPathResource("static/font/思源黑体.ttf");
        try (Surface surface = Surface.makeRasterN32Premul(800, 450);
             Paint paintBackGround = new Paint();
             Paint paintText = new Paint();
             Typeface textFace = Typeface.makeFromFile(fontResource.getFile().getAbsolutePath())) {
            Canvas canvas = surface.getCanvas();
            paintBackGround.setColor(0xFFFFFFFF);
            canvas.drawRRect(RRect.makeXYWH(0, 0, 800, 450, 3, 0, 0, 3), paintBackGround);
            paintText.setColor(0xFF4B4B4B);
            canvas.drawString("测试测试测试测试", 20, 200, new Font(textFace, 20), paintText);
            Image image = surface.makeImageSnapshot();
            Data pngData = image.encodeToData(EncodedImageFormat.PNG);
            if (!ObjectUtils.isEmpty(pngData)) {
                byte[] pngBytes = pngData.getBytes();
                java.nio.file.Files.write(java.nio.file.Path.of("output.png"), pngBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
