import ExcelDeal.IOExcel;
import ExcelDeal.User;

import java.util.List;

/**
 * 对改进后 Excel工具类进行测试
 * @author Bailibo
 * @time 21/12/30
 */
public class Test2 {
    public static void main(String[] args) {
        //先进行数据导入测试
        IOExcel<User> userIOExcel = new IOExcel<>(User.class);
        List<User> inputList = userIOExcel.inputExcel();

        for(User user:inputList){
            System.out.println(user.toString());
        }

        IOExcel<User> excel = new IOExcel<User>(User.class);
        excel.outputExcel(inputList);

    }
}
