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
package snapads4j.model.creatives;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.AdTypeEnum;
import snapads4j.enums.CallToActionEnum;
import snapads4j.enums.CreativeTypeEnum;
import snapads4j.enums.ForcedViewEligibilityEnum;
import snapads4j.enums.PackagingStatusEnum;
import snapads4j.enums.PlaybackTypeEnum;
import snapads4j.enums.ReviewStatusEnum;
import snapads4j.enums.TopSnapCropPositionEnum;

/**
 * CreativeMedia
 * 
 * A Creative is the combination of one or more pieces of media.
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class Creative {
    
    /** ID */
    @JsonProperty("id")
    private String id;
    
    /** Last date update of Creative */
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date updatedAt;

    /** Date creation of Creative */
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdAt;
    
    /** Ad Account ID **/
    @JsonProperty("ad_account_id")
    private String adAccountId;
    
    /** Brand name of Creative */
    @JsonProperty("brand_name")
    private String brandName;
    
    @JsonProperty("call_to_action")
    private CallToActionEnum callToAction;
    
    private String headline;
    
    /**
     * Allow Users to Share with Friends.
     */
    private boolean shareable;
    
    /**
     * Creative name.
     */
    private String name;
    
    /**
     * Top Snap Media ID
     */
    @JsonProperty("top_snap_media_id")
    private String topSnapMediaId;
    
    /**
     * Top Snap Crop Position
     */
    @JsonProperty("top_snap_crop_position")
    private TopSnapCropPositionEnum topSnapCropPosition;
    
    /**
     * Creative type.
     */
    private CreativeTypeEnum type;
    
    @JsonProperty("forced_view_eligibility")
    private ForcedViewEligibilityEnum forcedViewEligibility;
    
    @JsonProperty("preview_creative_id")
    private String previewCreativeId;
    
    /**
     * Specifies whether the creatives within the composite auto-advance or loop.
     */
    @JsonProperty("playback_type")
    private PlaybackTypeEnum playbackType;
    
    /**
     * Type of Ad Product.
     */
    @JsonProperty("ad_product")
    private AdTypeEnum adProduct;
    
    /**
     * Packaging Status
     */
    @JsonProperty("packaging_status")
    private PackagingStatusEnum packagingStatus;
    
    /**
     * Review Status
     */
    @JsonProperty("review_status")
    private ReviewStatusEnum reviewStatus;

}// Creative
