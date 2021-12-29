import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/***
 * Excel导入导出类
 * @author Space2048
 * @time 21/12/28
 */
public class ExcelParsingImpl implements ExcelParsing{

    private String addr = null;
    private List<String[]> list = null; //数据

    ExcelParsingImpl(String Exceladdr, List<String[]> list){
        this.addr = Exceladdr;
        this.list = list;
    }

    ExcelParsingImpl(String Exceladdr){
        this.addr = Exceladdr;
    }

    /***
     * 导出Excel数据
     * @return 导出结果 true 成功 | false 失败
     */
    @Override
    public Boolean outputExcel(){
        if(null == list){     //数据为空导出失败
            System.out.println("Excel导出失败,数据为空");
            return false;
        }

        if(null == addr || "".equals(addr)){
            FileSystemView fsv = FileSystemView.getFileSystemView();
            System.out.println(fsv.getHomeDirectory());;
        }
        /**
         * 没数据 ---- 先编写导入
         */
        return true;
    }

    /***
     * 导入Excel数据
     * @return 导入结果 true 成功 | false 失败
     */
    @Override
    public List<Object[]> inputExcel(){

        try{
            File excel = new File(addr);
            if(excel.isFile() && excel.exists()){   //判断文件是否存在
                String[] split = excel.getName().split("\\.");
                Workbook workbook;

                /***
                 * 判断文件类型
                 */
                if("xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);
                    workbook = new HSSFWorkbook(fis);
                }

                else if("xlsx".equals(split[1])){
                    workbook = new XSSFWorkbook(excel);
                }
                else {
                    System.out.println("文件类型错误");
                    return null;
                }
                /**
                 * 开始解析Excel
                 */

                Sheet sheet = workbook.getSheetAt(0);
                Row row = sheet.getRow(0);
                if(row == null){
                    System.out.println("导入数据，Excel第一行不能为空");
                    return null;
                }


                /**保存数据列表**/
                List<Object[]> list = new ArrayList<Object[]>(sheet.getLastRowNum());

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Object[] objArray = new Object[sheet.getLastRowNum()];
                    row = sheet.getRow(i);
                    for (int j = 0; j < objArray.length; j++) {
                        Cell cell = row.getCell(j);
                        /**
                         * 这里新建对象并设置属性
                         * 然后加入到对象列表中
                         */

                        objArray[j] =getCellValue(row,j);

                    }
                    list.add(objArray);
                }

                return list;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /***
     * 获取单元格数据
     * Ruoyi框架源码
     * @param row
     * @param column
     * @return
     */
    private Object getCellValue(Row row, int column)
    {
        if (row == null)
        {
            return row;
        }
        Object val = "";
        try
        {
            Cell cell = row.getCell(column);
            if (cell != null)
            {
                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)
                {
                    val = cell.getNumericCellValue();
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
                    }
                    else
                    {
                        if ((Double) val % 1 != 0)
                        {
                            val = new BigDecimal(val.toString());
                        }
                        else
                        {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                }
                else if (cell.getCellType() == CellType.STRING)
                {
                    val = cell.getStringCellValue();
                }
                else if (cell.getCellType() == CellType.BOOLEAN)
                {
                    val = cell.getBooleanCellValue();
                }
                else if (cell.getCellType() == CellType.ERROR)
                {
                    val = cell.getErrorCellValue();
                }

            }
        }
        catch (Exception e)
        {
            return val;
        }
        return val;
    }


    /***
     * 为类转换导出的数据
     */

//    public static List<T> toConmmonData(){
//        Field[] filedLine =
//    }
}
