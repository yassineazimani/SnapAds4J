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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import snapads4j.enums.MediaStatusTypeEnum;
import snapads4j.enums.MediaTypeEnum;
import snapads4j.enums.MediaTypeImageEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.media.CreativeMedia;
import snapads4j.model.media.SnapHttpResponseFinalUploadMedia;
import snapads4j.model.media.SnapHttpResponseUploadMedia;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileUtils;
import snapads4j.utils.SnapResponseUtils;

/**
 * Unit tests mocked for SnapMediaTest
 *
 * @author Yassine
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapMediaTest {

    @Spy
    private SnapMedia snapMedia;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private StatusLine statusLine;

    @Mock
    private EntityUtilsWrapper entityUtilsWrapper;

    @Mock
    private HttpEntity httpEntity;

    private final String oAuthAccessToken = "meowmeowmeow";

    private final String adAccountID = "8adc3db7-8148-4fbf-999c-8d2266369d74";

    private final String mediaID = "a7bee653-1865-41cf-8cee-8ab85a205837";

    private final String largeMediaID = "7536bbc5-0074-4dc4-b654-5ba9cd9f9441";
    
    private CreativeMedia media;

    private CreativeMedia mediaFail;
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapMedia.setHttpClient(httpClient);
	snapMedia.setEntityUtilsWrapper(entityUtilsWrapper);
	media = initializeCreativeMedia("Media A - Video", MediaTypeEnum.VIDEO);
	mediaFail = initializeCreativeMedia("Media A - Video", MediaTypeEnum.VIDEO);
	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    } // setUp()

    @Test
    public void test_create_media_should_success_1() throws IOException, InterruptedException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
	.thenReturn(SnapResponseUtils.getSnapMediaCreated());
	Assertions.assertThatCode(() -> snapMedia.createMedia(oAuthAccessToken, media)).doesNotThrowAnyException();
	Assertions.assertThat(snapMedia.createMedia(oAuthAccessToken, media)).isNotNull();
	snapMedia.createMedia(oAuthAccessToken, media)
		.ifPresent(media -> {
		    Assertions.assertThat(media.getId()).isEqualTo("a7bee653-1865-41cf-8cee-8ab85a205837");
		    Assertions.assertThat(media.getName()).isEqualTo("Media A - Video");
		    Assertions.assertThat(media.getAdAccountId()).isEqualTo(adAccountID);
		    Assertions.assertThat(media.getType()).isEqualTo(MediaTypeEnum.VIDEO);
		    Assertions.assertThat(media.getMediaStatus()).isEqualTo(MediaStatusTypeEnum.PENDING_UPLOAD);
		    assertThat(sdf.format(media.getCreatedAt())).isEqualTo("2016-08-14T06:18:01.855Z");
		    assertThat(sdf.format(media.getUpdatedAt())).isEqualTo("2016-08-14T06:18:01.855Z");
		});
    }// test_create_media_should_success_1()

    @Test
    public void test_create_media_should_success_2() throws IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	CreativeMedia mediaLensPackage = initializeCreativeMedia("Media B - Lens Package", MediaTypeEnum.LENS_PACKAGE);
	Assertions.assertThatCode(() -> snapMedia.createMedia(oAuthAccessToken, mediaLensPackage))
		.doesNotThrowAnyException();
    }// test_create_media_should_success_2()

    @Test
    public void test_create_media_should_success_3() throws IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	CreativeMedia mediaLensPackage = initializeCreativeMedia("Media C - Image", MediaTypeEnum.IMAGE);
	Assertions.assertThatCode(() -> snapMedia.createMedia(oAuthAccessToken, mediaLensPackage))
		.doesNotThrowAnyException();
    }// test_create_media_should_success_3()

    @Test
    public void test_create_media_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapMedia.createMedia(null, media)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    }// test_create_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_media_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapMedia.createMedia("", media)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    }// test_create_media_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_media_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Media parameter is missing");
    }// test_create_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_media_should_throw_SnapArgumentException_2() {
	mediaFail.setName(null);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media's name is required");
    }// test_create_media_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_media_should_throw_SnapArgumentException_3() {
	mediaFail.setName("");
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media's name is required");
    }// test_create_media_should_throw_SnapOAuthAccessTokenException_3()

    @Test
    public void test_create_media_should_throw_SnapArgumentException_4() {
	mediaFail.setType(null);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media's type is required");
    }// test_create_media_should_throw_SnapOAuthAccessTokenException_4()

    @Test
    public void test_create_media_should_throw_SnapArgumentException_5() {
	mediaFail.setAdAccountId(null);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    }// test_create_media_should_throw_SnapOAuthAccessTokenException_5()

    @Test
    public void test_create_media_should_throw_SnapArgumentException_6() {
	mediaFail.setAdAccountId("");
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    }// test_create_media_should_throw_SnapOAuthAccessTokenException_6()

    @Test
    public void test_create_media_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	snapMedia.createMedia(oAuthAccessToken, media);
    }// test_create_media_should_throw_IOException()

    @Test
    public void should_throw_exception_401_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_media()

    @Test
    public void should_throw_exception_403_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_media()

    @Test
    public void should_throw_exception_404_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_media()

    @Test
    public void should_throw_exception_405_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_media()

    @Test
    public void should_throw_exception_406_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_media()

    @Test
    public void should_throw_exception_410_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_media()

    @Test
    public void should_throw_exception_418_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_media()

    @Test
    public void should_throw_exception_429_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_media()

    @Test
    public void should_throw_exception_500_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_media()

    @Test
    public void should_throw_exception_503_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_media()

    @Test
    public void should_throw_exception_1337_create_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_media()

    @Test
    public void test_upload_media_video_should_success()
	    throws SnapArgumentException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    Assertions.assertThatCode(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, mediaID, mediaFile))
		    .doesNotThrowAnyException();
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    }// test_upload_media_video_should_success()

    @Test
    public void test_upload_media_video_should_throw_SnapOAuthAccessTokenException_1() {
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.uploadMediaVideo(null, mediaID, mediaFile))
		    .isInstanceOf(SnapOAuthAccessTokenException.class)
		    .hasMessage("The OAuthAccessToken must to be given");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    }// test_upload_media_video_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_upload_media_video_should_throw_SnapOAuthAccessTokenException_2() {
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.uploadMediaVideo("", mediaID, mediaFile))
		    .isInstanceOf(SnapOAuthAccessTokenException.class)
		    .hasMessage("The OAuthAccessToken must to be given");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    }// test_upload_media_video_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_upload_media_video_should_throw_IOException() throws ClientProtocolException, IOException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    try {
		snapMedia.uploadMediaVideo(oAuthAccessToken, mediaID, mediaFile);
	    } catch (JsonProcessingException | UnsupportedEncodingException | SnapResponseErrorException
		    | SnapOAuthAccessTokenException | SnapArgumentException e) {
		e.printStackTrace();
	    }
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    }// test_upload_media_video_should_throw_IOException()

    @Test
    public void test_upload_media_video_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, mediaID, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Media parameter is missing");
    }// test_upload_media_video_should_throw_SnapArgumentException_1()

    @Test
    public void test_upload_media_video_should_throw_SnapArgumentException_2() {
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/Wolf-27400.mp4", "Wolf-27400.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, mediaID, mediaFile))
		    .isInstanceOf(SnapArgumentException.class)
		    .hasMessage("The media's max length mustn't exceed 31.8 MB");
	});
	FileUtils.deleteFile("Wolf-27400.mp4");
    }// test_upload_media_video_should_throw_SnapArgumentException_2()

    @Test
    public void test_upload_media_video_should_throw_SnapArgumentException_3() {
	assertThatThrownBy(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, "", null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Media ID is missing");
    }// test_upload_media_video_should_throw_SnapArgumentException_3()

    @Test
    public void test_upload_media_video_should_throw_SnapArgumentException_4() {
	assertThatThrownBy(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, null, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Media ID is missing");
    }// test_upload_media_video_should_throw_SnapArgumentException_4()

    @Test
    public void test_upload_media_image_should_success_1()
	    throws SnapArgumentException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    Assertions.assertThatCode(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
		    .doesNotThrowAnyException();
	});
	FileUtils.deleteFile("app-icon-1.png");
    }// test_upload_media_image_should_success_1()

    @Test
    public void test_upload_media_image_should_success_2()
	    throws SnapArgumentException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-2.jpg", "app-icon-2.jpg");
	optFile.ifPresent((mediaFile) -> {
	    Assertions.assertThatCode(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.TOP_SNAP))
		    .doesNotThrowAnyException();
	});
	FileUtils.deleteFile("app-icon-2.jpg");
    }// test_upload_media_image_should_success_2()

    @Test
    public void test_upload_media_image_should_success_3()
	    throws SnapArgumentException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-4.png", "app-icon-4.png");
	optFile.ifPresent((mediaFile) -> {
	    Assertions.assertThatCode(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.TOP_SNAP))
		    .doesNotThrowAnyException();
	});
	FileUtils.deleteFile("app-icon-4.png");
    }// test_upload_media_image_should_success_3()

    @Test
    public void test_upload_media_image_should_success_4()
	    throws SnapArgumentException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-5.jpeg", "app-icon-5.jpeg");
	optFile.ifPresent((mediaFile) -> {
	    Assertions.assertThatCode(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.TOP_SNAP))
		    .doesNotThrowAnyException();
	});
	FileUtils.deleteFile("app-icon-5.jpeg");
    }// test_upload_media_image_should_success_4()

    @Test
    public void test_upload_media_image_should_throw_IOException() throws ClientProtocolException, IOException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-5.jpeg", "app-icon-5.jpeg");
	optFile.ifPresent((mediaFile) -> {
	    try {
		snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.TOP_SNAP);
	    } catch (JsonProcessingException | UnsupportedEncodingException | SnapResponseErrorException
		    | SnapOAuthAccessTokenException | SnapArgumentException e) {
		e.printStackTrace();
	    }
	});
	FileUtils.deleteFile("app-icon-5.jpeg");
    }// test_upload_media_image_should_throw_IOException()

    @Test
    public void test_upload_media_image_should_throw_SnapOAuthAccessTokenException_1() {
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-3.jpeg", "app-icon-3.jpeg");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.uploadMediaImage(null, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
		    .isInstanceOf(SnapOAuthAccessTokenException.class)
		    .hasMessage("The OAuthAccessToken must to be given");
	});
	FileUtils.deleteFile("app-icon-3.jpeg");
    }// test_upload_media_image_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_upload_media_image_should_throw_SnapOAuthAccessTokenException_2() {
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-3.jpeg", "app-icon-3.jpeg");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.uploadMediaImage("", mediaID, mediaFile, MediaTypeImageEnum.TOP_SNAP))
		    .isInstanceOf(SnapOAuthAccessTokenException.class)
		    .hasMessage("The OAuthAccessToken must to be given");
	});
	FileUtils.deleteFile("app-icon-3.jpeg");
    }// test_upload_media_image_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_upload_media_image_should_throw_SnapArgumentException_1() {
	Optional<File> optFile = new FileUtils().getFileFromResources("images/bitmap.bmp", "bitmap.bmp");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapArgumentException.class).hasMessage("Media Image must be a png file");
	});
	FileUtils.deleteFile("bitmap.bmp");
    }// test_upload_media_image_should_throw_SnapArgumentException_1()

    @Test
    public void test_upload_media_image_should_throw_SnapArgumentException_2() {
	Optional<File> optFile = new FileUtils().getFileFromResources("images/bitmap.bmp", "bitmap.bmp");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile,
		    MediaTypeImageEnum.TOP_SNAP)).isInstanceOf(SnapArgumentException.class).hasMessage(
			    "Media Image must be a (png/jpg/jpeg) file,Minimum resolution is 1080 x 1920,Ratio image must be 9:16");
	});
	FileUtils.deleteFile("bitmap.bmp");
    }// test_upload_media_image_should_throw_SnapArgumentException_2()

    @Test
    public void test_upload_media_image_should_throw_SnapArgumentException_3() {
	Optional<File> optFile = new FileUtils().getFileFromResources("images/bigdim.jpg", "bigdim.jpg");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.TOP_SNAP))
			    .isInstanceOf(SnapArgumentException.class)
			    .hasMessage("The media's max length mustn't exceed 5 MB");
	});
	FileUtils.deleteFile("bigdim.jpg");
    }// test_upload_media_image_should_throw_SnapArgumentException_3()

    @Test
    public void test_upload_media_image_should_throw_SnapArgumentException_4() {
	assertThatThrownBy(
		() -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, null, MediaTypeImageEnum.TOP_SNAP))
			.isInstanceOf(SnapArgumentException.class).hasMessage("Media parameter is missing");
    }// test_upload_media_image_should_throw_SnapArgumentException_4()

    @Test
    public void test_upload_media_image_should_throw_SnapArgumentException_5() {
	assertThatThrownBy(
		() -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, null, MediaTypeImageEnum.APP_ICON))
			.isInstanceOf(SnapArgumentException.class).hasMessage("Media parameter is missing");
    }// test_upload_media_image_should_throw_SnapArgumentException_5()

    @Test
    public void test_upload_media_image_should_throw_SnapArgumentException_6() {
	Optional<File> optFile = new FileUtils().getFileFromResources("images/bitmap3.bmp", "bitmap3.bmp");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile,
		    MediaTypeImageEnum.APP_ICON)).isInstanceOf(SnapArgumentException.class).hasMessage(
			    "Media Image must have a ratio 1:1,Media Image must be a png file,Minimum resolution is 200x200");
	});
	FileUtils.deleteFile("bitmap3.bmp");
    }// test_upload_media_image_should_throw_SnapArgumentException_6()

    @Test
    public void test_upload_media_image_should_throw_SnapArgumentException_7() {
	Optional<File> optFile = new FileUtils().getFileFromResources("images/bitmap3.bmp", "bitmap3.bmp");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, "", mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapArgumentException.class).hasMessage("Media ID is missing");
	});
	FileUtils.deleteFile("bitmap3.bmp");
    }// test_upload_media_image_should_throw_SnapArgumentException_7()

    @Test
    public void test_upload_media_image_should_throw_SnapArgumentException_8() {
	Optional<File> optFile = new FileUtils().getFileFromResources("images/bitmap3.bmp", "bitmap3.bmp");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, null, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapArgumentException.class).hasMessage("Media ID is missing");
	});
	FileUtils.deleteFile("bitmap3.bmp");
    }// test_upload_media_image_should_throw_SnapArgumentException_8()

    @Test
    public void should_throw_exception_401_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, mediaID, mediaFile))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_401_upload_media_video()

    @Test
    public void should_throw_exception_403_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_403_upload_media_video()

    @Test
    public void should_throw_exception_404_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_404_upload_media_video()

    @Test
    public void should_throw_exception_405_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_405_upload_media_video()

    @Test
    public void should_throw_exception_406_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_406_upload_media_video()

    @Test
    public void should_throw_exception_410_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_410_upload_media_video()

    @Test
    public void should_throw_exception_418_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_418_upload_media_video()

    @Test
    public void should_throw_exception_429_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class)
		    .hasMessage("Too Many Requests / Rate limit reached");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_429_upload_media_video()

    @Test
    public void should_throw_exception_500_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_500_upload_media_video()

    @Test
    public void should_throw_exception_503_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_503_upload_media_video()

    @Test
    public void should_throw_exception_1337_upload_media_video() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_1337_upload_media_video()

    @Test
    public void should_throw_exception_401_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class)
			    .hasMessage("Unauthorized - Check your API key");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_401_upload_media_image()

    @Test
    public void should_throw_exception_403_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_403_upload_media_image()

    @Test
    public void should_throw_exception_404_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_404_upload_media_image()

    @Test
    public void should_throw_exception_405_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_405_upload_media_image()

    @Test
    public void should_throw_exception_406_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_406_upload_media_image()

    @Test
    public void should_throw_exception_410_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_410_upload_media_image()

    @Test
    public void should_throw_exception_418_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_418_upload_media_image()

    @Test
    public void should_throw_exception_429_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class)
			    .hasMessage("Too Many Requests / Rate limit reached");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_429_upload_media_image()

    @Test
    public void should_throw_exception_500_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_500_upload_media_image()

    @Test
    public void should_throw_exception_503_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_503_upload_media_image()

    @Test
    public void should_throw_exception_1337_upload_media_image() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("images/app-icon-1.png", "app-icon-1.png");
	optFile.ifPresent((mediaFile) -> {
	    assertThatThrownBy(
		    () -> snapMedia.uploadMediaImage(oAuthAccessToken, mediaID, mediaFile, MediaTypeImageEnum.APP_ICON))
			    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
	});
	FileUtils.deleteFile("app-icon-1.png");
    } // should_throw_exception_1337_upload_media_image()

    /*
     * This test is divided into 2 parts : 1)
     * test_upload_large_media_should_success() 2)
     * test_upload_large_media_chunks_should_success
     */
    @Test
    public void test_upload_large_media_should_success()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(snapMedia._uploadLargeMediaUpdateChunks(Mockito.anyString(), Mockito.anyString(),
		Mockito.anyList(), Mockito.any(SnapHttpResponseUploadMedia.class)))
		.thenReturn(Optional.of(largeMediaID));
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getSnapLargeMediaUploadMetaResponses());
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    Assertions.assertThat(snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks)).isPresent();
	    Assertions.assertThat(snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks).get())
		    .isEqualTo(largeMediaID);
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// test_upload_large_media_should_success()

    @Test
    public void test_upload_large_media_chunks_should_success()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapLargeMediaUpload());
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    Assertions.assertThat(snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isPresent();
	    Assertions
		    .assertThat(snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp).get())
		    .isEqualTo(largeMediaID);
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// test_upload_large_media_chunks_should_success()

    @Test
    public void test_upload_large_media_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks);
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// test_upload_large_media_should_throw_IOException()

    @Test
    public void test_upload_large_media_update_response_throw_SnapResponseErrorException() {
	SnapHttpResponseFinalUploadMedia responseFinalFromJson = new SnapHttpResponseFinalUploadMedia();
	responseFinalFromJson.setRequestStatus("fail");
	assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateResponse(responseFinalFromJson))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Upload large media failed")
		.extracting(e -> ((SnapResponseErrorException) e).getStatusCode()).isEqualTo(-1);

    }// test_upload_large_media_update_response_throw_SnapResponseErrorException()

    @Test
    public void test_upload_large_media_chunks_should_throw_IOException()
	    throws ClientProtocolException, IOException, SnapResponseErrorException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp);
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// test_upload_large_media_chunks_should_throw_IOException()

    @Test
    public void test_upload_large_media_should_throw_SnapOAuthAccessTokenException_1() {
	List<File> chunks = Stream.of(new File[] { new File("") }).collect(Collectors.toList());
	assertThatThrownBy(() -> snapMedia.uploadLargeMedia("", mediaID, "final.mov", chunks))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_upload_large_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_upload_large_media_should_throw_SnapOAuthAccessTokenException_2() {
	List<File> chunks = Stream.of(new File[] { new File("") }).collect(Collectors.toList());
	assertThatThrownBy(() -> snapMedia.uploadLargeMedia(null, mediaID, "final.mov", chunks))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_upload_large_media_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_upload_large_media_should_throw_SnapArgumentException_1() {
	List<File> chunks = Stream.of(new File[] { new File("") }).collect(Collectors.toList());
	assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, null, "final.mov", chunks))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Media ID is missing");
    }// test_upload_large_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_upload_large_media_should_throw_SnapArgumentException_2() {
	List<File> chunks = Stream.of(new File[] { new File("") }).collect(Collectors.toList());
	assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, "", "final.mov", chunks))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Media ID is missing");
    }// test_upload_large_media_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_upload_large_media_should_throw_SnapArgumentException_3() {
	List<File> chunks = Stream.of(new File[] { new File("") }).collect(Collectors.toList());
	assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "", chunks))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Media's filename is missing");
    }// test_upload_large_media_should_throw_SnapOAuthAccessTokenException_3()

    @Test
    public void test_upload_large_media_should_throw_SnapArgumentException_4() {
	List<File> chunks = Stream.of(new File[] { new File("") }).collect(Collectors.toList());
	assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, null, chunks))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Media's filename is missing");
    }// test_upload_large_media_should_throw_SnapOAuthAccessTokenException_4()

    @Test
    public void test_upload_large_media_should_throw_SnapArgumentException_5() {
	List<File> chunks = new ArrayList<>();
	assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "final.mov", chunks))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Chunks file not providen");
    }// test_upload_large_media_should_throw_SnapOAuthAccessTokenException_5()

    @Test
    public void test_upload_large_media_should_throw_SnapArgumentException_6() {
	assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "final.mov", null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Chunks file not providen");
    }// test_upload_large_media_should_throw_SnapOAuthAccessTokenException_6()

    @Test
    public void test_upload_large_media_should_throw_SnapArgumentException_7()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/Wolf-27400.mp4", "Wolf-27400.mp4");
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] { optFile.get(), optFile2.get() }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapArgumentException.class)
		    .hasMessage("The chunk's n°2 max length mustn't exceed 31.8 MB");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("Wolf-27400.mp4");
    }// test_upload_large_media_should_throw_SnapArgumentException_7()

    @Test
    public void should_throw_exception_401_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_401_upload_large_media()

    @Test
    public void should_throw_exception_403_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_403_upload_large_media()

    @Test
    public void should_throw_exception_404_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_404_upload_large_media()

    @Test
    public void should_throw_exception_405_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_405_upload_large_media()

    @Test
    public void should_throw_exception_406_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_406_upload_large_media()

    @Test
    public void should_throw_exception_410_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_410_upload_large_media()

    @Test
    public void should_throw_exception_418_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_418_upload_large_media()

    @Test
    public void should_throw_exception_429_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class)
		    .hasMessage("Too Many Requests / Rate limit reached");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_429_upload_large_media()

    @Test
    public void should_throw_exception_500_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_500_upload_large_media()

    @Test
    public void should_throw_exception_503_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_503_upload_large_media()

    @Test
    public void should_throw_exception_1337_upload_large_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent(chuck -> {
	    List<File> chunks = Stream.of(new File[] { chuck }).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia.uploadLargeMedia(oAuthAccessToken, mediaID, "lfv.mp4", chunks))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    } // should_throw_exception_1337_upload_large_media()

    @Test
    public void should_throw_exception_400_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_400_upload_large_media_chunks()

    @Test
    public void should_throw_exception_401_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_401_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_403_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_403_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_404_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks =Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_404_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_405_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_405_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_406_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_406_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_410_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_410_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_418_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_418_upload_large_media_chunks()

    @Test
    public void should_throw_exception_429_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_429_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_500_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_500_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_503_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_503_upload_large_media_chunks()
    
    @Test
    public void should_throw_exception_1337_upload_large_media_chunks()
	    throws IOException, SnapArgumentException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-1.mp4");
	Optional<File> optFile2 = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4",
		"vidsplay-rain-falling-on-window-1-2.mp4");
	String metaResponses = SnapResponseUtils.getSnapLargeMediaUploadMetaResponses();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	SnapHttpResponseUploadMedia resp = mapper.readValue(metaResponses, SnapHttpResponseUploadMedia.class);
	if (optFile.isPresent() && optFile2.isPresent()) {
	    List<File> chunks = Stream.of(new File[] {optFile.get(), optFile2.get()}).collect(Collectors.toList());
	    assertThatThrownBy(() -> snapMedia._uploadLargeMediaUpdateChunks(oAuthAccessToken, mediaID, chunks, resp))
		    .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
	}
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-2.mp4");
    }// should_throw_exception_1337_upload_large_media_chunks()
    
    @Test
    public void test_get_all_media_should_success() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
	.thenReturn(SnapResponseUtils.getSnapAllMedia());
	Assertions.assertThatCode(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID)).doesNotThrowAnyException();
	List<CreativeMedia> medias = snapMedia.getAllMedia(oAuthAccessToken, adAccountID);
	Assertions.assertThat(medias).isNotNull();
	Assertions.assertThat(medias).isNotEmpty();
	Assertions.assertThat(medias.size()).isEqualTo(3);
	Assertions.assertThat(medias.get(0).getId()).isEqualTo("7f65f9ff-63d8-41e7-991a-06b95a1ffbde");
	Assertions.assertThat(medias.get(0).getName()).isEqualTo("Media 2");
	Assertions.assertThat(medias.get(0).getAdAccountId()).isEqualTo(adAccountID);
	Assertions.assertThat(medias.get(0).getType()).isEqualTo(MediaTypeEnum.VIDEO);
	Assertions.assertThat(medias.get(0).getMediaStatus()).isEqualTo(MediaStatusTypeEnum.PENDING_UPLOAD);
	    assertThat(sdf.format(medias.get(0).getCreatedAt())).isEqualTo("2016-08-12T20:39:57.029Z");
	    assertThat(sdf.format(medias.get(0).getUpdatedAt())).isEqualTo("2016-08-12T20:39:57.029Z");
	
	Assertions.assertThat(medias.get(1).getId()).isEqualTo("a7bee653-1865-41cf-8cee-8ab85a205837");
	Assertions.assertThat(medias.get(1).getName()).isEqualTo("Media A - Video");
	Assertions.assertThat(medias.get(1).getFileName()).isEqualTo("sample.mov");
	Assertions.assertThat(medias.get(1).getAdAccountId()).isEqualTo(adAccountID);
	Assertions.assertThat(medias.get(1).getType()).isEqualTo(MediaTypeEnum.VIDEO);
	Assertions.assertThat(medias.get(1).getMediaStatus()).isEqualTo(MediaStatusTypeEnum.READY);
	    assertThat(sdf.format(medias.get(1).getCreatedAt())).isEqualTo("2016-08-14T06:23:37.086Z");
	    assertThat(sdf.format(medias.get(1).getUpdatedAt())).isEqualTo("2016-08-14T06:24:28.378Z");
	
	Assertions.assertThat(medias.get(2).getId()).isEqualTo("ab32d7e5-1f80-4e1a-a76b-3c543d2b28e4");
	Assertions.assertThat(medias.get(2).getName()).isEqualTo("App Icon");
	Assertions.assertThat(medias.get(2).getFileName()).isEqualTo("Mobile Strike.png");
	Assertions.assertThat(medias.get(2).getAdAccountId()).isEqualTo(adAccountID);
	Assertions.assertThat(medias.get(2).getType()).isEqualTo(MediaTypeEnum.IMAGE);
	Assertions.assertThat(medias.get(2).getMediaStatus()).isEqualTo(MediaStatusTypeEnum.READY);
	    assertThat(sdf.format(medias.get(2).getCreatedAt())).isEqualTo("2016-08-12T17:36:59.740Z");
	    assertThat(sdf.format(medias.get(2).getUpdatedAt())).isEqualTo("2016-08-12T17:38:01.918Z");
    
    }// test_get_all_media_should_success()
    
    @Test
    public void test_get_all_media_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapMedia.getAllMedia("", adAccountID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_all_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_all_media_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapMedia.getAllMedia(null, adAccountID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_all_media_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_all_media_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is missing");
    }// test_get_all_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_all_media_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is missing");
    }// test_get_all_media_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_all_media_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapMedia.getAllMedia(oAuthAccessToken, adAccountID);
    }// test_all_media_should_throw_IOException()
    
    @Test
    public void should_throw_exception_400_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_all_media()

    @Test
    public void should_throw_exception_401_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_all_media()

    @Test
    public void should_throw_exception_403_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_all_media()

    @Test
    public void should_throw_exception_404_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_all_media()

    @Test
    public void should_throw_exception_405_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_all_media()

    @Test
    public void should_throw_exception_406_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_all_media()

    @Test
    public void should_throw_exception_410_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_all_media()

    @Test
    public void should_throw_exception_418_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_all_media()

    @Test
    public void should_throw_exception_429_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_all_media()

    @Test
    public void should_throw_exception_500_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_all_media()

    @Test
    public void should_throw_exception_503_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_all_media()

    @Test
    public void should_throw_exception_1337_all_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getAllMedia(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_all_media()
    
    @Test
    public void test_get_specific_media_should_success() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
	.thenReturn(SnapResponseUtils.getSnapSpecificMedia());
	Assertions.assertThatCode(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID)).doesNotThrowAnyException();
	Assertions.assertThat(snapMedia.getSpecificMedia(oAuthAccessToken, mediaID)).isNotNull();
	snapMedia.getSpecificMedia(oAuthAccessToken, mediaID)
		.ifPresent(media -> {
		    Assertions.assertThat(media.getId()).isEqualTo("a7bee653-1865-41cf-8cee-8ab85a205837");
		    Assertions.assertThat(media.getName()).isEqualTo("Media A - Video");
		    Assertions.assertThat(media.getFileName()).isEqualTo("sample.mov");
		    Assertions.assertThat(media.getAdAccountId()).isEqualTo(adAccountID);
		    Assertions.assertThat(media.getType()).isEqualTo(MediaTypeEnum.VIDEO);
		    Assertions.assertThat(media.getMediaStatus()).isEqualTo(MediaStatusTypeEnum.READY);
		    Assertions.assertThat(sdf.format(media.getCreatedAt())).isEqualTo("2016-08-14T06:23:37.086Z");
		    Assertions.assertThat(sdf.format(media.getUpdatedAt())).isEqualTo("2016-08-14T06:24:28.378Z");
		});
    }// test_get_specific_media_should_success()

    @Test
    public void test_get_specific_media_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapMedia.getSpecificMedia("", mediaID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_specific_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_specific_media_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(null, mediaID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_specific_media_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_specific_media_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media ID is missing");
    }// test_get_specific_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_specific_media_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media ID is missing");
    }// test_get_specific_media_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_specific_media_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapMedia.getSpecificMedia(oAuthAccessToken, mediaID);
    }// test_specific_media_should_throw_IOException()
    
    @Test
    public void should_throw_exception_400_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_specific_media()

    @Test
    public void should_throw_exception_401_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_specific_media()

    @Test
    public void should_throw_exception_403_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_specific_media()

    @Test
    public void should_throw_exception_404_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_specific_media()

    @Test
    public void should_throw_exception_405_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_specific_media()

    @Test
    public void should_throw_exception_406_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_specific_media()

    @Test
    public void should_throw_exception_410_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_specific_media()

    @Test
    public void should_throw_exception_418_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_specific_media()

    @Test
    public void should_throw_exception_429_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_specific_media()

    @Test
    public void should_throw_exception_500_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_specific_media()

    @Test
    public void should_throw_exception_503_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_specific_media()

    @Test
    public void should_throw_exception_1337_specific_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_specific_media()
    
    @Test
    public void test_get_preview_media_should_success() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
	.thenReturn(SnapResponseUtils.getSnapPreviewMedia());
	Assertions.assertThatCode(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID)).doesNotThrowAnyException();
	Assertions.assertThat(snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID)).isNotNull();
	Assertions.assertThat(snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID)).isNotEmpty();
	Assertions.assertThat(snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID).containsKey("link")).isTrue();
	Assertions.assertThat(snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID).containsKey("expiresAt")).isTrue();
	Assertions.assertThat(snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID).get("link")).isEqualTo("https://adsapisc.appspot.com/media/video_preview?media_id=8e781365-ce4c-4336-8c53-f6a1f7c50af1&expires_at=1521063563303&signature=MGQCMA1DRI6uBax3GSq93E8hp5b3P2Ebg0lIlPa8iGML9rs1B9WFmPeHH6ttvx_rmDG5AgIwY0pjIvEEpwOXM8o3h9Hst60DjRN9Mw7am7OmkdrBGfoI4IiHBflv0XpK87Tnb_BE");
    }// test_get_preview_media_should_success()

    @Test
    public void test_get_preview_media_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia("", mediaID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_preview_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_preview_media_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(null, mediaID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_preview_media_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_preview_media_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media ID is missing");
    }// test_get_preview_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_preview_media_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media ID is missing");
    }// test_get_preview_media_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_preview_media_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID);
    }// test_preview_media_should_throw_IOException()
    
    @Test
    public void should_throw_exception_400_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_preview_media()

    @Test
    public void should_throw_exception_401_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_preview_media()

    @Test
    public void should_throw_exception_403_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_preview_media()

    @Test
    public void should_throw_exception_404_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_preview_media()

    @Test
    public void should_throw_exception_405_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_preview_media()

    @Test
    public void should_throw_exception_406_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_preview_media()

    @Test
    public void should_throw_exception_410_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_preview_media()

    @Test
    public void should_throw_exception_418_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_preview_media()

    @Test
    public void should_throw_exception_429_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_preview_media()

    @Test
    public void should_throw_exception_500_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_preview_media()

    @Test
    public void should_throw_exception_503_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_preview_media()

    @Test
    public void should_throw_exception_1337_preview_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getPreviewOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_preview_media()
    
    @Test
    public void test_get_thumbnail_media_should_success() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
	.thenReturn(SnapResponseUtils.getSnapThumbnailMedia());
	Assertions.assertThatCode(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID)).doesNotThrowAnyException();
	Assertions.assertThat(snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID)).isNotNull();
	Assertions.assertThat(snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID)).isNotEmpty();
	Assertions.assertThat(snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID).containsKey("link")).isTrue();
	Assertions.assertThat(snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID).containsKey("expiresAt")).isTrue();
	Assertions.assertThat(snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID).get("link")).isEqualTo("https://adsapisc.appspot.com/media/video_thumbnail?media_id=095a4a6d-01b0-4f6c-8901-41ee38c7b540&expires_at=1536870570555&signature=MGQCMBQ_NfJM0yZCrdyLiEon4Lkbei0zFJF2HpLiHa2NvSLV2JyOVhLqHfQgqbDWUuzaCQIwHzPj_ZFtPNk688SoFiKWUIFEKKBMhSm8t4moy9xlfgnoSv-8LMQ1omM_P8QCj7O9");
    }// test_get_thumbnail_media_should_success()

    @Test
    public void test_get_thumbnail_media_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia("", mediaID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_thumbnail_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_thumbnail_media_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(null, mediaID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_thumbnail_media_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_thumbnail_media_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media ID is missing");
    }// test_get_thumbnail_media_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_thumbnail_media_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The media ID is missing");
    }// test_get_thumbnail_media_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_thumbnail_media_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID);
    }// test_thumbnail_media_should_throw_IOException()
    
    @Test
    public void should_throw_exception_400_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_thumbnail_media()

    @Test
    public void should_throw_exception_401_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_thumbnail_media()

    @Test
    public void should_throw_exception_403_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_thumbnail_media()

    @Test
    public void should_throw_exception_404_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_thumbnail_media()

    @Test
    public void should_throw_exception_405_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_thumbnail_media()

    @Test
    public void should_throw_exception_406_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_thumbnail_media()

    @Test
    public void should_throw_exception_410_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_thumbnail_media()

    @Test
    public void should_throw_exception_418_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_thumbnail_media()

    @Test
    public void should_throw_exception_429_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_thumbnail_media()

    @Test
    public void should_throw_exception_500_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_thumbnail_media()

    @Test
    public void should_throw_exception_503_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_thumbnail_media()

    @Test
    public void should_throw_exception_1337_thumbnail_media() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapMedia.getThumbnailOfSpecificMedia(oAuthAccessToken, mediaID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_thumbnail_media()
    
    /**
     * Initialize a creative media
     * 
     * @param name Name
     * @param type Type
     * @return Creative Media Instance
     */
    private CreativeMedia initializeCreativeMedia(String name, MediaTypeEnum type) {
	CreativeMedia media = new CreativeMedia();
	media.setAdAccountId(adAccountID);
	media.setName(name);
	media.setType(type);
	return media;
    }// initializeCreativeMedia()
}// SnapMediaTest
