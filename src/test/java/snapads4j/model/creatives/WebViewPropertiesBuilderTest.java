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
public class WebViewPropertiesBuilderTest {

    private WebViewPropertiesBuilder builder;

    @Before
    public void init() {
        builder = new WebViewPropertiesBuilder();
    }// init()

    @Test
    public void test_web_builder_1() {
        String url = "http://www.snapchat.com";
        builder.setUrl(url);
        WebViewProperties properties = builder.build();
        Assertions.assertThat(properties).isNotNull();
        Assertions.assertThat(properties.getUrl()).isNotNull();
        Assertions.assertThat(properties.getUrl()).isNotEmpty();
        Assertions.assertThat(properties.getUrl()).isEqualTo(url);
    }// test_web_builder_1()

    @Test
    public void test_web_builder_2() {
        builder.setAllowSnapJavascriptSdk(true);
        WebViewProperties properties = builder.build();
        Assertions.assertThat(properties).isNotNull();
        Assertions.assertThat(properties.isAllowSnapJavascriptSdk()).isTrue();
    }// test_web_builder_2()

    @Test
    public void test_web_builder_3() {
        builder.setBlockPreload(false);
        WebViewProperties properties = builder.build();
        Assertions.assertThat(properties).isNotNull();
        Assertions.assertThat(properties.isBlockPreload()).isFalse();
    }// test_web_builder_3()
}// WebViewPropertiesBuilderTest
