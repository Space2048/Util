package ExcelDeal;

import java.util.List;
import java.util.Map;

/**
 * Excel导入导出接口
 * @author Bailibo
 * @time 22/1/5
 */
public interface ExcelInterface {
    /**
     * 导出Excel
     * @param map 导出的对象
     * @param fileAddr 文件地址
     * @return true成功 false失败
     */
    boolean outputAllSheet(Map<String, List> map, String fileAddr);

    /**
     * 导出Excel
     * @param classes 可变参数 导出对象的类型
     * @return 字符串对列表的HashMap
     */
     Map<String,List> inputAllSheet(Class... classes);
}
