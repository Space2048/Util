import ExcelDeal.Entity.Student;
import ExcelDeal.Entity.Teacher;
import ExcelDeal.Entity.User;
import ExcelDeal.IOExcel;
import ExcelDeal.IOMoreSheet;

import javax.print.attribute.HashAttributeSet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author Bailibo
 * @time 21/12/29
 * 基本数据导入导出Test
 */

public class Test1{

    private Class clazz;

    private List<Class> classList;

    public Test1(Class clazz){
        this.clazz =clazz;
    }

    public Test1(List<Class> classList){
        this.classList = classList;
    }
    public List<Object> OutTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        List<Object> objectList = new ArrayList<>(3);

        if(clazz !=null){
            objectList.add(clazz.newInstance());
            return objectList;
        }

        for (int i = 0; i < classList.size(); i++) {
           objectList.add(classList.get(i).newInstance());
        }

        Object object = objectList.get(0);
        Method method = object.getClass().getMethod("setUserId",Integer.class);
        method.invoke(object,3);

        return objectList;
    }
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

//        Map<String,List> map = new HashMap<>();
//        //先进行数据导入测试
//        IOExcel<User> userIOExcel = new IOExcel<>(User.class);
//        List<User> users = userIOExcel.inputExcel();
//
////        map.put()
//        List<Class> classList = new ArrayList<>(3);
//        classList.add(User.class);
//        classList.add(Student.class);
//        classList.add(Teacher.class);
//        Test1 test0 = new Test1(classList);
//
//        List<Object> list = test0.OutTest();
//        System.out.println(list.get(0));




//        Test1 test1 = new Test1(User.class);
//        User user = (User) test1.OutTest();
//        user.setUserName("Bai");
//        List<Object> list = new ArrayList<>();
//        list.add(user);





//        ExcelParsingImpl ep = new ExcelParsingImpl("C:\\Users\\Administrator\\Desktop\\Test.xlsx");
//
//        List<Object[]> list = ep.inputExcel();
//
//        SolveClass<User> sc = new SolveClass<User>(User.class);
//        List<User> users = sc.toCommon(list);
//
//        //导入测试
//        for (User user: users){
//            System.out.println(user.toString());
//        }
//
//        Field[] fields = User.class.getDeclaredFields();
//        List<Object[]> outList = new ArrayList<Object[]>(users.size());
//        //导出测试
//        for (User user:users) {
//            outList.add(sc.toObectArray(user));
//        }
//
//        Long time = new Date().getTime();
//        String filename = "C:\\Users\\Administrator\\Desktop\\"+String.valueOf(time)+".xlsx";
//        ep.outputExcel(outList);

        IOMoreSheet excel = new IOMoreSheet("Test.xlsx","C:\\Users\\Administrator\\Desktop");
        Map<String,List> map = excel.inputAllSheet(User.class,Student.class,Teacher.class);
        System.out.println(map.get("Sheet1"));
        System.out.println(map.get("Sheet2"));
        System.out.println(map.get("Sheet3"));

        excel.outputAllSheet(map,"C:\\Users\\Administrator\\Desktop");
    }
}
