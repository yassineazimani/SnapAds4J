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
package snapads4j.creatives;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.creatives.Creative;

public interface SnapCreativeInterface {
    
    /**
     * This function will create a Creative.
     * 
     * @param oAuthAccessToken
     * @param creative Creative to create
     * @return creative created
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    Optional<Creative> createCreative(String oAuthAccessToken, Creative creative)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    /**
     * This function will update the specified Creative.
     * 
     * <b>The API expects the entire object when updating any fields.</b>
     * 
     * @param oAuthAccessToken
     * @param creative Creative to update
     * @return creative updated
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    Optional<Creative> updateCreative(String oAuthAccessToken, Creative creative)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    /**
     * 
     * @param oAuthAccessToken
     * @param id
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    Optional<Creative> getSpecificCreative(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    /**
     * 
     * @param oAuthAccessToken
     * @param adAccountId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    List<Creative> getAllCreative(String oAuthAccessToken, String adAccountId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    /**
     * 
     * @param oAuthAccessToken
     * @param creativeID
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException 
     */
    Map<String, Object> getPreviewCreative(String oAuthAccessToken, String creativeID) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;
}// SnapCreativeInterface
