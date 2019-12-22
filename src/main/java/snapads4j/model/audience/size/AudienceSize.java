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
package snapads4j.model.audience.size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Audience Size
 *
 * @see {https://developers.snapchat.com/api/docs/#audience-size}
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AudienceSize {

    /**
     * Ad Squad ID
     */
    @JsonProperty("ad_squad_id")
    private String adSquadId;

    /**
     * Audience size minimum
     */
    @JsonProperty("audience_size_minimum")
    private long audienceSizeMinimum;

    /**
     * Audience size maximum
     */
    @JsonProperty("audience_size_maximum")
    private long audienceSizeMaximum;

}// AudienceSize
