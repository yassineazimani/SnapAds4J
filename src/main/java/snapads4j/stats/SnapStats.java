/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.stats;

import snapads4j.enums.GranularityEnum;
import snapads4j.model.stats.TimeSerieStat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SnapStats implements SnapStatsInterface{

    @Override
    public Optional<TimeSerieStat> getCampaignStats(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity) {
        return Optional.empty();
    }// getCampaignStats()

    @Override
    public Optional<TimeSerieStat> getCampaignStats(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, String breakdown, String test, String dimension, String pivot, String swipeUpAttributionWindow, String viewAttributionWindow, String positionStats, String omitEmpty, String conversionSourceTypes) {
        return Optional.empty();
    }// getCampaignStats()

    @Override
    public Optional<TimeSerieStat> getAdAccountStats(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity) {
        return Optional.empty();
    }// getAdAccountStats()

    @Override
    public Optional<TimeSerieStat> getAdAccountStats(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity, String breakdown, String test, String dimension, String pivot, String swipeUpAttributionWindow, String viewAttributionWindow, String positionStats, String omitEmpty, String conversionSourceTypes) {
        return Optional.empty();
    }// getAdAccountStats()

    @Override
    public Optional<TimeSerieStat> getAdSquadStats(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity) {
        return Optional.empty();
    }// getAdSquadStats()

    @Override
    public Optional<TimeSerieStat> getAdSquadStats(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, String breakdown, String test, String dimension, String pivot, String swipeUpAttributionWindow, String viewAttributionWindow, String positionStats, String omitEmpty, String conversionSourceTypes) {
        return Optional.empty();
    }// getAdSquadStats()

    @Override
    public Optional<TimeSerieStat> getAdStats(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity) {
        return Optional.empty();
    }// getAdStats()

    @Override
    public Optional<TimeSerieStat> getAdStats(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, String breakdown, String test, String dimension, String pivot, String swipeUpAttributionWindow, String viewAttributionWindow, String positionStats, String omitEmpty, String conversionSourceTypes) {
        return Optional.empty();
    }// getAdStats()

}// SnapStats