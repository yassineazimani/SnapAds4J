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
package snapads4j.model.interest;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class InterestBuilderTest {

    InterestBuilder builder;

    @Before
    public void init() {
        builder = new InterestBuilder();
    }// init()

    @Test
    public void test_interest_builder_1() {
        builder.setId(1);
        Interest interest = builder.build();
        Assertions.assertThat(interest).isNotNull();
        Assertions.assertThat(interest.getId()).isEqualTo(1);
    }// test_interest_builder_1()

    @Test
    public void test_interest_builder_2() {
        List<String> ids = new ArrayList<>();
        ids.add("id1");
        builder.setCategoryIds(ids);
        Interest interest = builder.build();
        Assertions.assertThat(interest).isNotNull();
        Assertions.assertThat(interest.getCategoryIds()).isNotNull();
        Assertions.assertThat(interest.getCategoryIds()).isNotEmpty();
        Assertions.assertThat(interest.getCategoryIds().get(0)).isEqualTo("id1");
    }// test_interest_builder_2()

    @Test
    public void test_segment_request_targeting_builder_3() {
        builder.setId(1);
        Interest interest = builder.build();
        Assertions.assertThat(interest).isNotNull();
        Assertions.assertThat(interest.toString()).isNotEmpty();
    }// test_segment_request_targeting_builder_3()
}// InterestBuilderTest
