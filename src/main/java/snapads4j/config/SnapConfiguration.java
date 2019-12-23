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
package snapads4j.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration to use the API.
 *
 * @author Yassine
 */
@Getter
@Setter
public class SnapConfiguration {

    /**
     * Client ID
     */
    private String clientId;

    /**
     * Client Secret
     */
    private String clientSecret;

    /**
     * URLEncoded Redirect URI
     */
    private String redirectUri;

    /**
     * Build the configuration to use API.
     *
     * @author Yassine
     */
    public static class Builder {

        /**
         * Configuration API
         */
        private final SnapConfiguration snapConfiguration;

        /**
         * Constructor
         */
        public Builder() {
            this.snapConfiguration = new SnapConfiguration();
        } // SnapConfigurationBuilder()

        /**
         * Save client ID in the configuration
         *
         * @param clientId client ID
         * @return SnapConfigurationBuilder
         */
        public Builder setClientId(String clientId) {
            this.snapConfiguration.setClientId(clientId);
            return this;
        } // setClientId()

        /**
         * Save client Secret in the configuration
         *
         * @param clientSecret client secret
         * @return SnapConfigurationBuilder
         */
        public Builder setClientSecret(String clientSecret) {
            this.snapConfiguration.setClientSecret(clientSecret);
            return this;
        } // setClientSecret()

        /**
         * Save redirect URI in the configuration
         *
         * @param redirectUri redirect URI
         * @return SnapConfigurationBuilder
         */
        public Builder setRedirectUri(String redirectUri) {
            this.snapConfiguration.setRedirectUri(redirectUri);
            return this;
        } // setRedirectUri()

        /**
         * Build an instance of SnapConfiguration.
         *
         * @return SnapConfiguration instance
         */
        public SnapConfiguration build() {
            return this.snapConfiguration;
        } // build()
    } // SnapConfigurationBuilder

    private SnapConfiguration(){}

} // SnapConfiguration
