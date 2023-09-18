package com.zdjizhi.common.pojo;

/**
 * @author qidaijie
 * @Package com.zdjizhi.common.pojo
 * @Description:
 * @date 2023/5/911:42
 */
public class Metrics {
    private String name;
    private Tags tags;
    private Fields fields;
    private long timestamp_ms;


    public Metrics(String name, Tags tags, Fields fields, long timestamp_ms) {
        this.name = name;
        this.tags = tags;
        this.fields = fields;
        this.timestamp_ms = timestamp_ms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public long getTimestamp_ms() {
        return timestamp_ms;
    }

    public void setTimestamp_ms(long timestamp_ms) {
        this.timestamp_ms = timestamp_ms;
    }
}
