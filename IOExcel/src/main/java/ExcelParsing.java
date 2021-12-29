import java.util.List;

public interface ExcelParsing {
    /**
     * Excel数据导出
     * @return
     */
    public Boolean outputExcel(String configdir,List<Object[]> list);

    /**
     * Excel数据导入
     * @return
     */
    public List<Object[]> inputExcel();
}
