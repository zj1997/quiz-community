package com.zj.quiz_community.pojo;

import java.util.Date;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 15:52
 */
public class Message {

    private Integer id ;
    private Integer fromId;
    private Integer toId;
    private String content;
    private Date createdDate;
    private int hasRead;
    private String conversationId;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {

        if(fromId > toId){
            return String.format("%d_%d",toId,fromId);
        }

        return String.format("%d_%d",fromId,toId);
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
