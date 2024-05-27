package com.gbf.kukuru.handler;

import lombok.extern.slf4j.Slf4j;
import com.gbf.kukuru.exception.KukuruException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理类
 *
 * @author Ginoko
 * @since 2022-05-13
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Kukuru机器人业务异常处理方法
     *
     * @param e Kukuru机器人业务异常
     */
    @ExceptionHandler(value = KukuruException.class)
    public void KukuruExceptionHandler(KukuruException e) {
        log.error("发生异常[" + e.getCode() + "], 原因: " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * 空指针异常处理方法
     *
     * @param e 空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public void NullPointerExceptionHandler(NullPointerException e) {
        log.error("发生空指针异常! ", e);
        e.printStackTrace();
    }

    /**
     * 未知异常处理方法
     *
     * @param e 异常
     */
    @ExceptionHandler(value = Exception.class)
    public void UnknownExceptionHandler(Exception e) {
        log.error("发生未知异常! ", e);
        e.printStackTrace();
    }
}
