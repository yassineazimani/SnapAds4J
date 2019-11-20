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
 * Data source type
 *
 * @author Yassine
 */
public enum SourceTypeEnum {
  @JsonProperty("FIRST_PARTY")
  FIRST_PARTY,
  @JsonProperty("ENGAGEMENT")
  ENGAGEMENT,
  @JsonProperty("PIXEL")
  PIXEL,
  @JsonProperty("FOOT_TRAFFIC_INSIGHTS")
  FOOT_TRAFFIC_INSIGHTS;
} // SourceTypeEnum
