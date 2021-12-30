package ExcelDeal;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * 使用反射和泛型完成数据类型转换
 * @author Bailibo
 * @time 21/12/29
 * @param <T>
 */
public class SolveClass<T> {

    public Class<T> clazz;

    public SolveClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    /***
     * 将Object 类型转换成实际类中属性类型
     * @param list 数据类型
     * @return 转换为有类型的数据
     */
    public List<T> toCommon(List<Object[]> list) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        List<T> resultList = new ArrayList<T>(list.size());
        Field[] fields = clazz.getDeclaredFields();

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

    /***
     * 把实体对象属性值变为Object类型，然后存入Object[]数字中
     * @author Bailibo
     * @time 21/12/30
     */
    public Object[] toObectArray(T unKnowobj) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        Object[] objList = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {

            objList[i] = fields[i].get(unKnowobj);

        }
        return objList;
    }

}
