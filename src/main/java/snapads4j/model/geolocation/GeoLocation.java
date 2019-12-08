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
package snapads4j.model.geolocation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.OperationEnum;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class GeoLocation {

  @JsonProperty("country_code")
  private String countryCode;

  /** Postal code */
  @JsonProperty("postal_code")
  private List<Integer> postalCodes;

  /** Metropole IDS */
  @JsonProperty("metro_id")
  private List<Integer> metroIds;

  private OperationEnum operation;
} // GeoLocation
