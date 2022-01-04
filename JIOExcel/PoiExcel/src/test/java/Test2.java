import ExcelDeal.Entity.Student;
import ExcelDeal.Entity.Teacher;
import ExcelDeal.IOExcel;
import ExcelDeal.Entity.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对改进后 Excel工具类进行测试
 * @author Bailibo
 * @time 21/12/30
 */


public class Test2 {
    public static void main(String[] args) throws IOException, InvalidFormatException {
//        List<Class> classList = new ArrayList<>(3);
//        classList.add(User.class);
//        classList.add(Student.class);
//        classList.add(Teacher.class);
//        //先进行数据导入测试
//        IOExcel<User> userIOExcel = new IOExcel<>(User.class,classList);
//        List<User> inputList = userIOExcel.inputExcel();
//
//        Map<String,List> map = userIOExcel.importAllSheet();
//
//        List list = map.get(0);
//
//        for (int j = 0; j < list.size(); j++) {
//                list.get(j).toString();
//            }

//        for (int i = 0; i < 3; i++) {
//            String name = "Sheet";
//            List list = map.get(name+String.valueOf(i));
//            for (int j = 0; j < list.size(); j++) {
//                list.get(j).toString();
//            }
//        }
//        for(User user:inputList){
//            System.out.println(user.toString());
//        }
//
//
//        IOExcel<User> excel = new IOExcel<>(User.class);
//        if(!excel.outputExcel(inputList)){
//            System.out.println("导出失败!");
//        }
//        System.out.println("导出成功");
    }
}
