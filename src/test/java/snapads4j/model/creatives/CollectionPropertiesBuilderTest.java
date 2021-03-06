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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CollectionPropertiesBuilderTest {

    @Test
    public void test_builder() {
        CollectionProperties properties = new CollectionProperties.Builder()
                .setDefaultFallbackInteractionType("defaultFallbackInteractionType")
                .setInteractionZoneId("interactionZoneId")
                .setDeepLinkProperties(new DeepLinkProperties.Builder().build())
                .setWebViewProperties(new WebViewProperties.Builder().build())
                .build();
        Assertions.assertThat(properties).isNotNull();
        Assertions.assertThat(properties.toString()).isNotEmpty();
        Assertions.assertThat(properties.getDefaultFallbackInteractionType()).isEqualTo("defaultFallbackInteractionType");
        Assertions.assertThat(properties.getDeepLinkProperties()).isNotNull();
        Assertions.assertThat(properties.getInteractionZoneId()).isEqualTo("interactionZoneId");
        Assertions.assertThat(properties.getWebViewProperties()).isNotNull();
    }// test_builder()

}// CollectionPropertiesBuilderTest
