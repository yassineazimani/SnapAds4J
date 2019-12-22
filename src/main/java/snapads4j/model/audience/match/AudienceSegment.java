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
import snapads4j.enums.StatusEnum;
import snapads4j.enums.TargetableStatusEnum;
import snapads4j.enums.UploadStatusEnum;

import java.util.List;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@ToString
public class AudienceSegment extends SamLookalikes {

    @JsonProperty("approximate_number_users")
    private int approximateNumberUsers;

    private StatusEnum status;

    @JsonProperty("upload_status")
    private UploadStatusEnum uploadStatus;

    @JsonProperty("targetable_status")
    private TargetableStatusEnum targetableStatus;

    @JsonProperty("organization_id")
    private String organizationId;

    @JsonProperty("visible_to")
    private List<String> visibleTo;

    public AudienceSegment() {
        this.retentionInDays = 9999;
    }// AudienceSegment()

}// AudienceSegment
