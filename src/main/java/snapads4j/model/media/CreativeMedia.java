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
package snapads4j.model.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.MediaStatusTypeEnum;
import snapads4j.enums.MediaTypeEnum;
import snapads4j.model.AbstractSnapModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * CreativeMedia
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class CreativeMedia extends AbstractSnapModel {

    /**
     * Ad Account ID
     */
    @JsonProperty("ad_account_id")
    @NotEmpty(message = "The Ad Account ID is required")
    private String adAccountId;

    /**
     * Download link of the creative media
     */
    @JsonProperty("download_link")
    private String downloadLink;

    /**
     * Media status
     */
    @JsonProperty("media_status")
    private MediaStatusTypeEnum mediaStatus;

    /**
     * Name
     */
    @NotEmpty(message = "The media's name is required")
    private String name;

    /**
     * Media Type
     */
    @NotNull(message = "The media's type is required")
    private MediaTypeEnum type;

    /**
     * filename
     */
    @JsonProperty("file_name")
    private String fileName;

    /**
     * Doesn't know contents, it doesn't exist in the documentation API.
     */
    @JsonProperty("lens_package_metadata")
    private String lensPackageMetadata;

}// CreativeMedia
