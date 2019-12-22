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
package snapads4j.model.audit.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import snapads4j.enums.TypeAuditLogEnum;
import snapads4j.model.AbstractSnapModel;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AuditLog extends AbstractSnapModel {

    private String name;

    private String action;

    @JsonProperty("user_id")
    private String userId;

    private String email;

    @JsonProperty("event_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date eventAt;

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("app_name")
    private String appName;

    @JsonProperty("entity_id")
    private String entityId;

    @JsonProperty("entity_type")
    private TypeAuditLogEnum entityType;

    @JsonProperty("update_value_records")
    private Map<String, Map<String, String>> updateValueRecords;

}// UpdateValueRecords
