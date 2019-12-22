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

import com.fasterxml.jackson.annotation.JsonFormat;
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

import java.util.Date;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class Ad extends AbstractSnapModel {

    private String name;

    private StatusEnum status;

    private AdTypeEnum type;

    @JsonProperty("ad_squad_id")
    private String adSquadId;

    @JsonProperty("creative_id")
    private String creativeId;

    @JsonProperty("third_party_on_swipe_tracking_urls")
    private SwipeTrackingUrl thirdPartyOnSwipeTrackingUrls;

    @JsonProperty("third_party_paid_impression_tracking_urls")
    private PaidImpressionTrackingUrl thirdPartyPaidImpressionTrackingUrls;

}// Ad
