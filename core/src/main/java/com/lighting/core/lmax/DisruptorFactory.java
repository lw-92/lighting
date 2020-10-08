package com.lighting.core.lmax;

import com.lighting.core.flow.FlowEvent;
import com.lighting.core.flow.FlowWorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.commons.lang3.StringUtils;
import java.util.Objects;
import java.util.stream.IntStream;


public class DisruptorFactory {

    public static final String LIGHTING_CORE_SIZE_KEY = "lightCoreSize";

    private static volatile Disruptor defaultDisruptor;

    private static Object lock=new Object();

    /**
     * 获取一个默认的disruptor
     */
    public static Disruptor getDefaultDisruptor() {
        if (!Objects.isNull(defaultDisruptor)) {
            return defaultDisruptor;
        }
        synchronized (lock){
            if(Objects.isNull(defaultDisruptor)){
                String property = System.getProperty(LIGHTING_CORE_SIZE_KEY);
                int coreSize = Runtime.getRuntime().availableProcessors() * 2;
                if (StringUtils.isNotBlank(property)) {
                    coreSize = Integer.parseInt(property);
                }
                defaultDisruptor = createDisruptor(coreSize);
            }
        }
        return defaultDisruptor;

    }

   public static Disruptor createDisruptor(int coreSize){
       Disruptor disruptor = new Disruptor(() -> new FlowEvent<>(), coreSize, DaemonThreadFactory.INSTANCE);
       FlowWorkHandler[] dataFlowHandlers = IntStream.range(0, coreSize).mapToObj(i -> new FlowWorkHandler(i + "")).toArray(FlowWorkHandler[]::new);
       disruptor.handleEventsWithWorkerPool(dataFlowHandlers);
       disruptor.start();
       return disruptor;
   }
}
