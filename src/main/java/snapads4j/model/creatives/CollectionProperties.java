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
 * Collection Properties
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@ToString
public class CollectionProperties {

    private CollectionProperties(){}

    /**
     * Interaction Zone ID
     */
    @JsonProperty("interaction_zone_id")
    @NotEmpty(message = "Interaction Zone ID (Collection Properties) is required")
    private String interactionZoneId;

    /**
     * Default Fallback Interaction Type
     */
    @JsonProperty("default_fallback_interaction_type")
    @NotEmpty(message = "Default Fallback Interaction Type (Collection Properties) is required")
    private String defaultFallbackInteractionType;

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

    public static class Builder {

        private final CollectionProperties instance;

        public Builder() {
            this.instance = new CollectionProperties();
        }// DeepLinkPropertiesBuilder()

        public Builder setDeepLinkProperties(DeepLinkProperties deepLinkProperties) {
            this.instance.setDeepLinkProperties(deepLinkProperties);
            return this;
        }// setDeepLinkProperties()

        public Builder setDefaultFallbackInteractionType(String defaultFallbackInteractionType) {
            this.instance.setDefaultFallbackInteractionType(defaultFallbackInteractionType);
            return this;
        }// setDefaultFallbackInteractionType()

        public Builder setInteractionZoneId(String interactionZoneId) {
            this.instance.setInteractionZoneId(interactionZoneId);
            return this;
        }// setInteractionZoneId()

        public Builder setWebViewProperties(WebViewProperties webViewProperties) {
            this.instance.setWebViewProperties(webViewProperties);
            return this;
        }// setWebViewProperties()

        public CollectionProperties build() {
            return this.instance;
        }// build()
    }// Builder

}// CollectionProperties
