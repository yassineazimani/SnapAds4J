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
package snapads4j.model.segment;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import snapads4j.enums.OperationEnum;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SegmentRequestTargetingBuilderTest {

    @Test
    public void test_segment_request_targeting_builder_1() {
        SegmentRequestTargeting sTarget = new SegmentRequestTargeting.Builder().setId(1L).build();
        Assertions.assertThat(sTarget).isNotNull();
        Assertions.assertThat(sTarget.getId()).isEqualTo(1L);
    }// test_segment_request_targeting_builder_1()

    @Test
    public void test_segment_request_targeting_builder_2() {
        SegmentRequestTargeting sTarget = new SegmentRequestTargeting.Builder().setOperation(OperationEnum.INCLUDE).build();
        Assertions.assertThat(sTarget).isNotNull();
        Assertions.assertThat(sTarget.getOperation()).isEqualTo(OperationEnum.INCLUDE);
    }// test_segment_request_targeting_builder_2()

    @Test
    public void test_segment_request_targeting_builder_3() {
        List<String> segmentsIds = new ArrayList<>();
        segmentsIds.add("segment-1");
        SegmentRequestTargeting sTarget = new SegmentRequestTargeting.Builder().setSegmentIds(segmentsIds).build();
        Assertions.assertThat(sTarget).isNotNull();
        Assertions.assertThat(sTarget.getSegmentIds()).isNotNull();
        Assertions.assertThat(sTarget.getSegmentIds()).isNotEmpty();
        Assertions.assertThat(sTarget.getSegmentIds()).size().isEqualTo(1);
        Assertions.assertThat(sTarget.getSegmentIds().get(0)).isEqualTo(segmentsIds.get(0));
    }// test_segment_request_targeting_builder_3()

    @Test
    public void test_segment_request_targeting_builder_4() {
        SegmentRequestTargeting sTarget = new SegmentRequestTargeting.Builder().setOperation(OperationEnum.INCLUDE).build();
        Assertions.assertThat(sTarget).isNotNull();
        Assertions.assertThat(sTarget.toString()).isNotEmpty();
    }// test_segment_request_targeting_builder_4()
}// SegmentRequestTargetingBuilderTest
