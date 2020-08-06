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
import snapads4j.model.Pagination;
import snapads4j.model.stats.TimeSerieStat;

import java.util.Date;
import java.util.List;

public interface SnapStatsInterface {

    List<Pagination<TimeSerieStat>> getCampaignStats(String oAuthAccessToken, int limit, String campaignID, Date startTime, Date endTime,
                                                    GranularityEnum granularity) throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, SnapResponseErrorException;

    List<Pagination<TimeSerieStat>> getCampaignStats(String oAuthAccessToken, int limit, String campaignID, Date startTime, Date endTime,
                                             GranularityEnum granularity, List<String> fields,
                                             BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                             ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                             Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, SnapResponseErrorException;

    List<Pagination<TimeSerieStat>> getAdAccountStats(String oAuthAccessToken, int limit, String adAccountID, Date startTime, Date endTime,
                                              GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

    List<Pagination<TimeSerieStat>> getAdAccountStats(String oAuthAccessToken, int limit, String adAccountID, Date startTime, Date endTime,
                                              GranularityEnum granularity, BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                              ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                              Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapExecutionException, SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException;

    List<Pagination<TimeSerieStat>> getAdSquadStats(String oAuthAccessToken, int limit, String adSquadID, Date startTime, Date endTime,
                                            GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

    List<Pagination<TimeSerieStat>> getAdSquadStats(String oAuthAccessToken, int limit, String adSquadID, Date startTime, Date endTime,
                                            GranularityEnum granularity, List<String> fields,
                                            BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                            ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                            Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapExecutionException, SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException;

    List<Pagination<TimeSerieStat>> getAdStats(String oAuthAccessToken, int limit, String adID, Date startTime, Date endTime,
                                       GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

    List<Pagination<TimeSerieStat>> getAdStats(String oAuthAccessToken, int limit, String adID, Date startTime, Date endTime,
                                       GranularityEnum granularity, List<String> fields,
                                       BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                       ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                       Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException;

    List<Pagination<TimeSerieStat>> getPixelDomainsStats(String oAuthAccessToken, int limit, String pixelID) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException
    ;

    List<Pagination<TimeSerieStat>> getPixelSpecificDomainStats(String oAuthAccessToken, int limit, String pixelID, String domain, Date startTime, Date endTime, GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException
    ;

    List<Pagination<TimeSerieStat>> getPixelSpecificDomainStats(String oAuthAccessToken, int limit, String pixelID, String domain, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, BreakdownEnum breakdown, Boolean test, String reportDimension, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                                        ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                                        Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException
    ;
}// SnapStatsInterface
