package com.example.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author skygge.
 * @Date on 2019-08-13.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ViewInject {
    int getLayoutId() default -1;
}
