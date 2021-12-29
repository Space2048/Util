package ExcelDeal;

import java.util.List;

public interface ExcelParsing {
    /**
     * Excel数据导出
     * @return true 成功 | false 失败
     */
    public Boolean outputExcel(String configdir,List<Object[]> list);

    /**
     * Excel数据导入
     * @return 结果列表 List<Object[]>
     */
    public List<Object[]> inputExcel();
}