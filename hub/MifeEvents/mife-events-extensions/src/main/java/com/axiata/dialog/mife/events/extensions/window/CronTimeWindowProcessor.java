package com.axiata.dialog.mife.events.extensions.window;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.wso2.siddhi.core.config.SiddhiContext;
import org.wso2.siddhi.core.event.StreamEvent;
import org.wso2.siddhi.core.event.in.InEvent;
import org.wso2.siddhi.core.event.in.InListEvent;
import org.wso2.siddhi.core.query.QueryPostProcessingElement;
import org.wso2.siddhi.core.query.processor.window.WindowProcessor;
import org.wso2.siddhi.core.util.collection.queue.SiddhiQueue;
import org.wso2.siddhi.core.util.collection.queue.SiddhiQueueGrid;
import org.wso2.siddhi.core.util.collection.queue.scheduler.SchedulerSiddhiQueueGrid;
import org.wso2.siddhi.query.api.definition.AbstractDefinition;
import org.wso2.siddhi.query.api.expression.Expression;
import org.wso2.siddhi.query.api.expression.constant.StringConstant;
import org.wso2.siddhi.query.api.extension.annotation.SiddhiExtension;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@SiddhiExtension(namespace = "mife", function = "cron")
public class CronTimeWindowProcessor extends WindowProcessor {

    static final Logger log = Logger.getLogger(CronTimeWindowProcessor.class);
    private Set<String> newEventKeyList;
    private SiddhiQueue<StreamEvent> window;
    private Scheduler scheduler;
    private int eventKeyAttributePosition = 0;
    private String jobName;
    private String jobGroup;

    @Override
    protected void processEvent(InEvent event) {
        acquireLock();
        try {
            log.info(event);
            newEventKeyList.add((String) event.getData(eventKeyAttributePosition));
//            window.put(new RemoveEvent(event, -1));
            nextProcessor.process(event);
        } finally {
            releaseLock();
        }
    }

    @Override
    protected void processEvent(InListEvent listEvent) {
        acquireLock();
        try {
            log.info(listEvent);
            for (int i = 0, activeEvents = listEvent.getActiveEvents(); i < activeEvents; i++) {
                newEventKeyList.add((String) listEvent.getEvent(i).getData(eventKeyAttributePosition));
            }
            nextProcessor.process(listEvent);
        } finally {
            releaseLock();
        }
    }

    @Override
    public Iterator<StreamEvent> iterator() {
        return window.iterator();
    }

    @Override
    public Iterator<StreamEvent> iterator(String predicate) {
        if (siddhiContext.isDistributedProcessingEnabled()) {
            return ((SchedulerSiddhiQueueGrid<StreamEvent>) window).iterator(predicate);
        } else {
            return window.iterator();
        }
    }

    @Override
    protected Object[] currentState() {
        log.debug("Running currentState");
        return new Object[]{window.currentState(), newEventKeyList};
//        return window.currentState();
    }

    @Override
    protected void restoreState(Object[] data) {
        log.debug("Running restoreState");
//        window.restoreState(data);
        window.restoreState((Object[]) data[0]);
        newEventKeyList = (Set<String>) data[1];
//        window.reSchedule();
    }

    @Override
    protected void init(Expression[] parameters, QueryPostProcessingElement nextProcessor, AbstractDefinition
            streamDefinition, String elementId, boolean async, SiddhiContext siddhiContext) {

        String cronString = ((StringConstant) parameters[0]).getValue();
        if (parameters[1] instanceof StringConstant) {
            String eventKeyAttributeName = ((StringConstant) parameters[1]).getValue();
            eventKeyAttributePosition = streamDefinition.getAttributePosition(eventKeyAttributeName);
        }

        newEventKeyList = new HashSet<String>();

        if (this.siddhiContext.isDistributedProcessingEnabled()) {
            window = new SiddhiQueueGrid<StreamEvent>(elementId, this.siddhiContext, this.async);
        } else {
            window = new SiddhiQueue<StreamEvent>();
        }
        scheduleCronJob(cronString, elementId);
    }

    @Override
    public void destroy(){
        try {
//            scheduler.shutdown();
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            log.warn("Error while scheduler shutdown", e);
        }

    }

    private void scheduleCronJob(String cronString, String elementId) {
        try {
            SchedulerFactory schedFact = new StdSchedulerFactory();
            scheduler = schedFact.getScheduler();
            scheduler.start();

            JobDataMap dataMap = new JobDataMap();
            dataMap.put("windowProcessor", this);

            jobName = "EventRemoverJob_" + elementId;
            jobGroup = "group1";
            JobDetail job = org.quartz.JobBuilder.newJob(EventRemoverJob.class)
					.withIdentity(jobName, jobGroup)
                    .usingJobData(dataMap)
					.build();

            Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
					.withIdentity("EventRemoverTrigger_" + elementId, "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule(cronString))
					.build();

            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            log.error("Error while instantiating quartz scheduler", e);
        }
    }

    public Set<String> getNewEventKeyList() {
        return newEventKeyList;
    }
}
