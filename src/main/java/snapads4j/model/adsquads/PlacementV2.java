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
package snapads4j.model.adsquads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.ConfigPlacementEnum;
import snapads4j.enums.PlatformPlacementEnum;
import snapads4j.enums.SnapChatPositionsEnum;

/**
 * PlacementV2
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class PlacementV2 {

  /** Configuration for placement */
  private ConfigPlacementEnum config;

  /** The platform to place the ads */
  private PlatformPlacementEnum platforms;

  /** List of possible placement positions */
  @JsonProperty("snapchat_positions")
  private SnapChatPositionsEnum snapchatPositions;

  /** Details about the content types to be included */
  private InclusionAdSquad inclusion;

  /** Details about the content types to be included */
  private ExclusionAdSquad exclusion;
} // PlacementV2
