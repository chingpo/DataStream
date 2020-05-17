package com.app.stream;

import com.app.input.LogMessage;
import com.app.transfer.LogMessageSchema;
import com.app.transfer.LogMessageWatermarks;
import com.app.util.ConfigUtil;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

public class FlinkEnv {

    public StreamExecutionEnvironment InitStream(String GroupId){
        ParameterTool parameterTool= getParams(GroupId);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        env.enableCheckpointing(5000); // create a checkpoint every 5 seconds
        env.getConfig().setGlobalJobParameters(parameterTool); // make parameters available in the web interface
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        return env;
    }


    public DataStream<LogMessage> AddSource(StreamExecutionEnvironment env,ParameterTool parameterTool){
        FlinkKafkaConsumer010 flinkKafkaConsumer = new FlinkKafkaConsumer010<LogMessage>(parameterTool.getRequired("input-topic"), new LogMessageSchema(), parameterTool.getProperties());
        DataStream<LogMessage> input = env.addSource(flinkKafkaConsumer.assignTimestampsAndWatermarks(new LogMessageWatermarks()));
        return input;
    }


    public ExecutionEnvironment InitSet(String GroupId){
        ParameterTool parameterTool= getParams(GroupId);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        return env;
    }

    public ParameterTool getParams(String GroupId){
       String[] args = new String[]{"--input-topic", ConfigUtil.getValue("kafka.topic"), "--bootstrap.servers", ConfigUtil.getValue("kafka.servers"),
               "--zookeeper.connect", ConfigUtil.getValue("zookeeper.connect"), "--group.id", GroupId, "--winsdows.size", "50"};

      

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        return parameterTool;
    }
}
