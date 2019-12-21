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

import snapads4j.model.demographics.Demographics;
import snapads4j.model.device.Device;
import snapads4j.model.geolocation.GeoLocation;
import snapads4j.model.interest.Interest;
import snapads4j.model.segment.SegmentRequestTargeting;

import java.util.List;
import java.util.Map;

/**
 * Used to build Targeting instance (${@link Targeting})
 *
 * @author Yassine
 */
public class TargetingBuilder {

    private final Targeting targetingInstance;

    public TargetingBuilder() {
        this.targetingInstance = new Targeting();
    } // TargetingBuilder()

    public TargetingBuilder setDemographics(List<Demographics> demographics) {
        this.targetingInstance.setDemographics(demographics);
        return this;
    } // setDemographics()

    public TargetingBuilder setDevices(List<Device> devices) {
        this.targetingInstance.setDevices(devices);
        return this;
    } // setDevices()

    public TargetingBuilder setGeolocation(List<GeoLocation> geos) {
        this.targetingInstance.setGeos(geos);
        return this;
    } // setGeolocation()

    public TargetingBuilder setInterests(List<Interest> interests) {
        this.targetingInstance.setInterests(interests);
        return this;
    } // setDevices()

    public TargetingBuilder setRegulatedContent(boolean regulatedContent) {
        this.targetingInstance.setRegulatedContent(regulatedContent);
        return this;
    } // setRegulatedContent()

    public TargetingBuilder setSegments(List<SegmentRequestTargeting> segments) {
        this.targetingInstance.setSegments(segments);
        return this;
    } // setSegments

    public TargetingBuilder setLocations(List<Map<String, Object>> locations) {
        this.targetingInstance.setLocations(locations);
        return this;
    } // setLocations

    public Targeting build() {
        return this.targetingInstance;
    } // build()
} // TargetingBuilder
