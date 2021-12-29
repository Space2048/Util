package ExcelDeal.impl;

import ExcelDeal.ExcelParsing;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ExcelDeal.ExcelParsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/***
 * Excel导入导出类
 * @author Space2048
 * @time 21/12/28
 */
public class ExcelParsingImpl implements ExcelParsing {
    /**
     * Excel文件位置
     */
    private String addr = null;
    /**
     * 需要导入的数据
     */
    private List<Object[]> list = null; //数据

    /**
     * ExcelDeal.impl.Excel 导出位置
     */
    private String outAddr;

    ExcelParsingImpl(String Exceladdr, List<Object[]> list){
        this.addr = Exceladdr;
        this.list = list;
    }

    public ExcelParsingImpl(String Exceladdr){
        this.addr = Exceladdr;
    }

    /***
     * 导出Excel数据
     * @return 导出结果 true 成功 | false 失败
     */
    @Override
    public Boolean outputExcel(String configdir,List<Object[]> list){
        if(null == list){     //数据为空导出失败
            System.out.println("Excel导出失败,数据为空");
            return false;
        }

        /**Spring 使用
         *
         */
//        @Value("${ExcelTool.output.dir}")
//        String configdir;

        /**
         * 创建表结构设置数据
         */
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("sheet1");
        for (int row = 0; row < list.size(); row++) {
            XSSFRow sheetRow = sheet.createRow(row);
            for (int col = 0; col < list.get(0).length; col++) {
                sheetRow.createCell(col).setCellValue(String.valueOf(list.get(row)[col]));
            }
        }

        /***
         * 写入文件
         */
        try{
            book.write(new FileOutputStream(configdir));
            book.close();
        }catch (Exception e){
            System.out.println(e);
        }

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
            /**判断文件是否存在
             *
             */
            if(excel.isFile() && excel.exists()){
                String[] split = excel.getName().split("\\.");
                Workbook workbook;

                /**判断文件类型
                 *
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
                    Object[] objArray = new Object[row.getLastCellNum()];
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
     * @param row 获取数据的行
     * @param column 数据列数
     * @return object对象
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
                        val = DateUtil.getJavaDate((Double) val); // POI ExcelDeal.impl.Excel 日期格式转换
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
