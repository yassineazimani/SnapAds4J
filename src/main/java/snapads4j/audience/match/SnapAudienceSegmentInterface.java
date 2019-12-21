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
package snapads4j.audience.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import snapads4j.exceptions.*;
import snapads4j.model.audience.match.AudienceSegment;
import snapads4j.model.audience.match.FormUserForAudienceSegment;
import snapads4j.model.audience.match.SamLookalikes;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface SnapAudienceSegmentInterface {

    Optional<AudienceSegment> createAudienceSegment(String oAuthAccessToken, AudienceSegment segment)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

    Optional<AudienceSegment> updateAudienceSegment(String oAuthAccessToken, AudienceSegment segment)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

    List<AudienceSegment> getAllAudienceSegments(String oAuthAccessToken, String adAccountID)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException;

    Optional<AudienceSegment> getSpecificAudienceSegment(String oAuthAccessToken, String segmentID)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException;

    int addUserToSegment(String oAuthAccessToken, FormUserForAudienceSegment formUserForAudienceSegment)
            throws SnapOAuthAccessTokenException, JsonProcessingException, UnsupportedEncodingException,
            SnapResponseErrorException, SnapArgumentException, SnapNormalizeArgumentException, SnapExecutionException;

    int deleteUserFromSegment(String oAuthAccessToken, FormUserForAudienceSegment formUserForAudienceSegment)
            throws SnapOAuthAccessTokenException, JsonProcessingException, UnsupportedEncodingException,
            SnapResponseErrorException, SnapArgumentException, SnapNormalizeArgumentException, SnapExecutionException;

    Optional<AudienceSegment> deleteAllUsersFromSegment(String oAuthAccessToken, String segmentID)
            throws SnapOAuthAccessTokenException,
            SnapResponseErrorException, SnapArgumentException, SnapExecutionException;

    Optional<AudienceSegment> createSamLookalikes(String oAuthAccessToken, SamLookalikes sam)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

}// SnapAudienceSegmentInterface
