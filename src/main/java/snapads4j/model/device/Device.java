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
package snapads4j.model.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Device.
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class Device {

    /**
     * Id
     */
    private Long id;

    /**
     * Connection Type
     */
    @JsonProperty("connection_type")
    private String connectionType;

    /**
     * OS Type
     */
    @JsonProperty("os_type")
    private String osType;

    /**
     * OS Version
     */
    @JsonProperty("os_version")
    private Double osVersion;

    /**
     * OS min Version
     */
    @JsonProperty("os_version_min")
    private Double osMinVersion;

    /**
     * OS max Version
     */
    @JsonProperty("os_version_max")
    private Double osMaxVersion;

    /**
     * Carrier IDs
     */
    @JsonProperty("carrier_id")
    private List<String> carrierIds;

    /**
     * Marketing names
     */
    @JsonProperty("marketing_name")
    private List<String> marketingNames;

    /**
     * Used to build Device instance (${@link Device})
     *
     * @author Yassine
     */
    public static class Builder {

        private final Device deviceInstance;

        public Builder() {
            this.deviceInstance = new Device();
        } // Builder()

        /**
         * Set ID
         *
         * @param id
         * @return
         */
        public Builder setId(Long id) {
            this.deviceInstance.setId(id);
            return this;
        } // setId()

        /**
         * Example : "WIFI" or "CELL"
         *
         * @param connectionType
         * @return
         */
        public Builder setConnectionType(String connectionType) {
            this.deviceInstance.setConnectionType(connectionType);
            return this;
        } // setConnectionType()

        /**
         * Example : "ANDROID" or "iOS"
         *
         * @param osType
         * @return
         */
        public Builder setOSType(String osType) {
            this.deviceInstance.setOsType(osType);
            return this;
        } // setOSType()

        /**
         * Example : 10.3.2
         *
         * @param osVersion
         * @return
         */
        public Builder setOSVersion(Double osVersion) {
            this.deviceInstance.setOsVersion(osVersion);
            return this;
        } // setOSVersion()

        /**
         * Example : 10.3.2
         *
         * @param osVersion
         * @return
         */
        public Builder setOSMinVersion(Double osVersion) {
            this.deviceInstance.setOsMinVersion(osVersion);
            return this;
        } // setOSMinVersion()

        /**
         * Example : 10.3.2
         *
         * @param osVersion
         * @return
         */
        public Builder setOSMaxVersion(Double osVersion) {
            this.deviceInstance.setOsMaxVersion(osVersion);
            return this;
        } // setOSMaxVersion()

        /**
         * Example : "US_ATT"
         *
         * @param carrierIds
         * @return
         */
        public Builder setCarrierId(List<String> carrierIds) {
            this.deviceInstance.setCarrierIds(carrierIds);
            return this;
        } // setCarrierId()

        /**
         * Example : "Apple/iPhone 7 Plus/", "Apple/iPhone 6s Plus/"
         *
         * @param marketingNames
         * @return
         */
        public Builder setMake(List<String> marketingNames) {
            this.deviceInstance.setMarketingNames(marketingNames);
            return this;
        } // setMake()

        public Device build() {
            return this.deviceInstance;
        } // build()
    } // Builder

} // Device
