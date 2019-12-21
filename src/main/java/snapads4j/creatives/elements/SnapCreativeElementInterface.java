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
package snapads4j.creatives.elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.creatives.elements.CreativeElement;
import snapads4j.model.creatives.elements.InteractionZone;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface SnapCreativeElementInterface {

    /**
     * @param oAuthAccessToken
     * @param creative
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException
     */
    Optional<CreativeElement> createCreativeElement(String oAuthAccessToken, CreativeElement creative)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

    /**
     * @param oAuthAccessToken
     * @param creatives
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException
     */
    List<CreativeElement> createCreativeElements(String oAuthAccessToken, List<CreativeElement> creatives)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

    /**
     * @param oAuthAccessToken
     * @param interactionZone
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException
     */
    Optional<InteractionZone> createInteractionZone(String oAuthAccessToken, InteractionZone interactionZone)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

}// SnapCreativeElementInterface
