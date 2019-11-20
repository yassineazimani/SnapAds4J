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

import java.util.List;
import java.util.Map;

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

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class Targeting {

  /** List of Demographic Targets. */
  private List<Demographics> demographics;

  /** List of Device Targets */
  private List<Device> devices;

  /** List of Geo Targets */
  private List<GeoLocation> geos;

  /** List of Location Targets */
  private List<Map<String, Object>> locations;

  /** List of Interest Targets */
  private List<Interest> interests;

  /** Flag to mark content within the Ad Squad as Regulated Content */
  @JsonProperty("regulated_content")
  private boolean regulatedContent;

  /** List of Snap Audience Match Segment Targets */
  private List<SegmentRequestTargeting> segments;
} // Targeting
