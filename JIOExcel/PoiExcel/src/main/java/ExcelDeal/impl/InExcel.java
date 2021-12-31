package ExcelDeal.impl;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InExcel {
    String fileAddr() default "C:\\Downloads";
    String type() default "xlsx";
}
