package com.app.stream.analy;

import lombok.Data;

@Data
public class PvUv {
    private String group;
    private Integer userid;
    private Integer pvcount;
    private Integer uvcount;
    private String timestring;
    private long timestamp;
    private String key;
}
