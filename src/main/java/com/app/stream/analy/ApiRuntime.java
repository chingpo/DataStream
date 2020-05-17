package com.app.stream.analy;

import lombok.Data;

@Data
public class ApiRuntime {

    private long eventTime;
    private String school;
    private String api;
    private String module;
    private long avgtime;

}
