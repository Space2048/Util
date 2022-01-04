package ExcelDeal.Entity;

import ExcelDeal.InExcel;
import ExcelDeal.OutExcel;

import java.util.Date;

@OutExcel(outAddr = "C:\\Users\\Administrator\\Desktop",fileName = "Testannotation")
@InExcel(fileAddr = "C:\\Users\\Administrator\\Desktop\\Test",type = "xlsx")
public class User {
    /**
     * 用户id
     */
    Integer userId;
    /**
     * 用户名
     */
    String userName;
    /**
     * 年龄
     */
    Integer userAge;
    /**
     * 生日
     */
    Date userBirth;
    /**
     *  工作
     */
    String userWork;


    public User() {
    }

    public User(Integer userId, String userName, Integer userAge, Date userBirth, String userWork) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userBirth = userBirth;
        this.userWork = userWork;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserWork() {
        return userWork;
    }

    public void setUserWork(String userWork) {
        this.userWork = userWork;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userBirth=" + userBirth +
                ", userWork='" + userWork + '\'' +
                '}';
    }
}
