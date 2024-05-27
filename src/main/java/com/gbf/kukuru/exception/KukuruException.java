package com.gbf.kukuru.exception;

import lombok.Getter;

/**
 * Kukuru机器人业务异常类
 *
 * @author Ginoko
 * @since 2022-05-13
 */
public class KukuruException extends RuntimeException {

    /**
     * 错误码
     */
    @Getter
    private final int code;
    /**
     * 错误信息
     */
    @Getter
    private final String message;

    public KukuruException(IErrorCode code) {
        super(code.getMessage());
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public KukuruException(IErrorCode code, String extraMessage) {
        super(code.getMessage() + extraMessage);
        this.code = code.getCode();
        this.message = code.getMessage() + extraMessage;
    }

    public KukuruException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
