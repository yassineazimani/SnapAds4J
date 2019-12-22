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

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeepLinkPropertiesBuilderTest {

    private DeepLinkPropertiesBuilder builder;

    @Before
    public void init() {
        builder = new DeepLinkPropertiesBuilder();
    }// init()

    @Test
    public void test_builder() {
        builder.setAndroidAppUrl("androidAppUrl");
        builder.setAppName("appName");
        builder.setDeepLinkUri("deepLinkUri");
        builder.setFallbackType("fallbackType");
        builder.setIconMediaId("iconMediaId");
        builder.setIosAppId("iosAppId");
        builder.setWebViewFallbackUrl("webViewFallbackUrl");
        DeepLinkProperties properties = builder.build();
        Assertions.assertThat(properties).isNotNull();
        Assertions.assertThat(properties.toString()).isNotEmpty();
        Assertions.assertThat(properties.getAndroidAppUrl()).isEqualTo("androidAppUrl");
        Assertions.assertThat(properties.getAppName()).isEqualTo("appName");
        Assertions.assertThat(properties.getDeepLinkUri()).isEqualTo("deepLinkUri");
        Assertions.assertThat(properties.getFallbackType()).isEqualTo("fallbackType");
        Assertions.assertThat(properties.getIconMediaId()).isEqualTo("iconMediaId");
        Assertions.assertThat(properties.getIosAppId()).isEqualTo("iosAppId");
        Assertions.assertThat(properties.getWebViewFallbackUrl()).isEqualTo("webViewFallbackUrl");
    }// test_builder()
}// DeepLinkPropertiesBuilderTest
