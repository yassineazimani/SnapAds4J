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
package snapads4j.model.location;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.OperationEnum;
import snapads4j.enums.ProximityUnitEnum;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class Location {

  @JsonProperty("location_type")
  private List<String> locationTypes;

  private List<Circle> circles;

  private OperationEnum operation;

  @JsonProperty("proximity_unit")
  private ProximityUnitEnum proximityUnit;

  private Integer proximity;
} // Location
