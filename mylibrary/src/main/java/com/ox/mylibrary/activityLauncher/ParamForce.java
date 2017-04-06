package com.ox.mylibrary.activityLauncher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ox on 16/11/15.
 * 启动activity 必须要的 参数，仅作为标记
 * 只可以修饰 String 类型的 属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ParamForce {
}
