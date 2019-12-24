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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.AdAccountTypeEnum;
import snapads4j.enums.CurrencyEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.model.AbstractSnapModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * AdAccount
 *
 * @see {https://developers.snapchat.com/api/docs/#ad-accounts}
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class AdAccount extends AbstractSnapModel {

    /**
     * AD Account Status
     */
    private StatusEnum status;

    /**
     * Name of the Advertiser
     */
    @NotEmpty(message = "The name of advertiser is required")
    private String advertiser;

    /**
     * Account currency
     */
    @NotNull(message = "The currency is required")
    private CurrencyEnum currency;

    /**
     * Account name
     */
    @NotEmpty(message = "The name is required")
    private String name;

    /**
     * Organization ID
     */
    @JsonProperty("organization_id")
    @NotEmpty(message = "The organization ID is required")
    private String organizationId;

    /**
     * Timezone
     */
    @NotEmpty(message = "The time zone is required")
    private String timezone;

    /**
     * Account type
     */
    @NotNull(message = "The ad account type is required")
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
    @NotEmpty(message = "The funding source ids are required")
    private List<String> fundingSourceIds;

    /**
     * Brand name
     */
    @JsonProperty("brand_name")
    private String brandName;
} // AdAccount
