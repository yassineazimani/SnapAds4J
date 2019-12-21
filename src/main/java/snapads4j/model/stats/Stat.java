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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class Stat {

    private Integer impressions;

    private Integer swipes;

    @JsonProperty("view_time_millis")
    private Long viewTimeMillis;

    @JsonProperty("conversion_purchases")
    private Integer conversionPurchases;

    @JsonProperty("conversion_purchases_app")
    private Integer conversionPurchasesApp;

    @JsonProperty("conversion_purchases_web")
    private Integer conversionPurchasesWeb;

    @JsonProperty("conversion_save")
    private Integer conversionSave;

    @JsonProperty("conversion_start_checkout")
    private Integer conversionStartCheckout;

    @JsonProperty("conversion_add_cart")
    private Integer conversionAddCart;

    @JsonProperty("conversion_view_content")
    private Integer conversionViewContent;

    @JsonProperty("conversion_add_billing")
    private Integer conversionAddBilling;

    @JsonProperty("conversion_sign_ups")
    private Integer conversionSignUps;

    @JsonProperty("conversion_searches")
    private Integer conversionSearches;

    @JsonProperty("conversion_level_completes")
    private Integer conversionLevelCompletes;

    @JsonProperty("conversion_app_opens")
    private Integer conversionAppOpens;

    @JsonProperty("conversion_page_views")
    private Integer conversionPageViews;

    private Integer spend;

    @JsonProperty("quartile_1")
    private Integer quartile1;

    @JsonProperty("quartile_2")
    private Integer quartile2;

    @JsonProperty("quartile_3")
    private Integer quartile3;

    @JsonProperty("view_completion")
    private Integer viewCompletion;

    @JsonProperty("screen_time_millis")
    private Long screenTimeMillis;

    @JsonProperty("video_views")
    private Integer videoViews;

}// Stat
