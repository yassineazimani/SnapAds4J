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
package snapads4j.model.organization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.TypeOrganizationEnum;
import snapads4j.model.AbstractSnapModel;

import java.util.Date;

/**
 * Organization
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class Organization extends AbstractSnapModel {

    /**
     * Organization's name
     */
    protected String name;

    /**
     * Organization's address
     */
    @JsonProperty("address_line_1")
    protected String addressLine1;

    /**
     * Organization locality
     */
    protected String locality;

    /**
     * Administrative district
     */
    @JsonProperty("administrative_district_level_1")
    protected String administrativeDistrictLevel1;

    /**
     * Organization country
     */
    protected String country;

    /**
     * Organization postal code
     */
    @JsonProperty("postal_code")
    protected String postalCode;

    /**
     * Type organization
     */
    protected TypeOrganizationEnum type;
} // Organization
