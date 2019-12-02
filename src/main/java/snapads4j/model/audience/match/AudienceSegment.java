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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.SourceTypeEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.enums.TargetableStatusEnum;
import snapads4j.enums.UploadStatusEnum;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@ToString
public class AudienceSegment {
    
    private String id;
    
    @JsonProperty("ad_account_id")
    private String adAccountId;
    
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date updatedAt;
    
    private String description;
    
    private String name;
    
    @JsonProperty("retention_in_days")
    private int retentionInDays;
    
    @JsonProperty("source_type")
    private SourceTypeEnum sourceType;
    
    @JsonProperty("approximate_number_users")
    private int approximateNumberUsers;
    
    private StatusEnum status;

    @JsonProperty("upload_status")
    private UploadStatusEnum uploadStatus;
    
    @JsonProperty("targetable_status")
    private TargetableStatusEnum targetableStatus;
    
    public AudienceSegment() {
	this.retentionInDays = 9999;
    }// AudienceSegment()
    
}// AudienceSegment
