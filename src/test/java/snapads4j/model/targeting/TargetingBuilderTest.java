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
package snapads4j.model.targeting;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import snapads4j.model.demographics.Demographics;
import snapads4j.model.device.Device;
import snapads4j.model.geolocation.GeoLocation;
import snapads4j.model.interest.Interest;
import snapads4j.model.segment.SegmentRequestTargeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TargetingBuilderTest {

    TargetingBuilder builder;

    @Before
    public void init() {
        builder = new TargetingBuilder();
    }// init()

    @Test
    public void test_interest_builder_1() {
        builder.setRegulatedContent(true);
        Targeting targeting = builder.build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.isRegulatedContent()).isEqualTo(true);
    }// test_interest_builder_1()

    @Test
    public void test_interest_builder_2() {
        List<Demographics> demos = new ArrayList<>();
        demos.add(new Demographics());
        builder.setDemographics(demos);
        Targeting targeting = builder.build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getDemographics()).isNotNull();
        Assertions.assertThat(targeting.getDemographics()).isNotEmpty();
        Assertions.assertThat(targeting.getDemographics().get(0)).isNotNull();
    }// test_interest_builder_2()

    @Test
    public void test_segment_request_targeting_builder_3() {
        builder.setRegulatedContent(true);
        Targeting targeting = builder.build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.toString()).isNotEmpty();
    }// test_segment_request_targeting_builder_3()

    @Test
    public void test_interest_builder_4() {
        List<GeoLocation> geos = new ArrayList<>();
        geos.add(new GeoLocation());
        builder.setGeolocation(geos);
        Targeting targeting = builder.build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getGeos()).isNotNull();
        Assertions.assertThat(targeting.getGeos()).isNotEmpty();
        Assertions.assertThat(targeting.getGeos().get(0)).isNotNull();
    }// test_interest_builder_4()

    @Test
    public void test_interest_builder_5() {
        List<Device> devices = new ArrayList<>();
        devices.add(new Device());
        builder.setDevices(devices);
        Targeting targeting = builder.build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getDevices()).isNotNull();
        Assertions.assertThat(targeting.getDevices()).isNotEmpty();
        Assertions.assertThat(targeting.getDevices().get(0)).isNotNull();
    }// test_interest_builder_5()

    @Test
    public void test_interest_builder_6() {
        List<Interest> interests = new ArrayList<>();
        interests.add(new Interest());
        builder.setInterests(interests);
        Targeting targeting = builder.build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getInterests()).isNotNull();
        Assertions.assertThat(targeting.getInterests()).isNotEmpty();
        Assertions.assertThat(targeting.getInterests().get(0)).isNotNull();
    }// test_interest_builder_6()

    @Test
    public void test_interest_builder_7() {
        List<SegmentRequestTargeting> segments = new ArrayList<>();
        segments.add(new SegmentRequestTargeting());
        builder.setSegments(segments);
        Targeting targeting = builder.build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getSegments()).isNotNull();
        Assertions.assertThat(targeting.getSegments()).isNotEmpty();
        Assertions.assertThat(targeting.getSegments().get(0)).isNotNull();
    }// test_interest_builder_7()

    @Test
    public void test_interest_builder_8() {
        List<Map<String, Object>> locations = new ArrayList<>();
        locations.add(new HashMap<>());
        builder.setLocations(locations);
        Targeting targeting = builder.build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getLocations()).isNotNull();
        Assertions.assertThat(targeting.getLocations()).isNotEmpty();
        Assertions.assertThat(targeting.getLocations().get(0)).isNotNull();
    }// test_interest_builder_8()

}// TargetingBuilderTest
