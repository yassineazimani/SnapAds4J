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

public class DeepLinkPropertiesBuilder {

    private DeepLinkProperties instance;

    public DeepLinkPropertiesBuilder() {
	this.instance = new DeepLinkProperties();
    }// DeepLinkPropertiesBuilder()

    public DeepLinkPropertiesBuilder setDeepLinkUri(String deepLinkUri) {
	this.instance.setDeepLinkUri(deepLinkUri);
	return this;
    }// setDeepLinkUri()

    public DeepLinkPropertiesBuilder setAppName(String appName) {
	this.instance.setAppName(appName);
	return this;
    }// setAppName()

    public DeepLinkPropertiesBuilder setIosAppId(String iosAppId) {
	this.instance.setIosAppId(iosAppId);
	return this;
    }// setIosAppId()

    public DeepLinkPropertiesBuilder setAndroidAppUrl(String androidAppUrl) {
	this.instance.setAndroidAppUrl(androidAppUrl);
	return this;
    }// setAndroidAppUrl()

    public DeepLinkPropertiesBuilder setIconMediaId(String iconMediaId) {
	this.instance.setIconMediaId(iconMediaId);
	return this;
    }// setIconMediaId()

    public DeepLinkPropertiesBuilder setFallbackType(String fallbackType) {
	this.instance.setFallbackType(fallbackType);
	return this;
    }// setFallbackType()

    public DeepLinkPropertiesBuilder setWebViewFallbackUrl(String webViewFallbackUrl) {
	this.instance.setWebViewFallbackUrl(webViewFallbackUrl);
	return this;
    }// setWebViewFallbackUrl()

    public DeepLinkProperties build() {
	return this.instance;
    }// build()
}// DeepLinkPropertiesBuilder
