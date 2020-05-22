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

    @Test
    public void test_interest_builder_set_regulated_content() {
        Targeting targeting = new Targeting.Builder().setRegulatedContent(true).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.isRegulatedContent()).isEqualTo(true);
    }// test_interest_builder_set_regulated_content()

    @Test
    public void test_interest_builder_set_demographics() {
        List<Demographics> demos = new ArrayList<>();
        demos.add(new Demographics.Builder().build());
        Targeting targeting = new Targeting.Builder().setDemographics(demos).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getDemographics()).isNotNull();
        Assertions.assertThat(targeting.getDemographics()).isNotEmpty();
        Assertions.assertThat(targeting.getDemographics().get(0)).isNotNull();
    }// test_interest_builder_set_demographics()_2()

    @Test
    public void test_segment_request_targeting_builder_set_regulated_content() {
        Targeting targeting = new Targeting.Builder().setRegulatedContent(true).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.toString()).isNotEmpty();
    }// test_segment_request_targeting_builder_set_regulated_content()

    @Test
    public void test_interest_new_builder_set_geolocations() {
        List<GeoLocation> geos = new ArrayList<>();
        geos.add(new GeoLocation());
        Targeting targeting = new Targeting.Builder().setGeolocations(geos).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getGeos()).isNotNull();
        Assertions.assertThat(targeting.getGeos()).isNotEmpty();
        Assertions.assertThat(targeting.getGeos().get(0)).isNotNull();
    }// test_interest_new_builder_set_geolocations()

    @Test
    public void test_interest_new_builder_set_devices() {
        List<Device> devices = new ArrayList<>();
        devices.add(new Device());
        Targeting targeting = new Targeting.Builder().setDevices(devices).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getDevices()).isNotNull();
        Assertions.assertThat(targeting.getDevices()).isNotEmpty();
        Assertions.assertThat(targeting.getDevices().get(0)).isNotNull();
    }// test_interest_new_builder_set_devices()

    @Test
    public void test_interest_new_builder_set_interests() {
        List<Interest> interests = new ArrayList<>();
        interests.add(new Interest());
        Targeting targeting = new Targeting.Builder().setInterests(interests).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getInterests()).isNotNull();
        Assertions.assertThat(targeting.getInterests()).isNotEmpty();
        Assertions.assertThat(targeting.getInterests().get(0)).isNotNull();
    }// test_interest_new_builder_set_interests()

    @Test
    public void test_interest_new_builder_set_segments() {
        List<SegmentRequestTargeting> segments = new ArrayList<>();
        segments.add(new SegmentRequestTargeting());
        Targeting targeting = new Targeting.Builder().setSegments(segments).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getSegments()).isNotNull();
        Assertions.assertThat(targeting.getSegments()).isNotEmpty();
        Assertions.assertThat(targeting.getSegments().get(0)).isNotNull();
    }// test_interest_new_builder_set_segments()

    @Test
    public void test_interest_new_builder_set_locations() {
        List<Map<String, Object>> locations = new ArrayList<>();
        locations.add(new HashMap<>());
        Targeting targeting = new Targeting.Builder().setLocations(locations).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.getLocations()).isNotNull();
        Assertions.assertThat(targeting.getLocations()).isNotEmpty();
        Assertions.assertThat(targeting.getLocations().get(0)).isNotNull();
    }// test_interest_new_builder_set_locations()

    @Test
    public void test_interest_new_builder_set_enable_targeting_expansion() {
        Targeting targeting = new Targeting.Builder().setEnableTargetingExpansion(true).build();
        Assertions.assertThat(targeting).isNotNull();
        Assertions.assertThat(targeting.isEnableTargetingExpansion()).isTrue();
    }// test_interest_new_builder_set_enable_targeting_expansion()

}// TargetingBuilderTest
