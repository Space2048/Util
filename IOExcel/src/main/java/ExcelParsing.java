import java.util.List;

public interface ExcelParsing {
    /**
     * Excel数据导出
     * @return
     */
    public Boolean outputExcel();

    /**
     * Excel数据导入
     * @return
     */
    public List<Object[]> inputExcel();
}
