package snap.api.media;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snap.api.enums.MediaTypeImageEnum;
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
     * @param mediaId
     * @param fileVideo
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public void uploadMediaVideo(String oAuthAccessToken, String mediaId, File fileVideo)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException;
    
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
     */
    public void uploadMediaImage(String oAuthAccessToken, String mediaId,  File fileImage, MediaTypeImageEnum typeImage)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException;
    
    /**
     * @see
     * @param oAuthAccessToken
     * @param mediaId
     * @param media
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public void uploadLargeMedia(String oAuthAccessToken, String mediaId, CreativeMedia media) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
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
