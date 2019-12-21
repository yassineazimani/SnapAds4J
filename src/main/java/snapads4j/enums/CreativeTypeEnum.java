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

/**
 * CreativeTypeEnum
 *
 * @author yassine
 */
public enum CreativeTypeEnum {
    /**
     * SNAP_AD
     */
    @JsonProperty("SNAP_AD")
    SNAP_AD,
    /**
     * APP_INSTALL
     */
    @JsonProperty("APP_INSTALL")
    APP_INSTALL,
    /**
     * LONGFORM_VIDEO
     */
    @JsonProperty("LONGFORM_VIDEO")
    LONGFORM_VIDEO,
    /**
     * WEB_VIEW
     */
    @JsonProperty("WEB_VIEW")
    WEB_VIEW,
    /**
     * DEEP_LINK
     */
    @JsonProperty("DEEP_LINK")
    DEEP_LINK,
    /**
     * AD_TO_LENS
     */
    @JsonProperty("AD_TO_LENS")
    AD_TO_LENS,
    /**
     * PREVIEW
     */
    @JsonProperty("PREVIEW")
    PREVIEW,
    /**
     * COMPOSITE
     */
    @JsonProperty("COMPOSITE")
    COMPOSITE,
    /**
     * LENS
     */
    @JsonProperty("LENS")
    LENS,
    /**
     * LENS_WEB_VIEW
     */
    @JsonProperty("LENS_WEB_VIEW")
    LENS_WEB_VIEW,
    /**
     * LENS_APP_INSTALL
     */
    @JsonProperty("LENS_APP_INSTALL")
    LENS_APP_INSTALL,
    /**
     * LENS_DEEP_LINK
     */
    @JsonProperty("LENS_DEEP_LINK")
    LENS_DEEP_LINK,
    /**
     * LENS_LONGFORM_VIDEO
     */
    @JsonProperty("LENS_LONGFORM_VIDEO")
    LENS_LONGFORM_VIDEO,
    /**
     * BUTTON
     */
    @JsonProperty("BUTTON")
    BUTTON,
    /**
     * COLLECTION
     */
    @JsonProperty("COLLECTION")
    COLLECTION
}// CreativeTypeEnum
