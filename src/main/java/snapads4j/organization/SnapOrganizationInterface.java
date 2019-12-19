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
package snapads4j.organization;

import java.util.List;
import java.util.Optional;

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.organization.Organization;
import snapads4j.model.organization.OrganizationWithAdAccount;

/**
 * SnapOrganizationInterface
 *
 * @author Yassine
 */
public interface SnapOrganizationInterface {

  /**
   * Get all organizations
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#organizations">All organizations</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @return All organizations {@link #Organization}
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
 * @throws SnapExecutionException 
   */
  public List<Organization> getAllOrganizations(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapExecutionException;

  /**
   * Get specific organization
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#organizations">Specific
   *     organization</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id Organization ID
   * @return specific organization {@link #Organization}
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
 * @throws SnapExecutionException 
   */
  public Optional<Organization> getSpecificOrganization(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

  /**
   * Get all organizations with ad accounts
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#organizations">All organizations with
   *     ad accounts</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @return All organizations {@link #Organization}
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
 * @throws SnapExecutionException 
   */
  public List<OrganizationWithAdAccount> getAllOrganizationsWithAdAccounts(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapExecutionException;
} // SnapOrganizationInterface
