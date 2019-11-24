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

@Getter
@Setter
@ToString
public class DeepLinkProperties {

    @JsonProperty("deep_link_uri")
    private String deepLinkUri;
    
    @JsonProperty("app_name")
    private String appName;
    
    @JsonProperty("ios_app_id")
    private String iosAppId;
	    
    @JsonProperty("android_app_url")
    private String androidAppUrl;
		    
    @JsonProperty("icon_media_id")
    private String iconMediaId;
    
    @JsonProperty("fallback_type")
    private String fallbackType;
    
    @JsonProperty("web_view_fallback_url")
    private String webViewFallbackUrl;
}// DeepLinkProperties