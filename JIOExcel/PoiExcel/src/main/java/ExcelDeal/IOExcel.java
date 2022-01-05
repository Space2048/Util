package ExcelDeal;

import ExcelDeal.annotations.InExcel;
import ExcelDeal.annotations.OutExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 重新编写Excel导入导出，集成以前的SolveClass类
 * 直接在本类中集成实现,同时添加注解自定义导出
 * @author Bailibo
 * @time 21/12/30
 */
@Deprecated
public class IOExcel<T>{

    /* Excel file name */
    private String fileName;

    //本地存放位置
    private String localAddr = "C:\\Download";

    //Excel 文件类型 .xls  .xlsx
    private String type;

    //最大处理行数
    private Integer maxDealLine;

    //数据对象类型
    Class<T> clazz;

    //日志
    final Logger logger = LoggerFactory.getLogger(IOExcel.class);


    public IOExcel(Class<T> clazz){
        this.clazz = clazz;
    }

    public IOExcel(Class<T> clazz,String fileName,String localAddr,String type){
        this.clazz = clazz;
        this.fileName = fileName;
        this.localAddr = localAddr;
        this.type = type;
    }

    /**
     * 数据导出函数
     * @param list 导出对象列表
     * @return true 成功 | false 失败
     */
    public Boolean outputExcel(List<T> list) {

        //列表为空直接退出
        if(null == list){
            return false;
        }

        //把List<T>转为 List<Object[]>
        List<Object[]> dealList = new ArrayList<Object[]>(list.size());
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < list.size(); i++) {
            Object[] objects = new Object[declaredFields.length];
            for (int j = 0; j < declaredFields.length; j++) {
                try {
                    objects[j] = declaredFields[j].get(list.get(i));
                } catch (IllegalAccessException e) {
                    logger.error(String.valueOf(e));
                }
            }
            dealList.add(objects);
        }

        // Check whether annotation exist then edit address
        if(clazz.isAnnotationPresent(OutExcel.class)){
            //get annotion OutExcel
            OutExcel outExcel = clazz.getAnnotation(OutExcel.class);
            localAddr = outExcel.outAddr();
            fileName = outExcel.fileName();
            if("null".equals(fileName)){
                fileName = null;
            }
        }

        //Determine whether the data is empty
        if(null == dealList || null == dealList.get(0)[0]){
            logger.debug("数据为空，或数据格式不对");
            return false;
        }

        //Create table structure and save data
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("sheet1");
        for (int row = 0; row < dealList.size(); row++) {
            XSSFRow sheetRow = sheet.createRow(row);
            for (int col = 0; col < dealList.get(0).length; col++) {
                sheetRow.createCell(col).setCellValue(String.valueOf(dealList.get(row)[col]));
            }
        }

        //写入文件
        if(null == fileName){
            //时间戳命名
            Date date = new Date();
            fileName = String.valueOf(date.getTime());
        }
        String addr  = localAddr + "\\"+fileName+".xlsx";
        try{
            book.write(new FileOutputStream(addr));
            book.close();
        }catch (Exception e){
            System.out.println(e);
        }

        return true;

    }

    /**
     * 数据导入函数
     * @param sheetNum sheet号
     * @return 返回对象列表
     */
    public List<T> inputExcel(int sheetNum) {
        //判断是否使用了注解
        if(clazz.isAnnotationPresent(InExcel.class)){
            InExcel inExcel = clazz.getAnnotation(InExcel.class);
            String addr = inExcel.fileAddr();
            type = inExcel.type();
            String sepline = "\\\\";
            addr = addr.replaceAll(sepline,"/");
            String[] str = addr.split("/");
            fileName = str[str.length-1];
            String str2 ="/"+fileName;
            localAddr = addr.replace(str2,"");

            System.out.println(addr);
            System.out.println("fileName" +fileName+" type: "+type+" localdir: "+localAddr);
        }

        if(null == fileName){
            return null;
        }

        String addr;
        addr = localAddr + "\\" +fileName+"."+type;

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
                    this.type = "xls";
                    FileInputStream fis = new FileInputStream(excel);
                    workBook = new HSSFWorkbook(fis);
                }

                else if("xlsx".equals(split[1])){
                    this.type = "xlsx";
                    workBook = new XSSFWorkbook(excel);
                }
                else {
                    logger.error("导入文件类型错误");
                    return null;
                }

                /**
                 * 开始解析Excel
                 */

                Sheet sheet = workBook.getSheetAt(sheetNum);
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
                List<T> resultList;
                try{
                    resultList = toCommon(list);
                }catch (Exception e){
                    logger.error(String.valueOf(e));
                    return null;
                }

                return resultList;
            }
        } catch (Exception e){
            logger.error(String.valueOf(e));
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
    private List<T> toCommon(List<Object[]> list) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //获取泛型类的属性
        List<T> resultList = new ArrayList<T>(list.size());
        Field[] fields = clazz.getDeclaredFields();

        //通过判断属性的类型进行强制类型转换
        for (int i = 0; i < list.size(); i++) {
            T obj = clazz.newInstance();
            for (int j = 0; j < list.get(i).length; j++) {

                if (fields[j].getType() == Integer.class) {
                    Integer result = Integer.valueOf(String.valueOf(list.get(i)[j]));
                    fields[j].set(obj, result);
                } else if (fields[j].getType() == Double.class) {
                    Double result = (Double) list.get(i)[j];
                    fields[j].set(obj, result);
                } else if (fields[j].getType() == String.class) {
                    String result = (String) list.get(i)[j];
                    fields[j].set(obj, result);
                } else if (fields[j].getType() == Date.class) {
                    Date result = (Date) list.get(i)[j];
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
