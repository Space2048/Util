package ExcelDeal.annotations;

import java.lang.annotation.*;

/**自定义导出文件位置，和导出文件名字的注解
 * @author Bailibo
 * @time 21/12/30
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OutExcel {

    String outAddr() default "C:\\Download";

    String fileName() default "null";
}
