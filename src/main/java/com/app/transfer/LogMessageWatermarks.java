package com.app.transfer;

import com.app.input.LogMessage;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;


public class LogMessageWatermarks implements AssignerWithPeriodicWatermarks<LogMessage> {

    private long currentTimestamp = Long.MIN_VALUE;

    @Override
    public long extractTimestamp(LogMessage event, long previousElementTimestamp) {
        // the inputs are assumed to be of format (message,timestamp)
        this.currentTimestamp = event.getTimestamp().getTime();//ms?
        return event.getTimestamp().getTime() ;


    }

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        Watermark watermark = new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);//delay 1s evaluate
        return watermark;
    }

}
