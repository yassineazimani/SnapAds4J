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
package snapads4j.model.bid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import snapads4j.enums.OptimizationGoalEnum;

/**
 * Bid estimate for {@link snapads4j.model.adsquads.AdSquad}or {@link TargetingSpecBidEstimate}
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BidEstimate {

    /**
     * Ad Squad ID
     */
    @JsonProperty("ad_squad_id")
    private String adSquadId;

    /**
     * Optimization Goal
     */
    @JsonProperty("optimization_goal")
    private OptimizationGoalEnum optimizationGoal;

    /**
     * Bid estimate minimum
     */
    @JsonProperty("bid_estimate_minimum")
    private long bidEstimateMinimum;

    /**
     * Bid estimate maximum
     */
    @JsonProperty("bid_estimate_maximum")
    private long bidEstimateMaximum;

}// BidEstimate
