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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * DeepLinkProperties
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@ToString
public class DeepLinkProperties {

    /**
     * Deep Link URI
     */
    @JsonProperty("deep_link_uri")
    @NotEmpty(message = "Deep Link URI (Deep Link Properties) is required")
    private String deepLinkUri;

    /**
     * App name
     */
    @JsonProperty("app_name")
    @NotEmpty(message = "App name (Deep Link Properties) is required")
    private String appName;

    /**
     * IOS App Id
     */
    @JsonProperty("ios_app_id")
    private String iosAppId;

    /**
     * Android App Url
     */
    @JsonProperty("android_app_url")
    private String androidAppUrl;

    /**
     * Icon Media ID
     */
    @JsonProperty("icon_media_id")
    @NotEmpty(message = "Icon Media ID (Deep Link Properties) is required")
    private String iconMediaId;

    /**
     * Fallback Type
     */
    @JsonProperty("fallback_type")
    private String fallbackType;

    /**
     * Web ViewFallback Url
     */
    @JsonProperty("web_view_fallback_url")
    private String webViewFallbackUrl;
}// DeepLinkProperties
