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
package snapads4j.media;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snapads4j.enums.MediaTypeImageEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.media.CreativeMedia;

public interface SnapMediaInterface {
    
    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param media
     * @return Media created
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    public Optional<CreativeMedia> createMedia(String oAuthAccessToken, CreativeMedia media) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @param fileVideo
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    public void uploadMediaVideo(String oAuthAccessToken, String mediaId, File fileVideo)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @param fileImage
     * @param typeImage
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException 
     */
    public void uploadMediaImage(String oAuthAccessToken, String mediaId,  File fileImage, MediaTypeImageEnum typeImage)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    /**
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @param filename
     * @param chunks
     * @return 
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @return
     * @throws SnapExecutionException 
     */
    public Optional<String> uploadLargeMedia(String oAuthAccessToken, String mediaId, String filename, List<File> chunks) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    /**
     * @see
     * @param oAuthAccessToken
     * @param adAccountId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException 
     */
    public List<CreativeMedia> getAllMedia(String oAuthAccessToken, String adAccountId)
	      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException 
     */
    public Optional<CreativeMedia> getSpecificMedia(String oAuthAccessToken, String mediaId) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException 
     */
    public Map<String, Object> getPreviewOfSpecificMedia(String oAuthAccessToken, String mediaId) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;
    
    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException 
     */
    public Map<String, Object> getThumbnailOfSpecificMedia(String oAuthAccessToken, String mediaId) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

}// SnapMediaInterface()
