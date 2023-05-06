package com.github.wieceslaw.summer.annotations;

import java.lang.annotation.*;


/**
 * Used to extend {@code @Config} annotations
 * @author   wieceslaw
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Extend {
    Class<?>[] value();
}
