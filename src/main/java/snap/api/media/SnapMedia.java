package snap.api.media;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
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
import snap.api.enums.MediaTypeEnum;
import snap.api.enums.MediaTypeImageEnum;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.media.CreativeMedia;
import snap.api.model.media.SnapHttpRequestMedia;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.FileProperties;
import snap.api.utils.FileUtils;
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
    
    private String endpointUploadVideo;
    
    private String endpointUploadImage;

    private final long maxLengthVideo;

    private final long maxLengthTopSnapImage;

    private int minWidthAppIcon;

    private int minHeightAppIcon;

    private int minWidthTopSnapImage;

    private int minHeightTopSnapImage;

    public SnapMedia() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointCreation = this.apiUrl + (String) fp.getProperties().get("api.url.media.create");
	this.endpointUploadVideo = this.apiUrl + fp.getProperties().getProperty("api.url.media.upload.video");
	this.endpointUploadImage = this.apiUrl + fp.getProperties().getProperty("api.url.media.upload.image");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
	this.minWidthAppIcon = Integer.valueOf((String) fp.getProperties().get("api.app.icon.min.width"));
	this.minHeightAppIcon = Integer.valueOf((String) fp.getProperties().get("api.app.icon.min.height"));
	this.minWidthTopSnapImage = Integer.valueOf((String) fp.getProperties().get("api.top.image.min.width"));
	this.minHeightTopSnapImage = Integer.valueOf((String) fp.getProperties().get("api.top.image.min.height"));
	this.maxLengthVideo = Long.valueOf((String) fp.getProperties().get("api.video.max.size"));
	this.maxLengthTopSnapImage = Long.valueOf((String) fp.getProperties().get("api.top.image.min.size"));
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
    public void uploadMediaVideo(String oAuthAccessToken, String mediaId, File fileVideo)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(mediaId)) {
	    throw new SnapArgumentException("Media ID is missing");
	}
	checkUploadMedia(fileVideo, MediaTypeEnum.VIDEO, null);
	final String url = this.endpointUploadVideo.replace("{media_id}", mediaId);
	HttpPost request = HttpUtils.preparePostUpload(url, oAuthAccessToken, fileVideo);
	try(CloseableHttpResponse response = httpClient.execute(request)){
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	}catch(IOException e) {
	    LOGGER.error("Impossible to upload media video, mediaId = {}", mediaId, e);
	}
    }// uploadMediaVideo()

    @Override
    public void uploadMediaImage(String oAuthAccessToken, String mediaId, File fileImage, MediaTypeImageEnum typeImage)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(mediaId)) {
	    throw new SnapArgumentException("Media ID is missing");
	}
	checkUploadMedia(fileImage, MediaTypeEnum.IMAGE, typeImage);
	final String url = this.endpointUploadImage.replace("{media_id}", mediaId);
	HttpPost request = HttpUtils.preparePostUpload(url, oAuthAccessToken, fileImage);
	try(CloseableHttpResponse response = httpClient.execute(request)){
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	}catch(IOException e) {
	    LOGGER.error("Impossible to upload media image, mediaId = {}", mediaId, e);
	}
    }// uploadMediaImage()

    @Override
    public void uploadLargeMedia(String oAuthAccessToken, String mediaId, CreativeMedia media)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}

    }// uploadLargeMedia()

    @Override
    public List<CreativeMedia> getAllMedia(String oAuthAccessToken, String adAccountId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	return null;
    }

    @Override
    public Optional<CreativeMedia> getSpecificMedia(String oAuthAccessToken, String mediaId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	return null;
    }

    @Override
    public String getPreviewOfSpecificMedia(String oAuthAccessToken, String mediaId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	return null;
    }

    @Override
    public String getThumbnailOfSpecificMedia(String oAuthAccessToken, String mediaId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	return null;
    }

    /**
     * Check Creative Media instance
     * 
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

    /**
     * Check Upload Media
     * 
     * @param mediaFile
     * @param type
     * @param typeImage
     * @throws SnapArgumentException
     */
    public void checkUploadMedia(File mediaFile, MediaTypeEnum type, MediaTypeImageEnum typeImage)
	    throws SnapArgumentException {
	String finalErrors = "";
	switch (type) {
	case IMAGE:
	    finalErrors = checkUploadMediaImage(mediaFile, typeImage);
	    break;
	case VIDEO:
	    finalErrors = checkUploadMediaVideo(mediaFile);
	    break;
	case LENS_PACKAGE:
	    break;
	default:
	    break;
	}
	if (!StringUtils.isEmpty(finalErrors)) {
	    finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
	    throw new SnapArgumentException(finalErrors);
	}
    }// checkUploadMedia()

    /**
     * Check Upload Media Image
     * 
     * @param mediaFile
     * @param typeImage
     * @return
     * @throws SnapArgumentException
     */
    private String checkUploadMediaImage(File mediaFile, MediaTypeImageEnum typeImage) throws SnapArgumentException {
	StringBuilder sb = new StringBuilder();
	if (mediaFile != null) {
	    if (typeImage == MediaTypeImageEnum.APP_ICON) {
		checkUploadMediaImageAppIcon(mediaFile, sb);
	    } else if (typeImage == MediaTypeImageEnum.TOP_SNAP) {
		checkUploadMediaImageTopSnap(mediaFile, sb);
	    }
	} else {
	    sb.append("Media parameter is missing,");
	}
	String finalErrors = sb.toString();
	return finalErrors;
    }// checkUploadMediaImage()

    /**
     * Check Upload Media Video
     * 
     * @param mediaFile
     * @return
     * @throws SnapArgumentException
     */
    private String checkUploadMediaVideo(File mediaFile) throws SnapArgumentException {
	StringBuilder sb = new StringBuilder();
	if (mediaFile != null) {
	    if (mediaFile.length() > maxLengthVideo) {
		sb.append("The media's max length mustn't exceed 31.8 MB,");
	    }
	} else {
	    sb.append("Media parameter is missing,");
	}
	String finalErrors = sb.toString();
	return finalErrors;
    }// checkUploadMediaVideo()

    /**
     * Check Upload Media Image App Icon
     * 
     * @param mediaFile
     * @param sb
     */
    private void checkUploadMediaImageAppIcon(File mediaFile, StringBuilder sb) {
	if (sb != null) {
	    // Must be square (ratio 1:1) :
	    try {
		if (!FileUtils.testEqualityWidthHeight(mediaFile)) {
		    sb.append("Media Image must have a ratio 1:1,");
		}
	    } catch (IOException e) {
		sb.append("Impossible to check media image file,");
	    }
	    // Must be only png image :
	    if (!FileUtils.getExtensionFile(mediaFile).equalsIgnoreCase("png")) {
		sb.append("Media Image must be a png file,");
	    }
	    // Minimum resolution: 200x200 :
	    Map<String, Integer> dimension = null;
	    try {
		dimension = FileUtils.getDimensionFile(mediaFile);
	    } catch (IOException e) {
		sb.append("Impossible to get media image dimension,");
	    }
	    if (dimension != null && dimension.get("width") < minWidthAppIcon
		    && dimension.get("height") < minHeightAppIcon) {
		sb.append("Minimum resolution is 200x200,");
	    }
	}
    }// checkUploadMediaImageAppIcon()

    /**
     * Check Upload Media Image Top Image
     * 
     * @param mediaFile
     * @param sb
     */
    private void checkUploadMediaImageTopSnap(File mediaFile, StringBuilder sb) {
	if (sb != null) {
	    // Must be only png, jpg or jpeg image :
	    if (!FileUtils.getExtensionFile(mediaFile).equalsIgnoreCase("png")
		    && !FileUtils.getExtensionFile(mediaFile).equalsIgnoreCase("jpg")
		    && !FileUtils.getExtensionFile(mediaFile).equalsIgnoreCase("jpeg")) {
		sb.append("Media Image must be a (png/jpg/jpeg) file,");
	    }
	    // Minimum resolution - 1080 x 1920 pixels :
	    Map<String, Integer> dimension = null;
	    try {
		dimension = FileUtils.getDimensionFile(mediaFile);
	    } catch (IOException e) {
		sb.append("Impossible to get media image dimension,");
	    }
	    if (dimension != null && dimension.get("width") < minWidthTopSnapImage
		    && dimension.get("height") < minHeightTopSnapImage) {
		sb.append("Minimum resolution is 1080 x 1920,");
	    }
	    // Must be square (ratio 9:16) :
	    if (dimension != null && dimension.get("height") != 0
		    && dimension.get("width") / dimension.get("height") != 9 / 16) {
		sb.append("Ratio image must be 9:16,");
	    }
	    if (mediaFile.length() > maxLengthTopSnapImage) {
		sb.append("The media's max length mustn't exceed 5 MB,");
	    }
	}
    }// checkUploadMediaImageTopSnap()

}// SnapMedia
