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
package snapads4j.model.adsquads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.FrequencyCapIntervalEnum;
import snapads4j.enums.FrequencyCapTypeEnum;

@Getter
@Setter
@ToString
/**
 * FrequencyCapConfig
 *
 * <p>Frequency cap spec cap_and_exclusion_config allows you to specify the maximum number of times
 * a user is exposed to an ad over a period of time.
 *
 * @author Yassine
 */
public class FrequencyCapConfig {
    /**
     * Number of times an ad is shown to the user in the interval
     */
    @JsonProperty("frequency_cap_count")
    private Integer frequencyCapCount;

    /**
     * Event to be frequency capped
     */
    @JsonProperty("frequency_cap_type")
    private FrequencyCapTypeEnum frequencyCapType;

    /**
     * Interval during which the frequency cap rule is applied. frequency_cap_count is reset at the
     * end of the interval. (Max 30 days or 720 hours)
     */
    @JsonProperty("time_interval")
    private Integer timeInterval;

    /**
     * Unit for time_interval
     */
    @JsonProperty("frequency_cap_interval")
    private FrequencyCapIntervalEnum frequencyCapInterval;
} // FrequencyCapConfig
