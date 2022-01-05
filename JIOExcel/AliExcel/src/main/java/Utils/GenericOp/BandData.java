package Utils.GenericOp;

import entity.User;

import java.lang.reflect.Method;

public class BandData<T>{
    //Class<User> clazz;

    Class<T> clazz;

    public BandData(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void studyWay() throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        T obj = clazz.newInstance();
        Method method  = clazz.getDeclaredMethod("getId");
        System.out.println(method);
    }


}
