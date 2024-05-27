package com.gbf.kukuru.exception;

/**
 * 错误码接口
 *
 * @author Ginoko
 * @since 2022-05-13
 */
public interface IErrorCode {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
