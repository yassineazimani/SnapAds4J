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
package snapads4j.model.ads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import snapads4j.enums.AdTypeEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.model.AbstractSnapModel;
import snapads4j.model.thirdparty.PaidImpressionTrackingUrl;
import snapads4j.model.thirdparty.SwipeTrackingUrl;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Ad
 *
 * @see {https://developers.snapchat.com/api/docs/#ads}
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class Ad extends AbstractSnapModel {

    /**
     * Ad name
     */
    @NotEmpty(message = "Ad's name parameter is required")
    private String name;

    /**
     * Ad status
     */
    @NotNull(message = "Ad's status parameter is required")
    private StatusEnum status;

    /**
     * Ad type
     */
    private AdTypeEnum type;

    /**
     * Ad Squad ID
     */
    @JsonProperty("ad_squad_id")
    @NotEmpty(message = "Ad Squad ID parameter is required")
    private String adSquadId;

    /**
     * Creative ID
     */
    @JsonProperty("creative_id")
    private String creativeId;

    @JsonProperty("third_party_on_swipe_tracking_urls")
    private SwipeTrackingUrl thirdPartyOnSwipeTrackingUrls;

    @JsonProperty("third_party_paid_impression_tracking_urls")
    private PaidImpressionTrackingUrl thirdPartyPaidImpressionTrackingUrls;

    /**
     * Name of the paying advertiser/political entity
     */
    @JsonProperty("paying_advertiser_name")
    private String payingAdvertiserName;



}// Ad
