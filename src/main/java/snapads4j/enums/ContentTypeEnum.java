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
 * ContentTypeEnum
 *
 * @author Yassine
 */
public enum ContentTypeEnum {
  /** NEWS */
  @JsonProperty("NEWS")
  NEWS,
  /** ENTERTAINMENT */
  @JsonProperty("ENTERTAINMENT")
  ENTERTAINMENT,
  /** SCIENCE_TECHNOLOGY */
  @JsonProperty("SCIENCE_TECHNOLOGY")
  SCIENCE_TECHNOLOGY,
  /** BEAUTY_FASHION */
  @JsonProperty("BEAUTY_FASHION")
  BEAUTY_FASHION,
  /** MENS_LIFESTYLE */
  @JsonProperty("MENS_LIFESTYLE")
  MENS_LIFESTYLE,
  /** WOMENS_LIFESTYLE */
  @JsonProperty("WOMENS_LIFESTYLE")
  WOMENS_LIFESTYLE,
  /** GENERAL_LIFESTYLE */
  @JsonProperty("GENERAL_LIFESTYLE")
  GENERAL_LIFESTYLE,
  /** FOOD */
  @JsonProperty("FOOD")
  FOOD,
  /** SPORTS */
  @JsonProperty("SPORTS")
  SPORTS,
  /** YOUNG_BOLD */
  @JsonProperty("YOUNG_BOLD")
  YOUNG_BOLD
} // ContentTypeEnum
