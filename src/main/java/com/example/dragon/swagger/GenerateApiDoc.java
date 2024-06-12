package com.example.dragon.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateApiDoc {
    String summary() default "";
    String description() default "";
    String responseCode() default "200";
    String responseDescription() default "";
    Class<?> responseClass();
    String mediaType() default "application/json";
}