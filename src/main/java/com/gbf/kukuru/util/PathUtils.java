package com.gbf.kukuru.util;

import com.gbf.kukuru.SpringMiraiServerApplication;
import lombok.SneakyThrows;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 路径工具类
 *
 * @author ginoko
 * @since 2022-06-10
 */
public class PathUtils {
    /**
     * Windows路径分隔符
     */
    public static String windowsPathSeparator = "\\\\";
    /**
     * Linux路径分隔符
     */
    public static String linuxPathSeparator = "/";

    /**
     * 判断当前系统是否为Windows
     *
     * @return true Windows; false Linux
     */
    public static boolean isWindows() {
        return '\\' == File.separatorChar;
    }

    /**
     * 获取当前系统的路径分隔符
     *
     * @return 路径分隔符
     */
    public static String getPathSeparator() {
        return isWindows() ? windowsPathSeparator : linuxPathSeparator;
    }

    /**
     * 路径分隔符调整并去重
     *
     * @param path 路径
     * @return 去重后的路径
     */
    public static String trimPath(String path) {
        if (isWindows()) {
            path = path.replace(linuxPathSeparator, windowsPathSeparator);
        } else {
            path = path.replace(windowsPathSeparator, linuxPathSeparator);
        }
        return Arrays.stream(path.split(getPathSeparator()))
                .filter(str -> !Objects.equals(str, ""))
                .collect(Collectors.joining(getPathSeparator()));
    }

    /**
     * 拼接路径
     * <p>
     * [head]path1[Separator]path2[Separator]...[Separator]pathN[tail]
     *
     * @param path  路径
     * @param paths 路径变长参数表
     * @return 路径字符串
     */
    public static String addPath(String path, String... paths) {
        StringBuilder builder = new StringBuilder(trimPath(path));
        for (String temp : paths) {
            builder.append(getPathSeparator()).append(trimPath(temp));
        }
        return builder.toString();
    }

    /**
     * 获取本项目jar包所在绝对路径
     *
     * @return 路径字符串
     */
    public static String getProjectJarPath() {
        ApplicationHome home = new ApplicationHome(SpringMiraiServerApplication.class);
        String homePath = home.getSource().getAbsolutePath();
        if (!isWindows()) {
            /* linux环境需要去除末位的jar包名称 */
            homePath = Arrays.stream(homePath.split(getPathSeparator()))
                    .filter(str -> !Objects.equals(str, "") && !str.contains(".jar"))
                    .collect(Collectors.joining(getPathSeparator()));
        }
        return homePath;
    }

    /**
     * 根据资源路径获取其真实的文件路径
     *
     * @param resourcePath 资源路径
     * @return 文件路径
     */
    @SneakyThrows
    public static String getRealPathFromResource(String resourcePath) {
        return new ClassPathResource(resourcePath).getFile().getAbsolutePath();
    }
}
