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

public class CollectionPropertiesBuilder {

    private final CollectionProperties instance;

    public CollectionPropertiesBuilder() {
        this.instance = new CollectionProperties();
    }// DeepLinkPropertiesBuilder()

    public CollectionPropertiesBuilder setDeepLinkProperties(DeepLinkProperties deepLinkProperties) {
        this.instance.setDeepLinkProperties(deepLinkProperties);
        return this;
    }// setDeepLinkProperties()

    public CollectionPropertiesBuilder setDefaultFallbackInteractionType(String defaultFallbackInteractionType) {
        this.instance.setDefaultFallbackInteractionType(defaultFallbackInteractionType);
        return this;
    }// setDefaultFallbackInteractionType()

    public CollectionPropertiesBuilder setInteractionZoneId(String interactionZoneId) {
        this.instance.setInteractionZoneId(interactionZoneId);
        return this;
    }// setInteractionZoneId()

    public CollectionPropertiesBuilder setWebViewProperties(WebViewProperties webViewProperties) {
        this.instance.setWebViewProperties(webViewProperties);
        return this;
    }// setWebViewProperties()

    public CollectionProperties build() {
        return this.instance;
    }// build()
}// CollectionPropertiesBuilder
