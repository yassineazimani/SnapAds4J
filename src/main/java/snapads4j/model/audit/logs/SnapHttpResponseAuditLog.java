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

import lombok.NoArgsConstructor;
import lombok.Setter;
import snapads4j.model.SnapHttpResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SnapHttpAuditLogAd
 *
 * @author yassine
 */
@Setter
@NoArgsConstructor
public class SnapHttpResponseAuditLog extends SnapHttpResponse {

    private List<SnapInnerAuditLog> changelogs;

    public List<AuditLog> getAllAuditLogs() {
        return changelogs.stream().map(SnapInnerAuditLog::getChangelog).collect(Collectors.toList());
    }// getAllAuditLogs()

}// SnapHttpAuditLogAd
