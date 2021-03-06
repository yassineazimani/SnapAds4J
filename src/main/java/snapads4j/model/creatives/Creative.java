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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.*;
import snapads4j.model.AbstractSnapModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Creative
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class Creative extends AbstractSnapModel {

    /**
     * Ad Account ID
     **/
    @JsonProperty("ad_account_id")
    @NotEmpty(message = "The Ad Account ID is required")
    private String adAccountId;

    /**
     * Brand name of Creative
     */
    @JsonProperty("brand_name")
    @NotEmpty(message = "The brand name is required")
    private String brandName;

    @JsonProperty("call_to_action")
    private CallToActionEnum callToAction;

    @NotEmpty(message = "The headline is required")
    private String headline;

    /**
     * Allow Users to Share with Friends.
     */
    private boolean shareable;

    /**
     * Creative name.
     */
    @NotEmpty(message = "The creative's name is required")
    private String name;

    /**
     * Top Snap Media ID
     */
    @JsonProperty("top_snap_media_id")
    @NotEmpty(message = "The top snap media ID is required")
    private String topSnapMediaId;

    /**
     * Top Snap Crop Position
     */
    @JsonProperty("top_snap_crop_position")
    private TopSnapCropPositionEnum topSnapCropPosition;

    /**
     * Creative type.
     */
    @NotNull(message = "The creative's type is required")
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

    /**
     * Long Form Video Properties
     */
    @JsonProperty("longform_video_properties")
    private LongformVideoProperties longformVideoProperties;

    /**
     * App Install Properties
     */
    @JsonProperty("app_install_properties")
    private AppInstallProperties appInstallProperties;

    /**
     * Web View Properties
     */
    @JsonProperty("web_view_properties")
    private WebViewProperties webViewProperties;

    /**
     * Deep Link Properties
     */
    @JsonProperty("deep_link_properties")
    private DeepLinkProperties deepLinkProperties;

    /**
     * Preview Properties
     */
    @JsonProperty("preview_properties")
    private PreviewProperties previewProperties;

    /**
     * Collection Properties
     */
    @JsonProperty("collection_properties")
    private CollectionProperties collectionProperties;

    /**
     * AdLens Properties
     */
    @JsonProperty("ad_to_lens_properties")
    private AdLensProperties adLensProperties;

    /**
     * Composite Properties
     */
    @JsonProperty("composite_properties")
    private CompositeProperties compositeProperties;

    /**
     * Build creative instance
     */
    public Creative() {
        this.shareable = true;
    }// Creative()

}// Creative
