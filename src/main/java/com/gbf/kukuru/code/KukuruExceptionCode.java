package com.gbf.kukuru.code;

import com.gbf.kukuru.exception.IErrorCode;

/**
 * 机器人业务异常错误码 枚举类
 */
public enum KukuruExceptionCode implements IErrorCode {
    UNKNOWN_ERROR(600, "未知错误！"),
    UNKNOWN_BILIBILI_DYNAMIC_TYPE(601, "未知的动态类型！"),
    UNEXPECT_BILIBILI_DYNAMIC_TYPE(602, "未期望的动态类型！"),
    DOWNLOAD_FILE_FAILED(603, "下载文件失败！"),
    TOO_LONG_CONTENT(604, "内容过长！"),
    /* 枚举终止的占位 */
    EOF(999, "EOF");

    KukuruExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误信息
     */
    private final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
