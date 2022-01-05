package ExcelDeal;

/**
 * ExcelDeal.impl.Excel 类，保存Excel基本信息
 * 只支持 文件中有一个sheet
 */
@Deprecated
public class Excel {
    /**
     * ExcelDeal.impl.Excel 文件地址
     */
    String addr;
    /**
     * Excel文件名
     */
    String name;
    /***
     * Excel文件中有效行数
     */
    Integer rowCount;
    /***
     * Excel文件中有效列数
     */
    Integer colCount;
    /**
     * Excel文件类型
     * type ".xls" ".xlsx"
     */
    String type;

    public Excel() {

    }

    public Excel(String addr, String name, Integer rowCount, Integer colCount, String type) {
        this.addr = addr;
        this.name = name;
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getColCount() {
        return colCount;
    }

    public void setColCount(Integer colCount) {
        this.colCount = colCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
