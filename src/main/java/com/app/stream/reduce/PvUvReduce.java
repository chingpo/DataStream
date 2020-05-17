package com.app.stream.reduce;

import com.app.stream.analy.PvUv;
import org.apache.flink.api.common.functions.ReduceFunction;

public class PvUvReduce implements ReduceFunction<PvUv> {
    @Override
    public PvUv reduce(PvUv value1, PvUv value2) throws Exception {

        Integer pvcountvalue1 = value1.getPvcount();
        Integer uvcountvalue1 = value1.getUvcount();
        Integer pvcountvalue2 = value2.getPvcount();
        Integer uvcountvalue2 = value2.getUvcount();

        value1.setPvcount(pvcountvalue1 + pvcountvalue2);
        if (uvcountvalue1 + uvcountvalue2>0){
            value1.setUvcount(1);
        }else {
            value1.setUvcount(0);
        }
        return value1;
    }
}
