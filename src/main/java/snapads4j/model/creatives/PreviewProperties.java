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
package snapads4j.model.creatives;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * PreviewProperties
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@ToString
public class PreviewProperties {

    /**
     * Preview Media ID
     */
    @JsonProperty("preview_media_id")
    @NotEmpty(message = "Preview Media ID (Preview Properties) is required")
    private String previewMediaId;

    /**
     * Logo Media ID
     */
    @JsonProperty("logo_media_id")
    @NotEmpty(message = "Logo Media ID (Preview Properties) is required")
    private String logoMediaId;

    /**
     * Preview Headline
     */
    @JsonProperty("preview_headline")
    @NotEmpty(message = "Preview Headline (Preview Properties) is required")
    private String previewHeadline;

}// PreviewProperties
