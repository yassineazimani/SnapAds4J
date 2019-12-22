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
package snapads4j.model.audience.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.SourceTypeEnum;
import snapads4j.model.AbstractSnapModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@ToString
public class SamLookalikes extends AbstractSnapModel {

    @JsonProperty("ad_account_id")
    @NotEmpty(message = "The Ad Account ID is required")
    protected String adAccountId;

    protected String description;

    @NotEmpty(message = "The name is required")
    protected String name;

    @JsonProperty("retention_in_days")
    @Min(value = 0, message = "The retention must be equal or greater than zero days")
    protected int retentionInDays;

    @JsonProperty("source_type")
    protected SourceTypeEnum sourceType;

    @JsonProperty("creation_spec")
    protected CreationSpec creationSpec;

}// SamLookalikes
