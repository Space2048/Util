package Utils.Annotation;

public class AnnotationExample {

    private String name;



    @Methedinfo(author = "Bailibo",date ="21/12/30",comments="测试使用注解" )
    public String noMethed(String name){
        return name;
    }
}
