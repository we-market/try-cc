package com.wemarket.cc.common.annotation;

import java.lang.annotation.*;

/**
 * Replace sensitive strings
 **/
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface WacMaskData {

    String wholeReplacementString() default "****";

    int[] cursors() default {};

    int[] offsets() default {};

    char replacementChar() default '*';
}
