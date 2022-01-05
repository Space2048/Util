package ExcelDeal.Entity;

import java.util.Date;

/**
 * Test:学生实体
 * @author Bailibo
 * @time 21/12/31
 */
public class    Student {
    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 入学时间
     */
    private Date adTime;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private String studentClass;

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", adTime=" + adTime +
                ", major='" + major + '\'' +
                ", studentClass='" + studentClass + '\'' +
                '}';
    }

    public Student() {

    }

    public Student(Integer studentId, String studentName, String studentNumber, Date adTime, String major, String studentClass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.adTime = adTime;
        this.major = major;
        this.studentClass = studentClass;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Date getAdTime() {
        return adTime;
    }

    public void setAdTime(Date adTime) {
        this.adTime = adTime;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

}
