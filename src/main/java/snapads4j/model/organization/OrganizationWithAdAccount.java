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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import snapads4j.model.config.ConfigurationSettings;

import java.util.List;
import java.util.Map;

/**
 * Organization with ad account
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationWithAdAccount extends Organization {

    /**
     * Contact name
     */
    @JsonProperty("contact_name")
    private String contact_name;

    /**
     * Contact email
     */
    @JsonProperty("contact_email")
    private String contact_email;

    /**
     * Contact phone
     */
    @JsonProperty("contact_phone")
    private String contact_phone;

    /**
     * Tax type
     */
    @JsonProperty("tax_type")
    private String taxType;

    /**
     * Partner organizations
     */
    @JsonProperty("partner_orgs")
    private Map<String, String> partnerOrgs;

    /**
     * Accepted term version
     */
    @JsonProperty("accepted_term_version")
    private Double acceptedTermVersion;

    /**
     * Is agency ?
     */
    @JsonProperty("is_agency")
    private boolean isAgency;

    /**
     * Configuration settings
     */
    @JsonProperty("configuration_settings")
    private ConfigurationSettings configurationSettings;

    /**
     * State
     */
    private String state;

    /**
     * Roles
     *
     * @see <a href="https://businesshelp.snapchat.com/en-US/a/roles-permissions">Roles
     * permissions</a>
     */
    private List<String> roles;

    /**
     * Ad accounts bound to this organization
     */
    @JsonProperty("ad_accounts")
    private List<AdAccount> adAccounts;

    /**
     * Display name
     */
    @JsonProperty("my_display_name")
    private String myDisplayName;

    /**
     * Invited email
     */
    @JsonProperty("my_invited_email")
    private String myInvitedEmail;

    /**
     * Member ID
     */
    @JsonProperty("my_member_id")
    private String myMemberId;
} // OrganizationWithAdAccount
