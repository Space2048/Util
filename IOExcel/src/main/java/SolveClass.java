import org.apache.commons.lang3.SerializationUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/***
 * 使用反射和泛型完成数据类型转换
 * @param <T>
 */
public class SolveClass<T>{

    public Class<T> clazz;

    /***
     * 将Object 类型转换成实际类中属性类型
     * @param list
     * @return
     */
    public List<T> toCommon( List<Object[]> list) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        /***
         * 创建一个实际的泛型类对象
         * 转换数据存入对象
         * 将对象放入列表
         * 返回列表
         */

        Class<T> clazz = null;
        T obj = null;

        Type superclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = null;
        if (superclass instanceof ParameterizedType) {
            parameterizedType = (ParameterizedType) superclass;
            Type[] typeArray = parameterizedType.getActualTypeArguments();
            if (typeArray != null && typeArray.length > 0) {
                clazz = (Class<T>) typeArray[0];
                obj = clazz.newInstance();
            }
        }

        Field[] fields = clazz.getDeclaredFields();

//        Constructor constructor = clz.getConstructor();
        /***
         * 判断属性类型并转换类型
         */
        List<T> resultList = new ArrayList<T>(list.size());

        int lisIndex = 0;
        int objIndex = 0;
        for(Object[] ls:list){
            for (Field field:fields){

                T obj_use = clazz.newInstance();

                if (field.getGenericType().toString().equals("class java.lang.String")){
                    field.set(obj,(String)list.get(lisIndex)[objIndex]);
                }
                objIndex++;
            }
            lisIndex++;
        }

        return resultList;

    }
}
