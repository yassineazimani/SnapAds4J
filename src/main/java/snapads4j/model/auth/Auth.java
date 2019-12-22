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

import lombok.Getter;
import lombok.Setter;

/**
 * Auth
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
public class Auth {

    /**
     * “authorization_code” if using code; “refresh_token” if using a refresh token
     */
    private String grantType;

    /**
     * URLEncoded Redirect URI. Required when grant_type=authorization_code. Must match redirect_uri from the previous /authorize call
     */
    private String redirectUri;

    /**
     * Code
     */
    private String code;

    /**
     * Client ID
     */
    private String clientId;

    /**
     * Client Secret
     */
    private String clientSecret;

}// Auth
