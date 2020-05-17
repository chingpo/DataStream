package com.app.transfer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.app.input.LogMessage;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;


public class LogMessageSchema implements DeserializationSchema<LogMessage>, SerializationSchema<LogMessage> {

    @Override
    public byte[] serialize(LogMessage message) {
        String str = JSON.toJSONString(message);
        return str.getBytes();
    }

    @Override
    public LogMessage deserialize(byte[] message) throws IOException {
        String jsonString = new String(message);

        try {
            LogMessage<JSON> JSONMessage = parseResult(jsonString);
            if (JSONMessage.getDate() != null) {
                return JSONMessage;
            } else {
                LogMessage<String> debugMessage = parseResult(jsonString);
                return debugMessage;
            }
        } catch (Exception ex) {
            LogMessage<String> debugMessage = parseResult(jsonString);
            return debugMessage;
        }
    }

    private static <T> LogMessage<T> parseResult(String json) {
        return JSON.parseObject(json, new TypeReference<LogMessage<T>>() {
        });
    }

    @Override
    public boolean isEndOfStream(LogMessage nextElement) {
        return false;
    }

    @Override
    public TypeInformation<LogMessage> getProducedType() {
        return TypeInformation.of(LogMessage.class);
    }
}



