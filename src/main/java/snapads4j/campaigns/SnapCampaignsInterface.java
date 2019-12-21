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
package snapads4j.campaigns;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.campaigns.Campaign;

/**
 * SnapCampaignsInterface
 *
 * @author Yassine
 */
public interface SnapCampaignsInterface {

    /**
     * Create a campaign.
     *
     * @see <a href=
     *      "https://developers.snapchat.com/api/docs/#create-a-campaign">Create
     *      campaign</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @param campaign         Campaign to create {@link Campaign}
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @return Campaign created
     * @throws SnapExecutionException 
     */
    Optional<Campaign> createCampaign(String oAuthAccessToken, Campaign campaign)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

    /**
     * Update a campaign.
     *
     * @see <a href=
     *      "https://developers.snapchat.com/api/docs/#update-a-campaign">Update
     *      campaign</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @param campaign         Campaign to update {@link Campaign}
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @return Campaign updated
     * @throws SnapExecutionException 
     */
    Optional<Campaign> updateCampaign(String oAuthAccessToken, Campaign campaign)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

    /**
     * Get all campaigns of an ad account.
     *
     * @see <a href=
     *      "https://developers.snapchat.com/api/docs/#get-all-campaigns">Get all
     *      campaigns</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @param adAccountId      AD Account ID
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @return all campaigns {@link Campaign}
     * @throws SnapExecutionException 
     */
    List<Campaign> getAllCampaigns(String oAuthAccessToken, String adAccountId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

    /**
     * Get specific campaign.
     *
     * @see <a href=
     *      "https://developers.snapchat.com/api/docs/#get-a-specific-campaign">Get
     *      specific campaign</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               Campaign ID to get
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @return campaign {@link Campaign}
     * @throws SnapExecutionException 
     */
    Optional<Campaign> getSpecificCampaign(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

    /**
     * Delete a specific campaign.
     *
     * @see <a href=
     *      "https://developers.snapchat.com/api/docs/#delete-a-specific-campaign">Delete
     *      campaign</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               Campaign ID to delete
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException 
     */
    void deleteCampaign(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;
} // SnapCampaignsInterface
