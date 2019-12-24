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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.model.demographics.Demographics;
import snapads4j.model.device.Device;
import snapads4j.model.geolocation.GeoLocation;
import snapads4j.model.interest.Interest;
import snapads4j.model.segment.SegmentRequestTargeting;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class Targeting {

    /**
     * List of Demographic Targets.
     */
    private List<Demographics> demographics;

    /**
     * List of Device Targets
     */
    private List<Device> devices;

    /**
     * List of Geo Targets
     */
    private List<GeoLocation> geos;

    /**
     * List of Location Targets
     */
    private List<Map<String, Object>> locations;

    /**
     * List of Interest Targets
     */
    private List<Interest> interests;

    /**
     * Flag to mark content within the Ad Squad as Regulated Content
     */
    @JsonProperty("regulated_content")
    private boolean regulatedContent;

    /**
     * List of Snap Audience Match Segment Targets
     */
    private List<SegmentRequestTargeting> segments;

    /**
     * Used to build Targeting instance (${@link Targeting})
     *
     * @author Yassine
     */
    public static class Builder {

        private final Targeting targetingInstance;

        public Builder() {
            this.targetingInstance = new Targeting();
        } // Builder()

        public Builder setDemographics(List<Demographics> demographics) {
            this.targetingInstance.setDemographics(demographics);
            return this;
        } // setDemographics()

        public Builder setDevices(List<Device> devices) {
            this.targetingInstance.setDevices(devices);
            return this;
        } // setDevices()

        public Builder setGeolocation(List<GeoLocation> geos) {
            this.targetingInstance.setGeos(geos);
            return this;
        } // setGeolocation()

        public Builder setInterests(List<Interest> interests) {
            this.targetingInstance.setInterests(interests);
            return this;
        } // setDevices()

        public Builder setRegulatedContent(boolean regulatedContent) {
            this.targetingInstance.setRegulatedContent(regulatedContent);
            return this;
        } // setRegulatedContent()

        public Builder setSegments(List<SegmentRequestTargeting> segments) {
            this.targetingInstance.setSegments(segments);
            return this;
        } // setSegments

        public Builder setLocations(List<Map<String, Object>> locations) {
            this.targetingInstance.setLocations(locations);
            return this;
        } // setLocations

        public Targeting build() {
            return this.targetingInstance;
        } // build()
    } // Builder

} // Targeting
