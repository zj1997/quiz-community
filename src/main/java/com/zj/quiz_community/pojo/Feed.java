package com.zj.quiz_community.pojo;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * @author zhaojie
 * @date 2018\10\29 0029 - 21:11
 */
public class Feed {

    private Integer id;
    private Integer userId;
    private Integer type;
    private String data;
    private Date createdDate;

    private JSONObject dataJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        dataJson = JSONObject.parseObject(data);
    }

    //通过key来获取相应部分的值
    public String get(String key){
        return dataJson == null?null:dataJson.getString(key);
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
