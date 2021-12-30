package Utils.IOExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import entity.User;

import java.util.List;
import java.util.Map;

public class Excelreader {
    public void simpleRead(){
        //写法一

        String filename = "C:\\Users\\Administrator\\Desktop\\Test.xlsx";

//        EasyExcel.read(filename, User.class)


        //写法二

        EasyExcel.read(filename, User.class, new ReadListener<User>() {
            /**
             * 单词缓存的数据量
             */
            public static final int BATCH_COUNT =100;

            /**
             *临时存储
             */
            private List<User> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


            @Override
            public void onException(Exception exception, AnalysisContext context) throws Exception {

            }

            @Override
            public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {

            }

            @Override
            public void invoke(User user, AnalysisContext analysisContext) {
                cachedDataList.add(user);
                if(cachedDataList.size() >= BATCH_COUNT){
                    saveData();
                    //存储完清理 list
                }
            }

            @Override
            public void extra(CellExtra extra, AnalysisContext context) {

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }

            @Override
            public boolean hasNext(AnalysisContext context) {
                return false;
            }

            private void saveData(){
                System.out.println(cachedDataList.get(0));
            }
        }).sheet().doRead();

    }
}
