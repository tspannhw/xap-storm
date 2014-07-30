package com.gigaspaces.storm.googleanalytics.feeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Mykola_Zalyayev
 */
@Component
public class FeederStarter {

    @Autowired
    private PageViewFeeder feeder;

    @Autowired
    private HttpRequestSender sender;

    @Autowired
    private RequestCountHelper countHelper;



    public void start(String host) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        FeederTask feederTask = new FeederTask(host);

        long defaultDelay = (long) (Math.random() * 100-90)+90;
        ScheduledFuture<?> sf = executorService.scheduleAtFixedRate(feederTask, defaultDelay, defaultDelay, TimeUnit.MILLISECONDS);
    }

    public class FeederTask implements Runnable {

        private String host;

        public FeederTask(String host) {
            this.host = host;
        }

        @Override
        public void run() {
            try {
                int nextCount = countHelper.nextCount();
                sender.sendRequest(feeder.nextRequestsList(nextCount), host);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
