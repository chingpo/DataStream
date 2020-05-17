package com.app.input;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * log fomat
 * manage logs by using log shipper tool -- filebeat
 * https://www.elastic.co/guide/en/beats/filebeat/current/filebeat-getting-started.html
 * @param <T>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogMessage<T> {
    private Date timestamp;
    private JSON filebeat;
    private String group;
    private int userid;
    private int thread_id;
    private String session_id;
    private JSON request;
    private T data;
    private JSON beat;
    private long runtime;
    private String version;
    private String level;
    private Date date;
    private JSON fields;

    @JSONField(name="@timestamp")
    public Date getTimestamp() {
        return timestamp;
    }

    @JSONField(name="@metadata")
    public JSON getFilebeat() {
        return filebeat;
    }

    @JSONField(name="@timestamp")
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @JSONField(name="@metadata")
    public void setFilebeat(JSON filebeat) {
        this.filebeat = filebeat;
    }
}
