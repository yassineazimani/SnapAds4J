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

import java.util.List;

/**
 * WebViewProperties
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@ToString
public class WebViewProperties {

    private WebViewProperties(){}

    /**
     * URL
     */
    private String url;

    /**
     * Allows Snap Javascript SDK
     */
    @JsonProperty("allow_snap_javascript_sdk")
    private boolean allowSnapJavascriptSdk;

    /**
     * Use block preload
     */
    @JsonProperty("block_preload")
    private boolean blockPreload;

    /**
     * Use immersive mode
     */
    @JsonProperty("use_immersive_mode")
    private boolean useImmersiveMode;

    /**
     * Deep Link Urls bind to this web view properties
     */
    @JsonProperty("deep_link_urls")
    private List<String> deepLinkUrls;

    public static class Builder {

        private final WebViewProperties instance;

        public Builder() {
            this.instance = new WebViewProperties();
        }// Builder()

        public Builder setUrl(String url) {
            this.instance.setUrl(url);
            return this;
        }// setUrl()

        public Builder setAllowSnapJavascriptSdk(boolean allowSnapJavascriptSdk) {
            this.instance.setAllowSnapJavascriptSdk(allowSnapJavascriptSdk);
            return this;
        }// setAllowSnapJavascriptSdk()

        public Builder setBlockPreload(boolean blockPreload) {
            this.instance.setBlockPreload(blockPreload);
            return this;
        }// setBlockPreload()

        public WebViewProperties build() {
            return this.instance;
        }// build()

    }// Builder

}// WebViewProperties
