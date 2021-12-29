import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Test1 {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        ExcelParsingImpl ep = new ExcelParsingImpl("C:\\Users\\Administrator\\Desktop\\Test.xlsx");

        List<Object[]> list = ep.inputExcel();

        SolveClass<User> sc = new SolveClass<User>();

        sc.toCommon(list);
    }
}
