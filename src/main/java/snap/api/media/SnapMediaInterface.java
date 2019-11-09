package snap.api.media;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.media.CreativeMedia;

public interface SnapMediaInterface {
    
    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param media
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public void createMedia(String oAuthAccessToken, CreativeMedia media) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
    JsonProcessingException, UnsupportedEncodingException;
    
    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param media
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public void uploadMedia(String oAuthAccessToken, CreativeMedia media) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
    JsonProcessingException, UnsupportedEncodingException;
    
    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param media
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public void uploadLargeMedia(String oAuthAccessToken, CreativeMedia media) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
    JsonProcessingException, UnsupportedEncodingException;
    
    /**
     * @see
     * @param oAuthAccessToken
     * @param adAccountId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     */
    public List<CreativeMedia> getAllMedia(String oAuthAccessToken, String adAccountId)
	      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     */
    public Optional<CreativeMedia> getSpecificMedia(String oAuthAccessToken, String mediaId) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     */
    public String getPreviewOfSpecificMedia(String oAuthAccessToken, String mediaId) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;
    
    /**
     * 
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @return
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     */
    public String getThumbnailOfSpecificMedia(String oAuthAccessToken, String mediaId) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;
}// SnapMediaInterface()
