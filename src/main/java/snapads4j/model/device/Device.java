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
package snapads4j.model.device;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Device.
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class Device {

  /** Id */
  private Long id;

  /** Connection Type */
  @JsonProperty("connection_type")
  private String connectionType;

  /** OS Type */
  @JsonProperty("os_type")
  private String osType;

  /** OS Version */
  @JsonProperty("os_version")
  private Double osVersion;

  /** OS min Version */
  @JsonProperty("os_version_min")
  private Double osMinVersion;

  /** OS max Version */
  @JsonProperty("os_version_max")
  private Double osMaxVersion;

  /** Carrier IDs */
  @JsonProperty("carrier_id")
  private List<String> carrierIds;

  /** Marketing names */
  @JsonProperty("marketing_name")
  private List<String> marketingNames;
} // Device
