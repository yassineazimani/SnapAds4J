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
package snapads4j.ads;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.ads.Ad;

/**
 * 
 * @author yassine
 *
 */
public interface SnapAdInterface {

    /**
     * Create an Ad
     * 
     * @param oAuthAccessToken oAuthAccessToken
     * @param ad               Ad model
     * @return Ad created
     * @throws SnapOAuthAccessTokenException
     * @throws JsonProcessingException
     * @throws SnapResponseErrorException
     * @throws SnapArgumentException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    public Optional<Ad> createAd(String oAuthAccessToken, Ad ad) throws SnapOAuthAccessTokenException,
	    JsonProcessingException, SnapResponseErrorException, SnapArgumentException, UnsupportedEncodingException, SnapExecutionException;

    /**
     * Update an Ad
     * 
     * @param oAuthAccessToken oAuthAccessToken
     * @param ad               Ad model
     * @return Ad updated
     * @throws SnapOAuthAccessTokenException
     * @throws JsonProcessingException
     * @throws SnapResponseErrorException
     * @throws SnapArgumentException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    public Optional<Ad> updateAd(String oAuthAccessToken, Ad ad) throws SnapOAuthAccessTokenException,
	    JsonProcessingException, SnapResponseErrorException, SnapArgumentException, UnsupportedEncodingException, SnapExecutionException;

    /**
     * 
     * @param oAuthAccessToken oAuthAccessToken
     * @param adSquadId        AdSquadID
     * @return List of {@link Ad}
     * @throws SnapArgumentException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapResponseErrorException
     * @throws SnapExecutionException 
     */
    public List<Ad> getAllAdsFromAdSquad(String oAuthAccessToken, String adSquadId)
	    throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException, SnapExecutionException;

    /**
     * 
     * @param oAuthAccessToken oAuthAccessToken
     * @param adAccountId      AdAccountID
     * @return List of {@link Ad}
     * @throws SnapArgumentException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapResponseErrorException
     * @throws SnapExecutionException 
     */
    public List<Ad> getAllAdsFromAdAccount(String oAuthAccessToken, String adAccountId)
	    throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException, SnapExecutionException;

    /**
     * 
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               Ad ID
     * @return Optional of {@link Ad}
     * @throws SnapArgumentException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapResponseErrorException
     * @throws SnapExecutionException 
     */
    public Optional<Ad> getSpecificAd(String oAuthAccessToken, String id)
	    throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException, SnapExecutionException;

    /**
     * Deletes a specific ad.
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               Ad ID
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException 
     */
    public void deleteAd(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;
}// SnapAdInterface
