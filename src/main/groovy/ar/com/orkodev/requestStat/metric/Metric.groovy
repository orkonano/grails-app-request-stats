package ar.com.orkodev.requestStat.metric

import groovy.transform.CompileStatic

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by orko on 14/07/14.
 */
@CompileStatic
class Metric {

    String name
    AtomicLong totalAccess
    AtomicLong timeProcessor
    AtomicLong renderTimeProcessor
    AtomicInteger lastTimeProcessor
    AtomicInteger lastRenderTimeProcessor
    AtomicInteger totalException
    Float lowerBound

    public Metric(){
        timeProcessor = new AtomicLong(0)
        totalAccess = new AtomicLong(0)
        renderTimeProcessor = new AtomicLong(0)
        lastTimeProcessor = new AtomicInteger(0)
        lastRenderTimeProcessor = new AtomicInteger(0)
        totalException = new AtomicInteger(0)
    }

    Long getTotalAccess(){
        return totalAccess.longValue()
    }

    Long getTimeProcessor(){
        return timeProcessor.longValue()
    }

    Long getRenderTimeProcessor(){
        return renderTimeProcessor.longValue()
    }

    Integer getLastTimeProcessor(){
        return lastTimeProcessor.intValue()
    }

    Integer getLastRenderTimeProcessor(){
        return lastRenderTimeProcessor.intValue()
    }

    Integer getTotalException(){
        return totalException.intValue()
    }

    /**
     * add the proccess time and add total access
     * @param time
     */
    void addTimeProcessor(long time){
        timeProcessor.addAndGet(time)
        lastTimeProcessor.set((int)time)
    }

    void incrementAccess(){
        totalAccess.addAndGet(1)
    }

    void addRenderTimeProcessor(long time){
        renderTimeProcessor.addAndGet(time)
        lastRenderTimeProcessor.set((int)time)
    }

    void addException(){
        totalException.incrementAndGet()
    }

    Double getAvg(){
        totalAccess.get() != 0 ? (Double)(timeProcessor.get().longValue() / totalAccess.get().longValue()) : 0D
    }

    Long getTotalTimeProcessor(){
        timeProcessor.get().longValue() + renderTimeProcessor.get().longValue()
    }

    Long getLastTotalTimeProcessor(){
        lastTimeProcessor.get().longValue() + lastRenderTimeProcessor.get().longValue()
    }

    Double getTotalAvg(){
        totalAccess.get() != 0 ? (Double)(totalTimeProcessor / totalAccess.get().longValue()) : 0D
    }

    Double getRenderAvg(){
        totalAccess.get() != 0 ? (Double)(renderTimeProcessor.get().longValue() / totalAccess.get().longValue()) : 0D
    }

    Double getExceptionPercentage(){
        exceptionsTotalAccessRelationship() * 100
    }

    private Double exceptionsTotalAccessRelationship(){
        totalAccess.get() != 0 ? (Double)(totalException.get().longValue() / totalAccess.get().longValue()) : 0D
    }

    Boolean isOverBound(){
        exceptionsTotalAccessRelationship() > this.lowerBound
    }
}
