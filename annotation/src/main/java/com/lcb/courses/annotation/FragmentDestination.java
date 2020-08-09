package com.lcb.courses.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 用来标记需要Navigation去进行加载或者跳转的
 */
@Target(ElementType.TYPE)
public @interface FragmentDestination {
    String pageUrl();

    boolean needLogin() default false;

    boolean asStarter() default false;
}
