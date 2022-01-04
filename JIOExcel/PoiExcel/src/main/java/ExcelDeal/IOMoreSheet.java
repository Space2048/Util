package ExcelDeal;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 多个sheet文件处理
 * @author Bailibo
 * @time 22/1/4
 */
public class IOMoreSheet {
    /**
     * sheet数
     */
    Integer sheetNum;
    /**
     * sheet名List
     * 最大导入10个sheet
     */
    List<String> sheetName = new ArrayList(10);
    /**
     * 文件名
     */
    String fileName;
    /**
     * 文件地址
     */
    String fileAddr;
    /**
     * 类列表
     */
    List<Class> classList;

    /**
     * 日志
     */
    final Logger ioallsheetlogger = LoggerFactory.getLogger(IOMoreSheet.class);


    /**
     * 导入使用构造函数
     * @param fileName  文件名
     * @param fileAddr 文件地址
     */
    public IOMoreSheet(String fileName,String fileAddr){
        this.fileName = fileName;
        this.fileAddr = fileAddr;
    }

    /**
     * 多Sheet导入
     * @param classes
     * @return
     */
    public Map<String,List> inputAllSheet(Class... classes){

        Map<String,List> resultMap = new HashMap();

        for (int i = 0; i < classes.length; i++) {
            List list = inputExcel(i,classes[i]);
            if(null == list){
                ioallsheetlogger.error("inputExcel错误");
                return null;
            }
            resultMap.put(sheetName.get(i),list);
        }
        if(null != resultMap){
            return resultMap;
        }
        return null;
    }

    public boolean outputAllSheet(Map<String,List> map,String fileAddr){
        this.fileAddr = fileAddr;
        if(null == map){
            return false;
        }

        XSSFWorkbook book = new XSSFWorkbook();
        for(String sheetName:map.keySet()){
            List list = map.get(sheetName);
            List<Object[]> dealList = new ArrayList<>(list.size());
            Field[] declaredFields = list.get(0).getClass().getDeclaredFields();
            for (int i = 0; i < list.size(); i++) {
                Object[] objects = new Object[declaredFields.length];
                for (int j = 0; j < declaredFields.length; j++) {
                    try {
                        declaredFields[j].setAccessible(true);
                        objects[j] = declaredFields[j].get(list.get(i));
                    } catch (IllegalAccessException e) {
                        ioallsheetlogger.error(String.valueOf(e));
                    }
                }
                dealList.add(objects);
            }

            XSSFSheet sheet = book.createSheet(sheetName);
            for (int row = 0; row < dealList.size(); row++) {
                XSSFRow sheetRow = sheet.createRow(row);
                for (int col = 0; col < dealList.get(0).length; col++) {
                    sheetRow.createCell(col).setCellValue(String.valueOf(dealList.get(row)[col]));
                }
            }

        }

        //写入文件
        //时间戳命名
        Date date = new Date();
        fileName = String.valueOf(date.getTime());
        String addr  = fileAddr + "\\"+fileName+".xlsx";
        try{
            book.write(new FileOutputStream(addr));
            book.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return true;
    }

    /**
     * 单sheet导入
     * @param sheetNum sheet编号 0开始
     * @return 对象列表
     */
    private List<Object> inputExcel(int sheetNum,Class clazz) {

        if(null == fileName){
            return null;
        }
        String addr;
        addr = fileAddr+"\\"+fileName;

        try{
            File excel = new File(addr);
            //判断文件是否存在
            if(excel.isFile() && excel.exists()){
                String[] split = excel.getName().split("\\.");
                Workbook workBook;

                /**判断文件类型
                 *
                 */

                if("xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);
                    workBook = new HSSFWorkbook(fis);
                }

                else if("xlsx".equals(split[1])){
                    workBook = new XSSFWorkbook(excel);
                }
                else {
                    ioallsheetlogger.error("导入文件类型错误");
                    return null;
                }

                /**
                 * 开始解析Excel
                 */

                Sheet sheet = workBook.getSheetAt(sheetNum);
                if(null != sheet.getSheetName()){
                    this.sheetName.add(sheet.getSheetName());
                }
                else {
                    this.sheetName.add("undefine");
                }
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
                        objArray[j] =getCellValue(row,j);
                    }
                    list.add(objArray);
                }

                //定义结果列表
                List<Object> resultList;
                try{
                    resultList = toCommon(list,clazz);
                }catch (Exception e){
                    ioallsheetlogger.error(String.valueOf(e));
                    return null;
                }

                return resultList;
            }
        } catch (Exception e){
            ioallsheetlogger.error(String.valueOf(e));
            return null;
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
     * 将Object 类型转换成实际类中属性类型
     * @param list 数据类型
     * @return 转换为有类型的数据
     */
    private List<Object> toCommon(List<Object[]> list,Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //获取泛型类的属性
        List<Object> resultList = new ArrayList(list.size());
        Field[] fields = clazz.getDeclaredFields();

        //通过判断属性的类型进行强制类型转换
        for (int i = 0; i < list.size(); i++) {
            Object obj = (Object) clazz.newInstance();
            for (int j = 0; j < list.get(i).length; j++) {

                if (fields[j].getType() == Integer.class) {
                    Integer result = Integer.valueOf(String.valueOf(list.get(i)[j]));
                    fields[j].setAccessible(true);
                    fields[j].set(obj, result);
                } else if (fields[j].getType() == Double.class) {
                    Double result = (Double) list.get(i)[j];
                    fields[j].setAccessible(true);
                    fields[j].set(obj, result);
                } else if (fields[j].getType() == String.class) {
                    String result = (String) list.get(i)[j];
                    fields[j].setAccessible(true);
                    fields[j].set(obj, result);
                } else if (fields[j].getType() == Date.class) {
                    Date result = (Date) list.get(i)[j];
                    fields[j].setAccessible(true);
                    fields[j].set(obj, result);
                } else {
                    fields[j].set(obj, null);
                }

            }
            resultList.add(obj);
        }

        return resultList;
    }

}
