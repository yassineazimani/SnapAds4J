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

    @JsonProperty("conversion_purchases_value")
    private Integer conversionPurchasesValue;

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

    @JsonProperty("conversion_subscribe")
    private Integer conversionSubscribe;

    @JsonProperty("conversion_ad_click")
    private Integer conversionAdClick;

    @JsonProperty("conversion_ad_view")
    private Integer conversionAdView;

    @JsonProperty("conversion_complete_tutorial")
    private Integer conversionCompleteTutorial;

    @JsonProperty("conversion_invite")
    private Integer conversionInvite;

    @JsonProperty("conversion_login")
    private Integer conversionLogin;

    @JsonProperty("conversion_share")
    private Integer conversionShare;

    @JsonProperty("conversion_reserve")
    private Integer conversionReserve;

    @JsonProperty("conversion_achievement_unlocked")
    private Integer conversionAchievementUnlocked;

    @JsonProperty("conversion_add_to_wishlist")
    private Integer conversionAddToWishlist;

    @JsonProperty("conversion_spend_credits")
    private Integer conversionSpendCredits;

    @JsonProperty("conversion_rate")
    private Integer conversionRate;

    @JsonProperty("conversion_start_trial")
    private Integer conversionStartTrial;

    @JsonProperty("conversion_list_view")
    private Integer conversionListView;

    @JsonProperty("custom_event_1")
    private Integer custom_event_1;

    @JsonProperty("custom_event_2")
    private Integer custom_event_2;

    @JsonProperty("custom_event_3")
    private Integer custom_event_3;

    @JsonProperty("custom_event_4")
    private Integer custom_event_4;

    @JsonProperty("custom_event_5")
    private Integer custom_event_5;

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

    @JsonProperty("android_installs")
    private Integer androidInstalls;

    @JsonProperty("attachment_avg_view_time_millis")
    private Integer attachmentAvgViewTimeMillis;

    @JsonProperty("attachment_frequency")
    private Integer attachmentFrequency;

    @JsonProperty("attachment_quartile_1")
    private Integer attachmentQuartile1;

    @JsonProperty("attachment_quartile_2")
    private Integer attachmentQuartile2;

    @JsonProperty("attachment_quartile_3")
    private Integer attachmentQuartile3;

    @JsonProperty("attachment_total_view_time_millis")
    private Integer attachmentTotalViewTimeMillis;

    @JsonProperty("attachment_uniques")
    private Integer attachmentUniques;

    @JsonProperty("attachment_view_completion")
    private Integer attachmentViewCompletion;

    @JsonProperty("attachment_video_views")
    private Integer attachmentVideoViews;

    @JsonProperty("avg_view_time_millis")
    private Integer avgViewTimeMillis;

    @JsonProperty("avg_screen_time_millis")
    private Integer avgScreenTimeMillis;

    private Integer frequency;

    @JsonProperty("ios_installs")
    private Integer iosInstalls;

    @JsonProperty("swipe_up_percent")
    private Integer swipeUpPercent;

    @JsonProperty("total_installs")
    private Integer totalInstalls;

    private Integer uniques;

    @JsonProperty("video_views_time_based")
    private Integer videoViewsTimeBased;

    @JsonProperty("video_views_15s")
    private Integer videoViews15s;

    @JsonProperty("story_opens")
    private Integer storyOpens;

    @JsonProperty("story_completes")
    private Integer storyCompletes;

    @JsonProperty("position_impressions")
    private Integer positionImpressions;

    @JsonProperty("position_uniques")
    private Integer positionUniques;

    @JsonProperty("position_frequency")
    private Integer positionFrequency;

    @JsonProperty("position_screen_time_millis")
    private Integer positionScreenTimeMillis;

    @JsonProperty("position_swipe_up_percent")
    private Integer positionSwipeUpPercent;

    @JsonProperty("avg_position_screen_time_millis")
    private Integer avgPositionScreenTimeMillis;

    private Integer shares;

    private Integer saves;

}// Stat
