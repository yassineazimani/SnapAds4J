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
package snapads4j.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ProximityUnitEnum <br>
 * Unit to be used for radius.
 *
 * @author Yassine
 */
public enum ProximityUnitEnum {
  @JsonProperty("METERS")
  METERS,
  @JsonProperty("MILES")
  MILES,
  @JsonProperty("FEET")
  FEET,
  @JsonProperty("KILOMETERS")
  KILOMETERS;
} // ProximityUnitEnum
