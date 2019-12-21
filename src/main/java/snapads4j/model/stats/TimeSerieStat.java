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
package snapads4j.model.stats;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.GranularityEnum;
import snapads4j.enums.SwipeUpAttributionWindowEnum;
import snapads4j.enums.TimeSerieTypeEnum;
import snapads4j.enums.ViewAttributionWindowEnum;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class TimeSerieStat {

    private String id;

    private TimeSerieTypeEnum type;

    private GranularityEnum granularity;

    @JsonProperty("start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date startTime;

    @JsonProperty("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date endTime;

    @JsonProperty("finalized_data_end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date finalizedDataEndTime;

    private List<TimeSerie> timeseries;

    private List<Domain> domains;

    @JsonProperty("swipe_up_attribution_window")
    private SwipeUpAttributionWindowEnum swipeUpAttributionWindow;

    @JsonProperty("view_attribution_window")
    private ViewAttributionWindowEnum viewAttributionWindow;

    /**
     * Useful in case Ad Squads {@link SnapHttpResponseTotalStat}
     */
    private Stat stats;

    private String domain;

}// TimeSerieStat
