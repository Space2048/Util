import ExcelDeal.impl.ExcelParsingImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/***
 * @author Bailibo
 * @time 21/12/29
 * 基本数据导入导出Test
 */

public class Test1 {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        ExcelParsingImpl ep = new ExcelParsingImpl("C:\\Users\\Administrator\\Desktop\\Test.xlsx");

        List<Object[]> list = ep.inputExcel();

        ep.outputExcel("C:\\Users\\Administrator\\Desktop\\out2.xlsx",list);


//        for(Object[] obj:list){
//            for(Object a:obj){
//                System.out.println(a);
//            }
//        }


    }
}
