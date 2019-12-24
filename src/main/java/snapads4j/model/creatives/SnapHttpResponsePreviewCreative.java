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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import snapads4j.model.SnapHttpResponse;

import java.util.Date;

@Getter
@Setter
public class SnapHttpResponsePreviewCreative extends SnapHttpResponse {

    @JsonProperty("request_status")
    private String requestStatus;

    @JsonProperty("request_id")
    private String requestId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @JsonProperty("expires_at")
    private Date expiresAt;

    @JsonProperty("snapcode_link")
    private String snapCodeLink;

    @JsonProperty("creative_id")
    private String creativeId;

}// SnapHttpResponseLinkMedia
