package snap.api.media;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
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

import snap.api.enums.MediaTypeEnum;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.media.CreativeMedia;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.FileUtils;

/**
 * Unit tests mocked for SnapMediaTest
 *
 * @author Yassine
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapMediaTest {
    
    @Spy private SnapMedia snapMedia;

    @Mock private CloseableHttpClient httpClient;

    @Mock private CloseableHttpResponse httpResponse;
    
    @Mock
    private StatusLine statusLine;
    
    @Mock
    private EntityUtilsWrapper entityUtilsWrapper;

    @Mock
    private HttpEntity httpEntity;

    private final String oAuthAccessToken = "meowmeowmeow";
    
    private final String adAccountID = "8adc3db7-8148-4fbf-999c-8d2266369d74";
    
    private final String mediaID = "a7bee653-1865-41cf-8cee-8ab85a205837";
    
    private CreativeMedia media;
    
    private CreativeMedia mediaFail;
    
    @Before
    public void setUp() {
      MockitoAnnotations.initMocks(this);
      snapMedia.setHttpClient(httpClient);
      snapMedia.setEntityUtilsWrapper(entityUtilsWrapper);
      media = initializeCreativeMedia("Media A - Video", MediaTypeEnum.VIDEO);
      mediaFail = initializeCreativeMedia("Media A - Video", MediaTypeEnum.VIDEO);
    } // setUp()
    
    @Test
    public void test_create_media_should_success() throws IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Assertions.assertThatCode(() -> snapMedia.createMedia(oAuthAccessToken, media)).doesNotThrowAnyException();
    }// test_create_media_should_success()

    @Test
    public void test_create_media_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapMedia.createMedia(null, media)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_media_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapMedia.createMedia("", media)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_media_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, null)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Media parameter is missing");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_1()
    
    @Test
    public void test_create_media_should_throw_SnapArgumentException_2() {
	mediaFail.setName(null);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("The media's name is required");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_create_media_should_throw_SnapArgumentException_3() {
	mediaFail.setName("");
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("The media's name is required");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_3()
    
    @Test
    public void test_create_media_should_throw_SnapArgumentException_4() {
	mediaFail.setType(null);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("The media's type is required");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_4()
    
    @Test
    public void test_create_media_should_throw_SnapArgumentException_5() {
	mediaFail.setAdAccountId(null);
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("The Ad Account ID is required");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_5()
    
    @Test
    public void test_create_media_should_throw_SnapArgumentException_6() {
	mediaFail.setAdAccountId("");
	assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, mediaFail)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("The Ad Account ID is required");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_6()
    
    @Test
    public void test_create_media_should_throw_IOException() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	snapMedia.createMedia(oAuthAccessToken, media);
    }// test_create_media_should_throw_IOException()
    
    @Test
    public void should_throw_exception_401_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_media()

    @Test
    public void should_throw_exception_403_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_media()

    @Test
    public void should_throw_exception_404_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Not Found");
    } // should_throw_exception_404_create_media()

    @Test
    public void should_throw_exception_405_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_media()

    @Test
    public void should_throw_exception_406_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_media()

    @Test
    public void should_throw_exception_410_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Gone");
    } // should_throw_exception_410_create_media()

    @Test
    public void should_throw_exception_418_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_media()

    @Test
    public void should_throw_exception_429_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_media()

    @Test
    public void should_throw_exception_500_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_media()

    @Test
    public void should_throw_exception_503_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_media()

    @Test
    public void should_throw_exception_1337_create_media()
        throws IOException, InterruptedException, SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
  	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
  	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
      assertThatThrownBy(() -> snapMedia.createMedia(oAuthAccessToken, media))
          .isInstanceOf(SnapResponseErrorException.class)
          .hasMessage("Error 1337");
    } // should_throw_exception_1337_create_media()
    
    @Test
    public void test_upload_media_video_should_success() throws SnapArgumentException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4", "vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	    Assertions.assertThatCode(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, mediaID, mediaFile)).doesNotThrowAnyException();
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    }// test_upload_media_video_should_success()
    
    @Test
    public void test_upload_media_video_should_throw_SnapOAuthAccessTokenException_1() {
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4", "vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	assertThatThrownBy(() -> snapMedia.uploadMediaVideo(null, mediaID, mediaFile))
		.isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    }// test_upload_media_video_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_upload_media_video_should_throw_SnapOAuthAccessTokenException_2() {
	Optional<File> optFile = new FileUtils().getFileFromResources("videos/vidsplay-rain-falling-on-window-1-1.mp4", "vidsplay-rain-falling-on-window-1-1.mp4");
	optFile.ifPresent((mediaFile) -> {
	assertThatThrownBy(() -> snapMedia.uploadMediaVideo("", mediaID, mediaFile))
		.isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
	});
	FileUtils.deleteFile("vidsplay-rain-falling-on-window-1-1.mp4");
    }// test_upload_media_video_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_upload_media_video_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, mediaID, null)).isInstanceOf(SnapArgumentException.class)
	.hasMessage("Media parameter is missing");
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
	assertThatThrownBy(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, "", null)).isInstanceOf(SnapArgumentException.class)
	.hasMessage("Media ID is missing");
    }// test_upload_media_video_should_throw_SnapArgumentException_3()
    
    @Test
    public void test_upload_media_video_should_throw_SnapArgumentException_4() {
	assertThatThrownBy(() -> snapMedia.uploadMediaVideo(oAuthAccessToken, null, null)).isInstanceOf(SnapArgumentException.class)
	.hasMessage("Media ID is missing");
    }// test_upload_media_video_should_throw_SnapArgumentException_4()
    
    /**
     * Initialize a creative media
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
