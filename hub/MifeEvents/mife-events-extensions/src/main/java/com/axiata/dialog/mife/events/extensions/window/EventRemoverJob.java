package com.axiata.dialog.mife.events.extensions.window;

import com.axiata.dialog.mife.events.extensions.MifeEventsExtensionsConstants;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.wso2.siddhi.core.event.in.InEvent;

public class EventRemoverJob implements Job {

    static final Logger log = Logger.getLogger(EventRemoverJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("Running Event Remover Job");

//            CronTimeWindowProcessor window = (CronTimeWindowProcessor) schedulerContext.get("windowProcessor");
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        CronTimeWindowProcessor window = (CronTimeWindowProcessor) dataMap.get("windowProcessor");

        for (String eventKey : window.getNewEventKeyList()) {
            InEvent resetEvent = new InEvent(MifeEventsExtensionsConstants.SPEND_LIMIT_SUMMING_STREAM, System
                    .currentTimeMillis(), new Object[]{eventKey, 0.0, true});
            window.processEvent(resetEvent);
        }
        window.getNewEventKeyList().clear();

    }
}
