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
package snapads4j.audience.size;

import com.fasterxml.jackson.core.JsonProcessingException;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.adsquads.AdSquad;
import snapads4j.model.audience.size.AudienceSize;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface SnapAudienceSizeInterface {

    Optional<AudienceSize> getAudienceSizeByTargetingSpec(String oAuthAccessToken, String adAccountID, AdSquad adSquad) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

    Optional<AudienceSize> getAudienceSizeByAdSquadId(String oAuthAccessToken, String adSquadID) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException;

}// SnapAudienceSizeInterface
