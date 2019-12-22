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
 * AppInstallProperties
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@ToString
public class AppInstallProperties {

    /**
     * App name
     */
    @JsonProperty("app_name")
    @NotEmpty(message = "App name (App Install Properties) is required")
    private String appName;

    /**
     * IOS App ID
     */
    @JsonProperty("ios_app_id")
    @NotEmpty(message = "IOS App ID (App Install Properties) is required")
    private String iosAppId;

    /**
     * Android App URL
     */
    @JsonProperty("android_app_url")
    @NotEmpty(message = "Android App URL (App Install Properties) is required")
    private String androidAppUrl;

    /**
     * Icon Media ID
     */
    @JsonProperty("icon_media_id")
    @NotEmpty(message = "Icon Media ID (App Install Properties) is required")
    private String iconMediaId;

}// AppInstallProperties
