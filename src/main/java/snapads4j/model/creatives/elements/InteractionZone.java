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
package snapads4j.model.creatives.elements;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * Interaction Zone
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@ToString
public class InteractionZone {

    /**
     * Id
     */
    private String id;

    /**
     * Date creation
     */
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date createdAt;

    /**
     * Date update
     */
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date updatedAt;

    /**
     * Ad Account ID
     */
    @JsonProperty("ad_account_id")
    @NotEmpty(message = "The interaction zone's ad account id is required")
    private String adAccountId;

    /**
     * Name
     */
    @NotEmpty(message = "The interaction zone's name is required")
    private String name;

    /**
     * Headline
     */
    @NotEmpty(message = "The interaction zone's headline is required")
    private String headline;

    /**
     * Interaction zone's creative elements
     */
    @JsonProperty("creative_element_ids")
    @NotEmpty(message = "The interaction zone's creative elements is required")
    private List<String> creativeElements;

}// InteractionZone
