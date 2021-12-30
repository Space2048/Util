import ExcelDeal.SolveClass;
import ExcelDeal.User;
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

        SolveClass<User> sc = new SolveClass<User>(User.class);
        List<User> users = sc.toCommon(list);


        for (User user: users){
            System.out.println(user.toString());
        }


//        for(Object[] obj:list){
//            for(Object a:obj){
//                System.out.println(a);
//            }
//        }


    }
}
