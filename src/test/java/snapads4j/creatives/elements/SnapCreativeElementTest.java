package snapads4j.creatives.elements;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import snapads4j.enums.CreativeTypeEnum;
import snapads4j.enums.InteractionTypeEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.creatives.WebViewPropertiesBuilder;
import snapads4j.model.creatives.elements.ButtonProperties;
import snapads4j.model.creatives.elements.CreativeElement;
import snapads4j.model.creatives.elements.InteractionZone;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SnapCreativeElementTest {

    @Spy
    private SnapCreativeElement snapCreative;

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

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    private InteractionZone interactionZone;

    private CreativeElement creative;

    private List<CreativeElement> creatives;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        snapCreative.setHttpClient(httpClient);
        snapCreative.setEntityUtilsWrapper(entityUtilsWrapper);
        interactionZone = initInteractionZone();
        creative = initCreativeElement();
        creatives = initCreativeElements();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }// setUp()

    @Test
    public void test_create_creative_should_success() throws IOException,
            SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapCreationCreativeElement());
        Assertions.assertThatCode(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .doesNotThrowAnyException();
        Optional<CreativeElement> optCreative = snapCreative.createCreativeElement(oAuthAccessToken, creative);
        Assertions.assertThat(optCreative.isPresent()).isEqualTo(true);
        optCreative.ifPresent(c -> {
            Assertions.assertThat(c.toString()).isNotEmpty();
            Assertions.assertThat(c.getId()).isEqualTo("f63bb5f5-471c-404f-8f0d-e5c1a003e4d9");
            Assertions.assertThat(c.getAdAccountId()).isEqualTo(creative.getAdAccountId());
            Assertions.assertThat(c.getName()).isEqualTo(creative.getName());
            Assertions.assertThat(c.getType()).isEqualTo(creative.getType());
            Assertions.assertThat(c.getTitle()).isEqualTo(creative.getTitle());
            Assertions.assertThat(c.getInteractionType()).isEqualTo(creative.getInteractionType());
            Assertions.assertThat(c.getDescription()).isEqualTo(creative.getDescription());
            Assertions.assertThat(c.getButtonProperties()).isNotNull();
            Assertions.assertThat(c.getButtonProperties().toString()).isNotEmpty();
            Assertions.assertThat(c.getButtonProperties().getButtonOverlayMediaId())
                    .isEqualTo(creative.getButtonProperties().getButtonOverlayMediaId());
            Assertions.assertThat(c.getWebViewProperties()).isNotNull();
            Assertions.assertThat(c.getWebViewProperties().toString()).isNotEmpty();
            Assertions.assertThat(c.getWebViewProperties().getUrl())
                    .isEqualTo(creative.getWebViewProperties().getUrl());
            Assertions.assertThat(sdf.format(c.getCreatedAt())).isEqualTo("2018-11-16T03:01:52.907Z");
            Assertions.assertThat(sdf.format(c.getUpdatedAt())).isEqualTo("2018-11-16T03:01:52.907Z");
        });
    }// test_create_creative_should_success()

    @Test
    public void test_create_creative_should_throw_SnapOAuthAccessTokenException_1() {
        assertThatThrownBy(() -> snapCreative.createCreativeElement("", creative))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_create_creative_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_creative_should_throw_SnapOAuthAccessTokenException_2() {
        assertThatThrownBy(() -> snapCreative.createCreativeElement(null, creative))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_create_creative_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_creative_should_throw_SnapArgumentException_1() {
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Creative parameter is not given");
    }// test_create_creative_should_throw_SnapArgumentException_1()

    @Test
    public void test_create_creative_should_throw_SnapArgumentException_2() {
        CreativeElement badCreative = new CreativeElement();
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, badCreative))
                .isInstanceOf(SnapArgumentException.class).hasMessage(
                "The Ad Account ID is required,The name is required,The creative type is required,The interaction type is required");
    }// test_create_creative_should_throw_SnapArgumentException_2()

    @Test
    public void test_create_creative_should_throw_SnapArgumentException_3() {
        creative.setWebViewProperties(null);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Web View Properties is required");
    }// test_create_creative_should_throw_SnapArgumentException_3()

    @Test
    public void test_create_creative_should_throw_SnapArgumentException_4() {
        creative.setDeepLinkProperties(null);
        creative.setInteractionType(InteractionTypeEnum.DEEP_LINK);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Deep Link Properties is required");
    }// test_create_creative_should_throw_SnapArgumentException_4()

    @Test
    public void test_create_creative_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapExecutionException.class);
    }// test_create_creative_should_throw_SnapExecutionException()

    @Test
    public void should_throw_exception_400_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_create_creative()

    @Test
    public void should_throw_exception_401_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_creative()

    @Test
    public void should_throw_exception_403_create_creative() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_creative()

    @Test
    public void should_throw_exception_404_create_creative() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_creative()

    @Test
    public void should_throw_exception_405_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_creative()

    @Test
    public void should_throw_exception_406_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_creative()

    @Test
    public void should_throw_exception_410_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_creative()

    @Test
    public void should_throw_exception_418_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_creative()

    @Test
    public void should_throw_exception_429_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_creative()

    @Test
    public void should_throw_exception_500_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_creative()

    @Test
    public void should_throw_exception_503_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_creative()

    @Test
    public void should_throw_exception_1337_create_creative() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElement(oAuthAccessToken, creative))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_creative()

    @Test
    public void test_create_creatives_should_success() throws IOException,
            SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapCreationCreativeElements());
        Assertions.assertThatCode(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .doesNotThrowAnyException();
        List<CreativeElement> lCreatives = snapCreative.createCreativeElements(oAuthAccessToken, creatives);
        Assertions.assertThat(lCreatives).isNotNull();
        Assertions.assertThat(lCreatives).isNotEmpty();
        Assertions.assertThat(lCreatives).size().isEqualTo(3);
        for (int i = 0; i < lCreatives.size(); ++i) {
            Assertions.assertThat(lCreatives.get(i).getAdAccountId()).isEqualTo(creatives.get(i).getAdAccountId());
            Assertions.assertThat(lCreatives.get(i).getDescription()).isEqualTo(creatives.get(i).getDescription());
            Assertions.assertThat(lCreatives.get(i).getName()).isEqualTo(creatives.get(i).getName());
            Assertions.assertThat(lCreatives.get(i).getTitle()).isEqualTo(creatives.get(i).getTitle());
            Assertions.assertThat(lCreatives.get(i).getType()).isEqualTo(creatives.get(i).getType());
            Assertions.assertThat(lCreatives.get(i).getButtonProperties().getButtonOverlayMediaId())
                    .isEqualTo(creatives.get(i).getButtonProperties().getButtonOverlayMediaId());
            Assertions.assertThat(lCreatives.get(i).getWebViewProperties().getUrl())
                    .isEqualTo(creatives.get(i).getWebViewProperties().getUrl());
            Assertions.assertThat(lCreatives.get(i).getWebViewProperties().isAllowSnapJavascriptSdk())
                    .isEqualTo(creatives.get(i).getWebViewProperties().isAllowSnapJavascriptSdk());
            Assertions.assertThat(lCreatives.get(i).getWebViewProperties().isBlockPreload())
                    .isEqualTo(creatives.get(i).getWebViewProperties().isBlockPreload());
            Assertions.assertThat(lCreatives.get(i).getWebViewProperties().isUseImmersiveMode()).isEqualTo(false);
            Assertions.assertThat(lCreatives.get(i).getWebViewProperties().getDeepLinkUrls()).isEmpty();
        }
        Assertions.assertThat(sdf.format(lCreatives.get(0).getCreatedAt())).isEqualTo("2018-11-16T03:05:23.241Z");
        Assertions.assertThat(sdf.format(lCreatives.get(0).getUpdatedAt())).isEqualTo("2018-11-16T03:05:23.241Z");
        Assertions.assertThat(sdf.format(lCreatives.get(1).getCreatedAt())).isEqualTo("2018-11-16T03:05:23.241Z");
        Assertions.assertThat(sdf.format(lCreatives.get(1).getUpdatedAt())).isEqualTo("2018-11-16T03:05:23.241Z");
        Assertions.assertThat(sdf.format(lCreatives.get(2).getCreatedAt())).isEqualTo("2018-11-16T03:05:23.242Z");
        Assertions.assertThat(sdf.format(lCreatives.get(2).getUpdatedAt())).isEqualTo("2018-11-16T03:05:23.242Z");
    }// test_create_creatives_should_success()

    @Test
    public void test_create_creatives_should_throw_SnapOAuthAccessTokenException_1() {
        assertThatThrownBy(() -> snapCreative.createCreativeElements("", initBadCreativeElements()))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_create_creatives_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_creatives_should_throw_SnapOAuthAccessTokenException_2() {
        assertThatThrownBy(() -> snapCreative.createCreativeElements(null, initBadCreativeElements()))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_create_creatives_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_creatives_should_throw_SnapArgumentException_1() {
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Creative elements parameter is not given");
    }// test_create_creatives_should_throw_SnapArgumentException_1()

    @Test
    public void test_create_creatives_should_throw_SnapArgumentException_2() {
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, initBadCreativeElements()))
                .isInstanceOf(SnapArgumentException.class).hasMessage(
                "CreativeElement index n°0 : The Ad Account ID is required,CreativeElement index n°0 : The name is required,CreativeElement index n°0 : The creative type is required,CreativeElement index n°0 : The interaction type is required");
    }// test_create_creatives_should_throw_SnapArgumentException_2()

    @Test
    public void test_create_creatives_should_throw_SnapArgumentException_3() {
        List<CreativeElement> bads = initBadCreativeElements();
        bads.get(0).setInteractionType(InteractionTypeEnum.WEB_VIEW);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, bads))
                .isInstanceOf(SnapArgumentException.class).hasMessage(
                "CreativeElement index n°0 : The Ad Account ID is required,CreativeElement index n°0 : The name is required,CreativeElement index n°0 : The creative type is required,CreativeElement index n°0 : Web View Properties is required");
    }// test_create_creatives_should_throw_SnapArgumentException_3()

    @Test
    public void test_create_creatives_should_throw_SnapArgumentException_4() {
        List<CreativeElement> bads = initBadCreativeElements();
        bads.get(0).setInteractionType(InteractionTypeEnum.DEEP_LINK);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, bads))
                .isInstanceOf(SnapArgumentException.class).hasMessage(
                "CreativeElement index n°0 : The Ad Account ID is required,CreativeElement index n°0 : The name is required,CreativeElement index n°0 : The creative type is required,CreativeElement index n°0 : Deep Link Properties is required");
    }// test_create_creatives_should_throw_SnapArgumentException_4()

    @Test
    public void test_create_creatives_should_throw_SnapArgumentException_5() {
        List<CreativeElement> bads = initBadCreativeElements();
        bads.get(0).setType(CreativeTypeEnum.BUTTON);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, bads))
                .isInstanceOf(SnapArgumentException.class).hasMessage(
                "CreativeElement index n°0 : The Ad Account ID is required,CreativeElement index n°0 : The name is required,CreativeElement index n°0 : The interaction type is required,CreativeElement index n°0 : Button Properties is required");
    }// test_create_creatives_should_throw_SnapArgumentException_5()

    @Test
    public void test_create_creatives_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapExecutionException.class);
    }// test_create_creatives_should_throw_SnapExecutionException()

    @Test
    public void should_throw_exception_400_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_create_creatives()

    @Test
    public void should_throw_exception_401_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_creatives()

    @Test
    public void should_throw_exception_403_create_creatives() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_creatives()

    @Test
    public void should_throw_exception_404_create_creatives() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_creatives()

    @Test
    public void should_throw_exception_405_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_creatives()

    @Test
    public void should_throw_exception_406_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_creatives()

    @Test
    public void should_throw_exception_410_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_creatives()

    @Test
    public void should_throw_exception_418_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_creatives()

    @Test
    public void should_throw_exception_429_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_creatives()

    @Test
    public void should_throw_exception_500_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_creatives()

    @Test
    public void should_throw_exception_503_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_creatives()

    @Test
    public void should_throw_exception_1337_create_creatives() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createCreativeElements(oAuthAccessToken, creatives))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_creatives()

    @Test
    public void test_create_interaction_zone_should_success() throws IOException,
            SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapCreationInteractionZone());
        Assertions.assertThatCode(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .doesNotThrowAnyException();
        Optional<InteractionZone> optInteraction = snapCreative.createInteractionZone(oAuthAccessToken,
                interactionZone);
        Assertions.assertThat(optInteraction.isPresent()).isEqualTo(true);
        optInteraction.ifPresent(zone -> {
            Assertions.assertThat(zone.toString()).isNotEmpty();
            Assertions.assertThat(zone.getId()).isEqualTo("a218dc8b-7a79-4da6-9a1c-e5a581c7bd46");
            Assertions.assertThat(zone.getAdAccountId()).isEqualTo(interactionZone.getAdAccountId());
            Assertions.assertThat(zone.getName()).isEqualTo(interactionZone.getName());
            Assertions.assertThat(zone.getHeadline()).isEqualTo(interactionZone.getHeadline());
            Assertions.assertThat(sdf.format(zone.getCreatedAt())).isEqualTo("2018-11-16T03:26:23.130Z");
            Assertions.assertThat(sdf.format(zone.getUpdatedAt())).isEqualTo("2018-11-16T03:26:23.130Z");
            Assertions.assertThat(zone.getCreativeElements()).isNotEmpty();
            Assertions.assertThat(zone.getCreativeElements()).isNotNull();
            Assertions.assertThat(zone.getCreativeElements()).size()
                    .isEqualTo(interactionZone.getCreativeElements().size());
            for (int i = 0; i < zone.getCreativeElements().size(); ++i) {
                Assertions.assertThat(zone.getCreativeElements().get(i))
                        .isEqualTo(interactionZone.getCreativeElements().get(i));
            }
        });
    }// test_create_interaction_zone_should_success()

    @Test
    public void test_create_interaction_zone_should_throw_SnapOAuthAccessTokenException_1() {
        assertThatThrownBy(() -> snapCreative.createInteractionZone("", interactionZone))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_create_interaction_zone_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_interaction_zone_should_throw_SnapOAuthAccessTokenException_2() {
        assertThatThrownBy(() -> snapCreative.createInteractionZone(null, interactionZone))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_create_interaction_zone_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_interaction_zone_should_throw_SnapArgumentException_1() {
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Interaction Zone parameter is not given");
    }// test_create_interaction_zone_should_throw_SnapArgumentException_1()

    @Test
    public void test_create_interaction_zone_should_throw_SnapArgumentException_2() {
        InteractionZone badZone = new InteractionZone();
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, badZone))
                .isInstanceOf(SnapArgumentException.class).hasMessage(
                "The interaction zone's ad account id is required,The interaction zone's headline is required,The interaction zone's name is required,The interaction zone's creative elements is required");
    }// test_create_interaction_zone_should_throw_SnapArgumentException_2()

    @Test
    public void test_create_interaction_zone_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapExecutionException.class);
    }// test_create_interaction_zone_should_throw_SnapExecutionException()

    @Test
    public void should_throw_exception_400_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_create_interaction_zone()

    @Test
    public void should_throw_exception_401_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_interaction_zone()

    @Test
    public void should_throw_exception_403_create_interaction_zone() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_interaction_zone()

    @Test
    public void should_throw_exception_404_create_interaction_zone() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_interaction_zone()

    @Test
    public void should_throw_exception_405_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_interaction_zone()

    @Test
    public void should_throw_exception_406_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_interaction_zone()

    @Test
    public void should_throw_exception_410_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_interaction_zone()

    @Test
    public void should_throw_exception_418_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_interaction_zone()

    @Test
    public void should_throw_exception_429_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_interaction_zone()

    @Test
    public void should_throw_exception_500_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_interaction_zone()

    @Test
    public void should_throw_exception_503_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_interaction_zone()

    @Test
    public void should_throw_exception_1337_create_interaction_zone() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_interaction_zone()

    private CreativeElement initCreativeElement() {
        ButtonProperties buttonProperties = new ButtonProperties();
        buttonProperties.setButtonOverlayMediaId("008a5ae9-bcc1-4c2e-a3f1-7e924d582019");
        WebViewPropertiesBuilder builder = new WebViewPropertiesBuilder();
        builder.setUrl("https://snapchat.com");
        CreativeElement c = new CreativeElement();
        c.setName("Product 1 button");
        c.setAdAccountId(adAccountID);
        c.setType(CreativeTypeEnum.BUTTON);
        c.setDescription("Product 1");
        c.setTitle("Best title");
        c.setInteractionType(InteractionTypeEnum.WEB_VIEW);
        c.setButtonProperties(buttonProperties);
        c.setWebViewProperties(builder.build());
        return c;
    }// initCreativeElement()

    private List<CreativeElement> initCreativeElements() {
        ButtonProperties buttonProperties = new ButtonProperties();
        buttonProperties.setButtonOverlayMediaId("008a5ae9-bcc1-4c2e-a3f1-7e924d582019");
        WebViewPropertiesBuilder builder = new WebViewPropertiesBuilder();
        builder.setUrl("https://snapchat.com");
        List<CreativeElement> results = new ArrayList<>();
        CreativeElement c1 = new CreativeElement();
        c1.setName("Product 1 button");
        c1.setType(CreativeTypeEnum.BUTTON);
        c1.setDescription("Product 1");
        c1.setInteractionType(InteractionTypeEnum.WEB_VIEW);
        c1.setTitle("Best title");
        c1.setButtonProperties(buttonProperties);
        c1.setWebViewProperties(builder.build());
        c1.setAdAccountId(adAccountID);
        results.add(c1);
        ButtonProperties buttonProperties2 = new ButtonProperties();
        buttonProperties2.setButtonOverlayMediaId("008a5ae9-bcc1-4c2e-a3f1-7e924d582012");
        builder = new WebViewPropertiesBuilder();
        builder.setUrl("https://snapchat2.com");
        CreativeElement c2 = new CreativeElement();
        c2.setName("Product 2 button");
        c2.setType(CreativeTypeEnum.BUTTON);
        c2.setDescription("Product 2");
        c2.setInteractionType(InteractionTypeEnum.WEB_VIEW);
        c2.setTitle("Best title");
        c2.setButtonProperties(buttonProperties2);
        c2.setWebViewProperties(builder.build());
        c2.setAdAccountId(adAccountID);
        results.add(c2);
        ButtonProperties buttonProperties3 = new ButtonProperties();
        buttonProperties3.setButtonOverlayMediaId("008a5ae9-bcc1-4c2e-a3f1-7e924d582013");
        builder = new WebViewPropertiesBuilder();
        builder.setUrl("https://snapchat3.com");
        CreativeElement c3 = new CreativeElement();
        c3.setName("Product 3 button");
        c3.setType(CreativeTypeEnum.BUTTON);
        c3.setDescription("Product 3");
        c3.setInteractionType(InteractionTypeEnum.WEB_VIEW);
        c3.setTitle("Best title");
        c3.setButtonProperties(buttonProperties3);
        c3.setWebViewProperties(builder.build());
        c3.setAdAccountId(adAccountID);
        results.add(c3);
        return results;
    }// initCreativeElements()

    private List<CreativeElement> initBadCreativeElements() {
        List<CreativeElement> results = new ArrayList<>();
        CreativeElement c1 = new CreativeElement();
        results.add(c1);
        return results;
    }// initBadCreativeElements()

    private InteractionZone initInteractionZone() {
        InteractionZone zone = new InteractionZone();
        zone.setAdAccountId(adAccountID);
        zone.setHeadline("MORE");
        zone.setName("First Interaction Zone");
        List<String> ids = Stream
                .of(new String[]{"70debf44-cb4b-4b5f-8828-bd2b68b9f0cf", "a2d1c8a0-0466-4924-b769-7a7e6ed5be3b",
                        "4091233e-3351-405d-8684-a97e70c3b5dc", "f63bb5f5-471c-404f-8f0d-e5c1a003e4d9"})
                .collect(Collectors.toList());
        zone.setCreativeElements(ids);
        return zone;
    }// initInteractionZone()

}// SnapCreativeElementTest
