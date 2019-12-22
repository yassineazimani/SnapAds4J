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

/**
 * Audit Log
 *
 * @see {https://developers.snapchat.com/api/docs/#audit-logs}
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AuditLog extends AbstractSnapModel {

    /**
     * Name
     */
    private String name;

    /**
     * Action
     */
    private String action;

    /**
     * User ID
     */
    @JsonProperty("user_id")
    private String userId;

    /**
     * Email
     */
    private String email;

    /**
     * Event date
     */
    @JsonProperty("event_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date eventAt;

    /**
     * App ID
     */
    @JsonProperty("app_id")
    private String appId;

    /**
     * App name
     */
    @JsonProperty("app_name")
    private String appName;

    /**
     * Entity ID
     */
    @JsonProperty("entity_id")
    private String entityId;

    /**
     * Entity Type
     */
    @JsonProperty("entity_type")
    private TypeAuditLogEnum entityType;

    /**
     * History of changes
     */
    @JsonProperty("update_value_records")
    private Map<String, Map<String, String>> updateValueRecords;

}// UpdateValueRecords
