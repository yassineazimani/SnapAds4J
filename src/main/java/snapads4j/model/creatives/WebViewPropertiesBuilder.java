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

public class WebViewPropertiesBuilder {

    private WebViewProperties instance;

    public WebViewPropertiesBuilder() {
	this.instance = new WebViewProperties();
    }// WebViewPropertiesBuilder()

    public WebViewPropertiesBuilder setUrl(String url) {
	this.instance.setUrl(url);
	return this;
    }// setUrl()

    public WebViewPropertiesBuilder setAllowSnapJavascriptSdk(boolean allowSnapJavascriptSdk) {
	this.instance.setAllowSnapJavascriptSdk(allowSnapJavascriptSdk);
	return this;
    }// setAllowSnapJavascriptSdk()

    public WebViewPropertiesBuilder setBlockPreload(boolean blockPreload) {
	this.instance.setBlockPreload(blockPreload);
	return this;
    }// setBlockPreload()

    public WebViewProperties build() {
	return this.instance;
    }// build()

}// WebViewPropertiesBuilder
