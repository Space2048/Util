import ExcelDeal.Entity.User;
import ExcelDeal.IOMoreSheet;
import java.util.List;
import java.util.Map;

/***
 * @author Bailibo
 * @time 21/12/29
 * 基本数据导入导出Test
 */
public class Test1{

    public static void main(String[] args){

        IOMoreSheet excel = new IOMoreSheet("Test.xlsx","C:\\Users\\Administrator\\Desktop");
        Map<String,List> map = excel.inputAllSheet(User.class);
        System.out.println(map.get("Sheet1"));
        excel.outputAllSheet(map,"C:\\Users\\Administrator\\Desktop");

    }
}
