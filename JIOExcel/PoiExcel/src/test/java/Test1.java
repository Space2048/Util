import ExcelDeal.SolveClass;
import ExcelDeal.User;
import ExcelDeal.impl.ExcelParsingImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

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

        //导入测试
        for (User user: users){
            System.out.println(user.toString());
        }

        Field[] fields = User.class.getDeclaredFields();
        List<Object[]> outList = new ArrayList<Object[]>(users.size());
        //导出测试
        for (User user:users) {
            outList.add(sc.toObectArray(user));
        }

        Long time = new Date().getTime();
        String filename = "C:\\Users\\Administrator\\Desktop\\"+String.valueOf(time)+".xlsx";
        ep.outputExcel(outList);
        


    }
}
