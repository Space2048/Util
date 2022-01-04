package ExcelDeal.Entity;

/**
 * Test:教师实体类
 * @author Bailibo
 * @time 21/12/31
 */
public class Teacher {

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 教师工号
     */
    private String teacherNumber;

    public Teacher() {
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", teacherNumber='" + teacherNumber + '\'' +
                '}';
    }

    public Teacher(Integer teacherId, String teacherName, String teacherNumber) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }
}
