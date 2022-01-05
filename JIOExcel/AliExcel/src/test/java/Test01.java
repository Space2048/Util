import Utils.GenericOp.BandData;
import entity.User;

import java.lang.reflect.Field;

public class Test01 {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BandData<User> bd = new BandData<User>(User.class);
        bd.studyWay();

    }
}
