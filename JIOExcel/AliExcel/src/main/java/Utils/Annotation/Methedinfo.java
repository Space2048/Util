package Utils.Annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Methedinfo {
    String author() default "Unknow";
    String date();
    int reversion() default 1;
    String comments();
}
