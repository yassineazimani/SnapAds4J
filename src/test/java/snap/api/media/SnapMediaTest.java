package snap.api.media;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
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
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.model.media.CreativeMedia;
import snap.api.utils.EntityUtilsWrapper;

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
    
    private CreativeMedia media;
    
    @Before
    public void setUp() {
      MockitoAnnotations.initMocks(this);
      snapMedia.setHttpClient(httpClient);
      snapMedia.setEntityUtilsWrapper(entityUtilsWrapper);
      media = initializeCreativeMedia("Media A - Video", MediaTypeEnum.VIDEO);
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
