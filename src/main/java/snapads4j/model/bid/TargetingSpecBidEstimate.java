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
import snapads4j.model.targeting.Targeting;

import javax.validation.constraints.NotNull;

/**
 * TargetingSpecBidEstimate
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TargetingSpecBidEstimate {

    /**
     * Optimization Goal
     */
    @JsonProperty("optimization_goal")
    @NotNull(message = "Optimization goal is required")
    private OptimizationGoalEnum optimizationGoal;

    /**
     * Targeting spec
     */
    @NotNull(message = "Targeting is required")
    private Targeting targeting;

}// TargetingSpecBidEstimate()
