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
import snapads4j.enums.TypeCreationSpecDetails;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@ToString
/**
 * 
 * @author yassine
 *
 */
public class CreationSpec {
    
    public CreationSpec() {
	this.type = TypeCreationSpecDetails.BALANCE;
    }// CreationSpec()

    /**
     * Seed Audience Segment ID
     */
    @JsonProperty("seed_segment_id")
    private String seedSegmentId;
    
    /**
     * ISO-2 Country Code
     */
    private String country;
    
    /**
     * The type of Lookalike to be created
     */
    private TypeCreationSpecDetails type;
    
}// CreationSpec
