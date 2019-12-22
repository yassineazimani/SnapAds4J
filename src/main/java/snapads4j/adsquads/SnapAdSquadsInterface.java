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
package snapads4j.adsquads;

import com.fasterxml.jackson.core.JsonProcessingException;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.adsquads.AdSquad;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface SnapAdSquadsInterface {

    Optional<AdSquad> createAdSquad(String oAuthAccessToken, AdSquad adSquad)
            throws SnapOAuthAccessTokenException, JsonProcessingException, SnapResponseErrorException,
            SnapArgumentException, UnsupportedEncodingException, SnapExecutionException;

    Optional<AdSquad> updateAdSquad(String oAuthAccessToken, AdSquad adSquad)
            throws SnapOAuthAccessTokenException, JsonProcessingException, SnapResponseErrorException,
            SnapArgumentException, UnsupportedEncodingException, SnapExecutionException;

    List<AdSquad> getAllAdSquadsFromCampaign(String oAuthAccessToken, String campaignId) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException, SnapExecutionException;

    List<AdSquad> getAllAdSquadsFromAdAccount(String oAuthAccessToken, String adAccountId) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException, SnapExecutionException;

    Optional<AdSquad> getSpecificAdSquad(String oAuthAccessToken, String id) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException, SnapExecutionException;

    /**
     * Deletes a specific ad squad.
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               Ad Squad ID
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException
     */
    void deleteAdSquad(String oAuthAccessToken, String id)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;
} // SnapAdSquadsInterface
