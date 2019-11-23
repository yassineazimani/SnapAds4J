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
package snapads4j.model.organization;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.CurrencyEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.enums.TypeOrganizationEnum;

/**
 * Ad Accounts handle Ads, billing information, and allows you to manage your Campaigns.
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class AdAccount {

  /** Ad account ID */
  private String id;

  /** Last date update of Ad account */
  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  /** Date creation of Ad account */
  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  /** Ad account name */
  private String name;

  /** Type organization */
  private TypeOrganizationEnum type;

  /** Status */
  private StatusEnum status;

  /** Currency used */
  private CurrencyEnum currency;

  /** Timezone */
  private String timezone;

  /**
   * Ad account roles
   *
   * @see <a href="https://businesshelp.snapchat.com/en-US/a/roles-permissions">Roles
   *     permissions</a>
   */
  private List<String> roles;
} // AdAccount
