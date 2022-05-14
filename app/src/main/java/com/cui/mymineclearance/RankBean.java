package com.cui.mymineclearance;

/**
 * @author CHL1963
 * @version 1.0
 * @description: TODO
 * @date 2022/5/11 16:48
 */

public class RankBean {
    String difficult;
    String id;
    String user;
    String ys;


    public RankBean(String difficult,String user, String ys) {
        this.difficult = difficult;
        this.id = id;
        this.user = user;
        this.ys = ys;
    }

    @Override
    public String toString() {
        return "RankBean{" +
                "difficult='" + difficult + '\'' +
                ", id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", ys='" + ys + '\'' +
                '}';
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getYs() {
        return ys;
    }

    public void setYs(String ys) {
        this.ys = ys;
    }
}
