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
package snapads4j.model.adaccount;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.AdAccountTypeEnum;
import snapads4j.enums.CurrencyEnum;
import snapads4j.enums.StatusEnum;

import java.util.Date;
import java.util.List;

/**
 * AdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class AdAccount {

    /**
     * AD Account ID
     */
    private String id;

    /**
     * Last date update of ad account
     */
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date updatedAt;

    /**
     * Creation date update of ad account
     */
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date createdAt;

    /**
     * AD Account Status
     */
    private StatusEnum status;

    /**
     * Name of the Advertiser
     */
    private String advertiser;

    /**
     * Account currency
     */
    private CurrencyEnum currency;

    /**
     * Account name
     */
    private String name;

    /**
     * Organization ID
     */
    @JsonProperty("organization_id")
    private String organizationId;

    /**
     * Timezone
     */
    private String timezone;

    private AdAccountTypeEnum type;

    /**
     * Lifetime Spend Limit for Account (micro currency)
     */
    @JsonProperty("lifetime_spend_cap_micro")
    private Double lifetimeSpendCapMicro;

    /**
     * Organization ID of the Advertiser selected
     */
    @JsonProperty("advertiser_organization_id")
    private String advertiserOrganizationId;

    /**
     * List of Funding Source IDs
     */
    @JsonProperty("funding_source_ids")
    private List<String> fundingSourceIds;

    /**
     * Brand name
     */
    @JsonProperty("brand_name")
    private String brandName;
} // AdAccount
