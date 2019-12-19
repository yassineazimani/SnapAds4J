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
package snapads4j.adaccount;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.adaccount.AdAccount;

public interface SnapAdAccountInterface {

  /**
   * Get all ad accounts
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-all-ad-accounts">All Ad
   *     Accounts</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param organizationID Organization ID
   * @return All ad accounts
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
 * @throws SnapExecutionException 
   */
  public List<AdAccount> getAllAdAccounts(String oAuthAccessToken, String organizationID)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

  /**
   * Get specific ad account
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-a-specific-ad-account">Specific Ad
   *     Account</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id Organization ID
   * @return specific ad account
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
 * @throws SnapExecutionException 
   */
  public Optional<AdAccount> getSpecificAdAccount(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

  /**
   * Update a specific ad account
   *
   * @see <a
   *     href="https://developers.snapchat.com/api/docs/#update-an-ad-accounts-lifetime-spend-cap">Update
   *     Ad Account</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param adAccount ad account to update
 * @return 
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @throws JsonProcessingException
   * @throws UnsupportedEncodingException 
   * 
   * @return AdAccount updated
 * @throws SnapExecutionException 
   */
  public Optional<AdAccount> updateAdAccount(String oAuthAccessToken, AdAccount adAccount)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
          JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
} // SnapAdAccountInterface
