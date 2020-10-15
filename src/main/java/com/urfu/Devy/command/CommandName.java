package main.java.com.urfu.Devy.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandName {
    String name();
    String info() default "";
    String detailedInfo() default "no detail info about this command";
}
