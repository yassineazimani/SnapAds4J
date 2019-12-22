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
package snapads4j.fundingsource;

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.fundingsource.FundingSource;

import java.util.List;
import java.util.Optional;

/**
 * SnapFundingSourceInterface
 *
 * @author Yassine
 */
public interface SnapFundingSourceInterface {

    /**
     * Get all funding sources for the specified Organization.
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param organizationID   Organization ID
     * @return funding sources
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException
     * @see <a href="https://developers.snapchat.com/api/docs/#get-all-funding-sources">All funding
     * source</a>
     */
    List<FundingSource> getAllFundingSource(String oAuthAccessToken, String organizationID)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

    /**
     * Get a specific funding source.
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               FundingSource ID
     * @return funding source
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException
     * @see <a href="https://developers.snapchat.com/api/docs/#get-a-specific-funding-source">Specific
     * funding source</a>
     */
    Optional<FundingSource> getSpecificFundingSource(String oAuthAccessToken, String id)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;
} // SnapFundingSourceInterface
