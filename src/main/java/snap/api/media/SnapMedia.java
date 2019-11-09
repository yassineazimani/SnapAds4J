package snap.api.media;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Getter;
import lombok.Setter;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.media.CreativeMedia;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.FileProperties;

@Getter
@Setter
public class SnapMedia implements SnapMediaInterface {

    private FileProperties fp;

    private String apiUrl;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapMedia.class);

    @Override
    public void createMedia(String oAuthAccessToken, CreativeMedia media)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	// TODO Auto-generated method stub

    }

    @Override
    public void uploadMedia(String oAuthAccessToken, CreativeMedia media)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	// TODO Auto-generated method stub

    }

    @Override
    public void uploadLargeMedia(String oAuthAccessToken, CreativeMedia media)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	// TODO Auto-generated method stub

    }

    @Override
    public List<CreativeMedia> getAllMedia(String oAuthAccessToken, String adAccountId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Optional<CreativeMedia> getSpecificMedia(String oAuthAccessToken, String mediaId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getPreviewOfSpecificMedia(String oAuthAccessToken, String mediaId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getThumbnailOfSpecificMedia(String oAuthAccessToken, String mediaId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

}// SnapMedia
