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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.SchemaEnum;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class FormUserForAudienceSegment {

    /**
     * Segment ID
     */
    @Getter
    @Setter
    private String id;

    /**
     * List of one type of Schema
     */
    @Getter
    private List<SchemaEnum> schema;

    /**
     * List of hashed identifiers
     */
    @Getter
    @Setter
    private List<String> data;

    public FormUserForAudienceSegment() {
        this.schema = new ArrayList<>();
    }// FormUserForAudienceSegment()

    public void setSchema(SchemaEnum schema) {
        if (schema != null) {
            this.schema.clear();
            this.schema.add(schema);
        }
    }// setSchema()

}// FormUserForAudienceSegment()
