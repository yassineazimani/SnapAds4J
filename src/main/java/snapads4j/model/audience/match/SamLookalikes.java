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

/**
 * A lookalike segment.
 *
 * @see {https://developers.snapchat.com/api/docs/#sam-lookalikes}
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@ToString
public class SamLookalikes extends AbstractSnapModel {

    /**
     * Ad Account ID
     */
    @JsonProperty("ad_account_id")
    @NotEmpty(message = "The Ad Account ID is required")
    protected String adAccountId;

    /**
     * Description
     */
    protected String description;

    /**
     * Audience Segment name
     */
    @NotEmpty(message = "The name is required")
    protected String name;

    /**
     * Number days to retain audience members
     */
    @JsonProperty("retention_in_days")
    @Min(value = 0, message = "The retention must be equal or greater than zero days")
    protected int retentionInDays;

    /**
     * Data source type
     */
    @JsonProperty("source_type")
    protected SourceTypeEnum sourceType;

    /**
     * Lookalike Creation Spec dictionary
     */
    @JsonProperty("creation_spec")
    protected CreationSpec creationSpec;

}// SamLookalikes
