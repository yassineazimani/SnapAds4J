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
package snapads4j.model.thirdparty;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SwipeTrackingUrlBuilderTest {

    @Test
    public void test_swipe_tracking_url_builder_1() {
        SwipeTrackingUrl s = new SwipeTrackingUrl.Builder().setExpandedTrackingUrl("url").build();
        Assertions.assertThat(s).isNotNull();
        Assertions.assertThat(s.getExpandedTrackingUrl()).isEqualTo("url");
    }// test_swipe_tracking_url_builder_1

    @Test
    public void test_swipe_tracking_url_builder_2() {
        SwipeTrackingUrl s = new SwipeTrackingUrl.Builder().setTrackingUrl("url2").build();
        Assertions.assertThat(s).isNotNull();
        Assertions.assertThat(s.getTrackingUrl()).isEqualTo("url2");
    }// test_swipe_tracking_url_builder_2

    @Test
    public void test_swipe_tracking_url_builder_3() {
        SwipeTrackingUrl s = new SwipeTrackingUrl.Builder().setTrackingUrlMetadata("url3").build();
        Assertions.assertThat(s).isNotNull();
        Assertions.assertThat(s.getTrackingUrlMetadata()).isEqualTo("url3");
    }// test_swipe_tracking_url_builder_3

    @Test
    public void test_swipe_tracking_url_builder_4() {
        SwipeTrackingUrl s = new SwipeTrackingUrl.Builder().setTrackingUrlMetadata("url3").build();
        Assertions.assertThat(s).isNotNull();
        Assertions.assertThat(s.toString()).isNotEmpty();
    }// test_swipe_tracking_url_builder_4

}// SwipeTrackingUrlBuilderTest
