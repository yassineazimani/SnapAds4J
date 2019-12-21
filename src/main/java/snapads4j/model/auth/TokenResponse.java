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
package snapads4j.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Token Response
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"token_type"})
public class TokenResponse {

    /**
     * Expiration expressed in seconds of the access token.
     */
    @JsonProperty("expires_in")
    private int expiresIn;

    /**
     * Refresh token. Useful to regenerate a new Access Token.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * Access Token. If you receive a status code HTTP 401, you should use your refresh token to
     * generate a new Access Token and retry the request.
     */
    @JsonProperty("access_token")
    private String accessToken;
} // TokenResponse
