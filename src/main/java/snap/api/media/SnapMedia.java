package snap.api.media;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Getter;
import lombok.Setter;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.media.CreativeMedia;
import snap.api.model.media.SnapHttpRequestMedia;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

@Getter
@Setter
public class SnapMedia implements SnapMediaInterface {

    private FileProperties fp;

    private String apiUrl;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapMedia.class);
    
    private String endpointCreation;
    
    public SnapMedia() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointCreation = this.apiUrl + (String) fp.getProperties().get("api.url.media.create");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapMedia()

    @Override
    public void createMedia(String oAuthAccessToken, CreativeMedia media)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	checkCreativeMedia(media);
	final String url = this.endpointCreation.replace("{ad_account_id}", media.getAdAccountId());
	SnapHttpRequestMedia reqBody = new SnapHttpRequestMedia();
	reqBody.addMedia(media);
	HttpPost request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to create creative media, ad_account_id = {}", media.getAdAccountId(), e);
	}
    }// createMedia()

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

    /**
     * Check Creative Media instance
     * @param media creative media
     * @throws SnapArgumentException
     */
    public void checkCreativeMedia(CreativeMedia media) throws SnapArgumentException {
	StringBuilder sb = new StringBuilder();
	if (media != null) {
	    if (StringUtils.isEmpty(media.getAdAccountId())) {
		sb.append("The Ad Account ID is required,");
	    }
	    if (StringUtils.isEmpty(media.getName())) {
		sb.append("The media's name is required,");
	    }
	    if (media.getType() == null) {
		sb.append("The media's type is required,");
	    }
	} else {
	    sb.append("Media parameter is missing,");
	}
	String finalErrors = sb.toString();
	if (!StringUtils.isEmpty(finalErrors)) {
	    finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
	    throw new SnapArgumentException(finalErrors);
	}
    }// checkCreativeMedia()

}// SnapMedia
