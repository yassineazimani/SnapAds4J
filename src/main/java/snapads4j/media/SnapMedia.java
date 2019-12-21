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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.enums.MediaTypeEnum;
import snapads4j.enums.MediaTypeImageEnum;
import snapads4j.exceptions.*;
import snapads4j.model.media.*;
import snapads4j.utils.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

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

    private String endpointUploadLargeMedia;

    private String endpointAllMedias;

    private String endpointSpecificMedia;

    private String endpointPreviewMedia;

    private String endpointThumbnailMedia;

    private final long maxLengthVideo;

    private final long maxLengthTopSnapImage;

    private int minWidthAppIcon;

    private int minHeightAppIcon;

    private int minWidthTopSnapImage;

    private int minHeightTopSnapImage;

    public SnapMedia() {
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointCreation = this.apiUrl + fp.getProperties().get("api.url.media.create");
        this.endpointUploadVideo = this.apiUrl + fp.getProperties().getProperty("api.url.media.upload.video");
        this.endpointUploadImage = this.apiUrl + fp.getProperties().getProperty("api.url.media.upload.image");
        this.endpointUploadLargeMedia = this.apiUrl + fp.getProperties().getProperty("api.url.media.upload.large.init");
        this.endpointAllMedias = this.apiUrl + fp.getProperties().get("api.url.media.all");
        this.endpointSpecificMedia = this.apiUrl + fp.getProperties().get("api.url.media.one");
        this.endpointPreviewMedia = this.apiUrl + fp.getProperties().get("api.url.media.preview");
        this.endpointThumbnailMedia = this.apiUrl + fp.getProperties().get("api.url.media.thumbnail");
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
        this.minWidthAppIcon = Integer.parseInt((String) fp.getProperties().get("api.app.icon.min.width"));
        this.minHeightAppIcon = Integer.parseInt((String) fp.getProperties().get("api.app.icon.min.height"));
        this.minWidthTopSnapImage = Integer.parseInt((String) fp.getProperties().get("api.top.image.min.width"));
        this.minHeightTopSnapImage = Integer.parseInt((String) fp.getProperties().get("api.top.image.min.height"));
        this.maxLengthVideo = Long.parseLong((String) fp.getProperties().get("api.video.max.size"));
        this.maxLengthTopSnapImage = Long.parseLong((String) fp.getProperties().get("api.top.image.min.size"));
    }// SnapMedia()

    @Override
    public Optional<CreativeMedia> createMedia(String oAuthAccessToken, CreativeMedia media)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkCreativeMedia(media);
        Optional<CreativeMedia> result = Optional.empty();
        final String url = this.endpointCreation.replace("{ad_account_id}", media.getAdAccountId());
        SnapHttpRequestMedia reqBody = new SnapHttpRequestMedia();
        reqBody.addMedia(media);
        HttpPost request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseMedia responseFromJson = mapper.readValue(body, SnapHttpResponseMedia.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificMedia();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to create creative media, ad_account_id = {}", media.getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to create creative media", e);
        }
        return result;
    }// createMedia()

    @Override
    public void uploadMediaVideo(String oAuthAccessToken, String mediaId, File fileVideo)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(mediaId)) {
            throw new SnapArgumentException("Media ID is missing");
        }
        checkUploadMedia(fileVideo, MediaTypeEnum.VIDEO, null);
        final String url = this.endpointUploadVideo.replace("{media_id}", mediaId);
        HttpPost request = HttpUtils.preparePostUpload(url, oAuthAccessToken, fileVideo);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to upload media video, mediaId = {}", mediaId, e);
            throw new SnapExecutionException("Impossible to upload media video", e);
        }
    }// uploadMediaVideo()

    @Override
    public void uploadMediaImage(String oAuthAccessToken, String mediaId, File fileImage, MediaTypeImageEnum typeImage)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(mediaId)) {
            throw new SnapArgumentException("Media ID is missing");
        }
        checkUploadMedia(fileImage, MediaTypeEnum.IMAGE, typeImage);
        final String url = this.endpointUploadImage.replace("{media_id}", mediaId);
        HttpPost request = HttpUtils.preparePostUpload(url, oAuthAccessToken, fileImage);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to upload media image, mediaId = {}", mediaId, e);
            throw new SnapExecutionException("Impossible to upload media image", e);
        }
    }// uploadMediaImage()

    @Override
    public Optional<String> uploadLargeMedia(String oAuthAccessToken, String mediaId, String filename,
                                             List<File> chunks) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        Optional<String> result = Optional.empty();
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(mediaId)) {
            throw new SnapArgumentException("Media ID is missing");
        }
        if (StringUtils.isEmpty(filename)) {
            throw new SnapArgumentException("Media's filename is missing");
        }
        if (CollectionUtils.isEmpty(chunks)) {
            throw new SnapArgumentException("Chunks file not providen");
        }
        checkUploadLargeMedia(chunks);
        final String url = this.endpointUploadLargeMedia.replace("{media_id}", mediaId);
        Map<String, String> metaData = new HashMap<>();
        metaData.put("file_name", filename);
        metaData.put("file_size", String.valueOf(FileUtils.getLengthLargeMedia(chunks)));
        metaData.put("number_of_parts", String.valueOf(chunks.size()));
        HttpPost request = HttpUtils.preparePostUpload(url, oAuthAccessToken, null, metaData);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseUploadMedia responseFromJson = mapper.readValue(body,
                        SnapHttpResponseUploadMedia.class);
                result = _uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaId, chunks, responseFromJson);
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to upload large media, mediaId = {}", mediaId, e);
            throw new SnapExecutionException("Impossible to upload large media", e);
        }
        return result;
    }// uploadLargeMedia()

    public Optional<String> _uploadLargeMediaUpdateChunks(String oAuthAccessToken, String mediaId, List<File> chunks,
                                                          SnapHttpResponseUploadMedia responseFromJson) throws SnapResponseErrorException, SnapExecutionException {
        Optional<String> result = Optional.empty();
        if (responseFromJson != null) {
            for (File chunk : chunks) {
                int part = 1;
                String urlPostChunk = responseFromJson.getAddPath();
                Map<String, String> metaDataChunk = new HashMap<>();
                metaDataChunk.put("upload_id", responseFromJson.getUploadId());
                metaDataChunk.put("part_number", String.valueOf(part));
                HttpPost requestChunk = HttpUtils.preparePostUpload(urlPostChunk, oAuthAccessToken, chunk,
                        metaDataChunk);
                try (CloseableHttpResponse responseChunk = httpClient.execute(requestChunk)) {
                    int statusCodeChunk = responseChunk.getStatusLine().getStatusCode();
                    if (statusCodeChunk >= 300) {
                        throw SnapExceptionsUtils
                                .getResponseExceptionByStatusCode(statusCodeChunk);
                    }
                } catch (IOException e) {
                    LOGGER.error("Impossible to upload large media, mediaId = {}", mediaId, e);
                    throw new SnapExecutionException("Impossible to upload large media", e);
                }
                ++part;
            }
            String urlFinalPostChunk = responseFromJson.getFinalizePath();
            Map<String, String> metaDataChunkFinal = new HashMap<>();
            metaDataChunkFinal.put("upload_id", responseFromJson.getUploadId());
            HttpPost requestChunkFinal = HttpUtils.preparePostUpload(urlFinalPostChunk, oAuthAccessToken, null,
                    metaDataChunkFinal);
            try (CloseableHttpResponse responseRequestChunkFinal = httpClient.execute(requestChunkFinal)) {
                int statusCodeFinal = responseRequestChunkFinal.getStatusLine().getStatusCode();
                if (statusCodeFinal >= 300) {
                    throw SnapExceptionsUtils
                            .getResponseExceptionByStatusCode(statusCodeFinal);
                }
                HttpEntity entityFinal = responseRequestChunkFinal.getEntity();
                if (entityFinal != null) {
                    String bodyFinal = entityUtilsWrapper.toString(entityFinal);
                    ObjectMapper mapperFinal = new ObjectMapper();
                    mapperFinal.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    SnapHttpResponseFinalUploadMedia responseFinalFromJson = mapperFinal.readValue(bodyFinal,
                            SnapHttpResponseFinalUploadMedia.class);
                    return _uploadLargeMediaUpdateResponse(responseFinalFromJson);
                }
            } catch (IOException e) {
                LOGGER.error("Impossible to upload large media, mediaId = {}", mediaId, e);
                throw new SnapExecutionException("Impossible to upload large media", e);
            }
        }
        return result;
    }// _uploadLargeMediaUpdateChunks()

    public Optional<String> _uploadLargeMediaUpdateResponse(SnapHttpResponseFinalUploadMedia responseFinalFromJson)
            throws SnapResponseErrorException {
        if (responseFinalFromJson.getRequestStatus().toLowerCase().equals("success") && responseFinalFromJson.getResult() != null) {
            return Optional.ofNullable(responseFinalFromJson.getResult().getId());
        }
        throw new SnapResponseErrorException("Upload large media failed", -1);
    }// _uploadLargeMediaUpdateResponse()

    @Override
    public List<CreativeMedia> getAllMedia(String oAuthAccessToken, String adAccountId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(adAccountId)) {
            throw new SnapArgumentException("The Ad Account ID is missing");
        }
        List<CreativeMedia> results = new ArrayList<>();
        final String url = this.endpointAllMedias.replace("{ad_account_id}", adAccountId);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseMedia responseFromJson = mapper.readValue(body, SnapHttpResponseMedia.class);
                if (responseFromJson != null) {
                    results = responseFromJson.getAllMedia();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get all medias, adAccountId = {}", adAccountId, e);
            throw new SnapExecutionException("Impossible to get all medias", e);
        }
        return results;
    }// getAllMedia()

    @Override
    public Optional<CreativeMedia> getSpecificMedia(String oAuthAccessToken, String mediaId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(mediaId)) {
            throw new SnapArgumentException("The media ID is missing");
        }
        Optional<CreativeMedia> result = Optional.empty();
        final String url = this.endpointSpecificMedia.replace("{media_id}", mediaId);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseMedia responseFromJson = mapper.readValue(body, SnapHttpResponseMedia.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificMedia();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get specific media, mediaId = {}", mediaId, e);
            throw new SnapExecutionException("Impossible to get specific media", e);
        }
        return result;
    }// getSpecificMedia()

    @Override
    public Map<String, Object> getPreviewOfSpecificMedia(String oAuthAccessToken, String mediaId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(mediaId)) {
            throw new SnapArgumentException("The media ID is missing");
        }
        Map<String, Object> result = new HashMap<>();
        final String url = this.endpointPreviewMedia.replace("{media_id}", mediaId);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseLinkMedia responseFromJson = mapper.readValue(body, SnapHttpResponseLinkMedia.class);
                if (responseFromJson != null) {
                    result.put("link", responseFromJson.getLink());
                    result.put("expiresAt", responseFromJson.getExpiresAt());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get preview of media, mediaId = {}", mediaId, e);
            throw new SnapExecutionException("Impossible to get preview of media", e);
        }
        return result;
    }// getPreviewOfSpecificMedia()

    @Override
    public Map<String, Object> getThumbnailOfSpecificMedia(String oAuthAccessToken, String mediaId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(mediaId)) {
            throw new SnapArgumentException("The media ID is missing");
        }
        Map<String, Object> result = new HashMap<>();
        final String url = this.endpointThumbnailMedia.replace("{media_id}", mediaId);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseLinkMedia responseFromJson = mapper.readValue(body, SnapHttpResponseLinkMedia.class);
                if (responseFromJson != null) {
                    result.put("link", responseFromJson.getLink());
                    result.put("expiresAt", responseFromJson.getExpiresAt());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get thumbnail of media, mediaId = {}", mediaId, e);
            throw new SnapExecutionException("Impossible to get thumbnail of media", e);
        }
        return result;
    }// getThumbnailOfSpecificMedia()

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
            default:
                break;
        }
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkUploadMedia()

    /**
     * Check Upload Media chunks
     *
     * @param chunks
     * @return
     * @throws SnapArgumentException
     */
    public void checkUploadLargeMedia(List<File> chunks) throws SnapArgumentException {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(chunks)) {
            for (int i = 0; i < chunks.size(); ++i) {
                File chunk = chunks.get(i);
                int numberChunk = i + 1;
                if (chunk.length() > maxLengthVideo) {
                    sb.append("The chunk's n°").append(numberChunk).append(" max length mustn't exceed 31.8 MB,");
                }
            }
        } else {
            sb.append("Chunks are missing,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkUploadLargeMedia()

    /**
     * Check Upload Media Image
     *
     * @param mediaFile
     * @param typeImage
     * @return
     */
    private String checkUploadMediaImage(File mediaFile, MediaTypeImageEnum typeImage) {
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
        return sb.toString();
    }// checkUploadMediaImage()

    /**
     * Check Upload Media Video
     *
     * @param mediaFile
     * @return
     */
    private String checkUploadMediaVideo(File mediaFile) {
        StringBuilder sb = new StringBuilder();
        if (mediaFile != null) {
            if (mediaFile.length() > maxLengthVideo) {
                sb.append("The media's max length mustn't exceed 31.8 MB,");
            }
        } else {
            sb.append("Media parameter is missing,");
        }
        return sb.toString();
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
