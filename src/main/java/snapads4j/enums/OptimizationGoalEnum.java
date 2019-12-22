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
package snapads4j.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OptimizationGoalEnum {
    /**
     * Cost Per 1000 Impressions (CPM), bid_micro will not be exceeded
     */
    @JsonProperty("IMPRESSIONS")
    IMPRESSIONS,

    /**
     * Target Cost Per Swipe bid_micro is treated as a goal, not a maximum, and can be exceeded
     */
    @JsonProperty("SWIPES")
    SWIPES,

    /**
     * Target Cost Per Install bid_micro is treated as a goal, not a maximum, and can be exceeded
     */
    @JsonProperty("APP_INSTALLS")
    APP_INSTALLS,

    /**
     * Target Cost per 2 second Video View bid_micro is treated as a goal, not a maximum, and can be
     * exceeded
     */
    @JsonProperty("VIDEO_VIEWS")
    VIDEO_VIEWS,

    /**
     * Cost Per Use (of filter) bid_micro is treated as a goal, not a maximum, and can be exceeded
     */
    @JsonProperty("USES")
    USES,

    /**
     * Target Cost Per Story Open for Story Ads bid_micro is treated as a goal, not a maximum, and can
     * be exceeded
     */
    @JsonProperty("STORY_OPENS")
    STORY_OPENS,

    /**
     * Target Cost Per PAGE_VIEW on web bid_micro is treated as a goal, not a maximum, and can be
     * exceeded
     */
    @JsonProperty("PIXEL_PAGE_VIEW")
    PIXEL_PAGE_VIEW,

    /**
     * Target Cost Per ADD_TO_CART on web bid_micro is treated as a goal, not a maximum, and can be
     * exceeded
     */
    @JsonProperty("PIXEL_ADD_TO_CART")
    PIXEL_ADD_TO_CART,

    /**
     * Target Cost Per PURCHASE on web bid_micro is treated as a goal, not a maximum, and can be
     * exceeded
     */
    @JsonProperty("PIXEL_PURCHASE")
    PIXEL_PURCHASE,

    /**
     * Target Cost Per SIGNUP on web bid_micro is treated as a goal, not a maximum, and can be
     * exceeded
     */
    @JsonProperty("PIXEL_SIGNUP")
    PIXEL_SIGNUP,

    /**
     * Target Cost Per ADD_TO_CART on app bid_micro is treated as a goal, not a maximum, and can be
     * exceeded
     */
    @JsonProperty("APP_ADD_TO_CART")
    APP_ADD_TO_CART,

    /**
     * Target Cost Per PURCHASE on app bid_micro is treated as a goal, not a maximum, and can be
     * exceeded
     */
    @JsonProperty("APP_PURCHASE")
    APP_PURCHASE,

    /**
     * Target Cost Per PURCHASE on app bid_micro is treated as a goal, not a maximum, and can be
     * exceeded
     */
    @JsonProperty("APP_SIGNUP")
    APP_SIGNUP
} // OptimizationGoalEnum
