package com.gigaspaces.storm.googleanalytics.bolt.urls;

import backtype.storm.topology.BasicOutputCollector;
import com.gigaspaces.storm.googleanalytics.bolt.generic.TotalRankingsBolt;
import com.gigaspaces.storm.googleanalytics.model.reports.TopUrlsReport;
import com.gigaspaces.storm.googleanalytics.tools.Rankable;

import java.util.LinkedHashMap;

/**
 * @author Oleksiy_Dyagilev
 */
public class TotalUrlRankingsBolt extends TotalRankingsBolt {

    public TotalUrlRankingsBolt(int topN, int emitFrequencyInSeconds) {
        super(topN, emitFrequencyInSeconds);
    }

    @Override
    protected void emitRankings(BasicOutputCollector collector) {
        super.emitRankings(collector);

        LinkedHashMap<String, Long> topReferrals = new LinkedHashMap<>();
        for (Rankable rankable : getRankings().getRankings()) {
            topReferrals.put((String)rankable.getObject(), rankable.getCount());
        }

        TopUrlsReport topUrlsReport = new TopUrlsReport(topReferrals);
        space.write(topUrlsReport);
    }
}