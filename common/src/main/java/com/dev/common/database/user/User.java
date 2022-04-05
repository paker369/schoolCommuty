package com.dev.common.database.user;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author long.guo
 * @since 1/29/21
 */
@Entity
public class User {
    @PrimaryKey
    public Long id = null;
    public String nickName;
    public String name;
    public String phone;
    public String password;
    public String headImage;
    public String address;
    public String birthday;
    public int age;
    public int gender;//0男1女
    public boolean isAdmin;
    public String school;

    public User() {

    }

    // 学生登录
    @Ignore
    public User(String phone, String password, String nickName, String birthday, int age, int gender) {
        this.phone = phone;
        this.password = password;
        this.birthday = birthday;
        this.age = age;
        this.gender = gender;
        if (nickName == null || nickName.isEmpty()) {
            this.nickName = "用户" + System.currentTimeMillis() / 1000;
        } else {
            this.nickName = nickName;
        }
    }

    @Ignore
    public User(String phone, String password, String headImage, String name, String nickName, String birthday, String address, int age, int gender) {
        this.nickName = nickName;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.headImage = headImage;
        this.address = address;
        this.birthday = birthday;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", headImage='" + headImage + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
