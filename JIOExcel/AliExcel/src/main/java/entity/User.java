package entity;

import java.util.Date;

/**
 * 测试用实体
 * @author Bailibo
 * @time 21/12/29
 */
public class User {
    /** 用户id**/
    private Integer id;
    /** 用户名**/
    private String name;
    /** 用户年龄**/
    private Integer age;
    /** 用户生日**/
    private Date birth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }


    public User(Integer id, String name, Integer age, Date birth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birth = birth;
    }


    public User() {
    }
}
