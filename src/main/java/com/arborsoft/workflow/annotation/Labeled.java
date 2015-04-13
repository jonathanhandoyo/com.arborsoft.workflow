package com.arborsoft.workflow.annotation;

import com.arborsoft.workflow.constant.Label;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Labeled {
    Label[] value();
}
