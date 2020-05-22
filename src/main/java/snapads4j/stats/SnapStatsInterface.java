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

import snapads4j.enums.*;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.stats.TimeSerieStat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SnapStatsInterface {

    Optional<TimeSerieStat> getCampaignStats(String oAuthAccessToken, String campaignID, Date startTime, Date endTime,
                                             GranularityEnum granularity) throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, SnapResponseErrorException;

    Optional<TimeSerieStat> getCampaignStats(String oAuthAccessToken, String campaignID, Date startTime, Date endTime,
                                             GranularityEnum granularity, List<String> fields,
                                             BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                             ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                             Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, SnapResponseErrorException;

    Optional<TimeSerieStat> getAdAccountStats(String oAuthAccessToken, String adAccountID, Date startTime, Date endTime,
                                              GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

    Optional<TimeSerieStat> getAdAccountStats(String oAuthAccessToken, String adAccountID, Date startTime, Date endTime,
                                              GranularityEnum granularity, BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                              ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                              Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapExecutionException, SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException;

    Optional<TimeSerieStat> getAdSquadStats(String oAuthAccessToken, String adSquadID, Date startTime, Date endTime,
                                            GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

    Optional<TimeSerieStat> getAdSquadStats(String oAuthAccessToken, String adSquadID, Date startTime, Date endTime,
                                            GranularityEnum granularity, List<String> fields,
                                            BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                            ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                            Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapExecutionException, SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException;

    Optional<TimeSerieStat> getAdStats(String oAuthAccessToken, String adID, Date startTime, Date endTime,
                                       GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

    Optional<TimeSerieStat> getAdStats(String oAuthAccessToken, String adID, Date startTime, Date endTime,
                                       GranularityEnum granularity, List<String> fields,
                                       BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                       ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                       Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException;

    Optional<TimeSerieStat> getPixelDomainsStats(String oAuthAccessToken, String pixelID) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException
    ;

    Optional<TimeSerieStat> getPixelSpecificDomainStats(String oAuthAccessToken, String pixelID, String domain, Date startTime, Date endTime, GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException
    ;

    Optional<TimeSerieStat> getPixelSpecificDomainStats(String oAuthAccessToken, String pixelID, String domain, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                                        ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                                        Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException
    ;
}// SnapStatsInterface
