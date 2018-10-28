package com.zj.quiz_community.async;


import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojie
 * @date 2018\10\23 0023 - 17:13
 */
public class EventModel {

    private EventType eventType;
    private Integer actorId;
    private Integer entityType;
    private Integer entityId;
    private Integer entityOwerId;

    private Map<String ,String> exts = new HashMap<>();

    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }

    public EventModel() {
    }

    public EventModel setExt(String key, String value){
        exts.put(key,value);
        return this;
    }

    public String getExt(String key){
        return exts.get(key);
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public Integer getActorId() {
        return actorId;
    }

    public EventModel setActorId(Integer actorId) {
        this.actorId = actorId;
        return this;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(Integer entityType) {
        this.entityType = entityType;
        return this;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public Integer getEntityOwerId() {
        return entityOwerId;
    }

    public EventModel setEntityOwerId(Integer entityOwerId) {
        this.entityOwerId = entityOwerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
