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
package snapads4j.fundingsource;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import snapads4j.enums.CreditCardTypeEnum;
import snapads4j.enums.CurrencyEnum;
import snapads4j.enums.FundingSourceTypeEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.fundingsource.FundingSource;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests mocked for SnapFundingSourceTest
 *
 * @author Yassine
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapFundingSourceTest {

    @Spy
    private SnapFundingSource fundingSource;

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

    private final String id = "e703eb9f-8eac-4eda-a9c7-deec3935222d";

    private final String organizationId = "40d6719b-da09-410b-9185-0cc9c0dfed1d";

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fundingSource.setHttpClient(httpClient);
        fundingSource.setEntityUtilsWrapper(entityUtilsWrapper);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    } // setUp()

    @Test
    public void test_getAllFundingSource_should_success()
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            IOException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAllFundingSources());
        List<FundingSource> fundingSources =
                fundingSource.getAllFundingSource(oAuthAccessToken, organizationId);
        assertThat(fundingSources).isNotNull();
        assertThat(fundingSources).isNotEmpty();
        assertThat(fundingSources).hasSize(3);

        assertThat(fundingSources.get(0).getId()).isEqualTo("1e224e75-3883-42cf-a5d9-ce505945d2d3");
        assertThat(fundingSources.get(0).getType()).isEqualTo(FundingSourceTypeEnum.CREDIT_CARD);
        assertThat(fundingSources.get(0).getCardType()).isEqualTo(CreditCardTypeEnum.DISCOVER);
        assertThat(fundingSources.get(0).getNameCreditCard()).isEqualTo("My DISCOVER card");
        assertThat(fundingSources.get(0).getLast4()).isEqualTo(1100);
        assertThat(fundingSources.get(0).getExpirationMonth()).isEqualTo(12);
        assertThat(fundingSources.get(0).getExpirationYear()).isEqualTo(2020);
        assertThat(fundingSources.get(0).getDailySpendLimitMicro()).isEqualTo(25000000);
        assertThat(fundingSources.get(0).getDailySpendCurrency()).isEqualTo(CurrencyEnum.USD);
        assertThat(sdf.format(fundingSources.get(0).getCreatedAt())).isEqualTo("2017-05-22T22:46:30.917Z");
        assertThat(sdf.format(fundingSources.get(0).getUpdatedAt())).isEqualTo("2017-05-22T22:46:30.917Z");

        assertThat(fundingSources.get(1).getId()).isEqualTo("9d111fbf-da5f-4526-9e7b-226f847b3d7e");
        assertThat(fundingSources.get(1).getType()).isEqualTo(FundingSourceTypeEnum.LINE_OF_CREDIT);
        assertThat(fundingSources.get(1).getAvailableCreditMicro()).isEqualTo(2000000000);
        assertThat(fundingSources.get(1).getCurrency()).isEqualTo(CurrencyEnum.USD);
        assertThat(fundingSources.get(1).getTotalBudgetMicro()).isEqualTo(10000000000.);
        assertThat(fundingSources.get(1).getStatus()).isEqualTo(StatusEnum.ACTIVE);
        assertThat(fundingSources.get(1).getCreditAccountType()).isEqualTo("MANAGED");
        assertThat(fundingSources.get(1).getBudgetSpentMicro()).isEqualTo(8000000000.);
        assertThat(sdf.format(fundingSources.get(1).getCreatedAt())).isEqualTo("2017-05-22T22:46:30.920Z");
        assertThat(sdf.format(fundingSources.get(1).getUpdatedAt())).isEqualTo("2017-05-22T22:46:30.920Z");

        assertThat(fundingSources.get(2).getId()).isEqualTo("d24b4011-3560-47ea-86fa-0ed14c6b90d4");
        assertThat(fundingSources.get(2).getType()).isEqualTo(FundingSourceTypeEnum.COUPON);
        assertThat(fundingSources.get(2).getAvailableCreditMicro()).isEqualTo(10000000000.);
        assertThat(fundingSources.get(2).getCurrency()).isEqualTo(CurrencyEnum.EUR);
        assertThat(fundingSources.get(2).getValueMicro()).isEqualTo(10000000000.);
        assertThat(fundingSources.get(2).getStatus()).isEqualTo(StatusEnum.REDEEMED);
        assertThat(sdf.format(fundingSources.get(2).getCreatedAt())).isEqualTo("2017-05-22T22:46:30.920Z");
        assertThat(sdf.format(fundingSources.get(2).getUpdatedAt())).isEqualTo("2017-05-22T22:46:30.920Z");
    } // test_getAllFundingSource_should_success()

    @Test
    public void test_getAllFundingSource_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(null, organizationId))
                .isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_getAllFundingSource_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_getAllFundingSource_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> fundingSource.getAllFundingSource("", organizationId))
                .isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_getAllFundingSource_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_getAllFundingSource_should_throw_SnapArgumentException_when_organization_id_is_null() {
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The organization ID is required");
    } // test_getAllFundingSource_should_throw_SnapArgumentException_when_organization_id_is_null()

    @Test
    public void test_getAllFundingSource_should_throw_SnapArgumentException_when_organization_id_is_empty() {
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, ""))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The organization ID is required");
    } // test_getAllFundingSource_should_throw_SnapArgumentException_when_organization_id_is_empty()

    @Test
    public void should_throw_exception_400_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Bad Request");
    } // should_throw_exception_400_getAllFundingSource()

    @Test
    public void should_throw_exception_401_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getAllFundingSource()

    @Test
    public void should_throw_exception_403_getAllFundingSource()
            throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Access Forbidden");
    } // should_throw_exception_403_getAllFundingSource()

    @Test
    public void should_throw_exception_404_getAllFundingSource()
            throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Found");
    } // should_throw_exception_404_getAllFundingSource()

    @Test
    public void should_throw_exception_405_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getAllFundingSource()

    @Test
    public void should_throw_exception_406_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Acceptable");
    } // should_throw_exception_406_getAllFundingSource()

    @Test
    public void should_throw_exception_410_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Gone");
    } // should_throw_exception_410_getAllFundingSource()

    @Test
    public void should_throw_exception_418_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("I'm a teapot");
    } // should_throw_exception_418_getAllFundingSource()

    @Test
    public void should_throw_exception_429_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getAllFundingSource()

    @Test
    public void should_throw_exception_500_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Internal Server Error");
    } // should_throw_exception_500_getAllFundingSource()

    @Test
    public void should_throw_exception_503_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Service Unavailable");
    } // should_throw_exception_503_getAllFundingSource()

    @Test
    public void should_throw_exception_1337_getAllFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, organizationId))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Error 1337");
    } // should_throw_exception_1337_getAllFundingSource()

    @Test
    public void test_getSpecificFundingSource_should_success()
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            IOException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapSpecificFundingSource());
        Optional<FundingSource> optFundingSource =
                fundingSource.getSpecificFundingSource(oAuthAccessToken, id);
        assertThat(optFundingSource.isPresent()).isTrue();
        optFundingSource.ifPresent(
                f -> {
                    assertThat(f.getId()).isEqualTo("e703eb9f-8eac-4eda-a9c7-deec3935222d");
                    assertThat(f.getType()).isEqualTo(FundingSourceTypeEnum.LINE_OF_CREDIT);
                    assertThat(f.getNameCreditCard()).isEqualTo("Hooli Test Ad Account Funding Source");
                    assertThat(f.getOrganizationId()).isEqualTo("40d6719b-da09-410b-9185-0cc9c0dfed1d");
                    assertThat(f.getCurrency()).isEqualTo(CurrencyEnum.USD);
                    assertThat(f.toString()).isNotEmpty();
                    assertThat(sdf.format(f.getCreatedAt())).isEqualTo("2016-08-11T22:03:54.337Z");
                    assertThat(sdf.format(f.getUpdatedAt())).isEqualTo("2016-08-11T22:03:54.337Z");
                });
    } // test_getSpecificFundingSource_should_success()

    @Test
    public void test_getSpecificFundingSource_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(null, id))
                .isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_getSpecificFundingSource_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_getSpecificFundingSource_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource("", id))
                .isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_getSpecificFundingSource_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_getSpecificFundingSource_should_throw_SnapArgumentException_when_funding_source_id_is_null() {
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The Funding source ID is required");
    } // test_getSpecificFundingSource_should_throw_SnapArgumentException_when_funding_source_id_is_null()

    @Test
    public void test_getSpecificFundingSource_should_throw_SnapArgumentException_when_funding_source_id_is_empty() {
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, ""))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The Funding source ID is required");
    } // test_getSpecificFundingSource_should_throw_SnapArgumentException_when_funding_source_id_is_empty()

    @Test
    public void should_throw_exception_401_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getSpecificFundingSource()

    @Test
    public void should_throw_exception_403_getSpecificFundingSource()
            throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Access Forbidden");
    } // should_throw_exception_403_getSpecificFundingSource()

    @Test
    public void should_throw_exception_404_getSpecificFundingSource()
            throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Found");
    } // should_throw_exception_404_getSpecificFundingSource()

    @Test
    public void should_throw_exception_405_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getSpecificFundingSource()

    @Test
    public void should_throw_exception_406_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Acceptable");
    } // should_throw_exception_406_getSpecificFundingSource()

    @Test
    public void should_throw_exception_410_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Gone");
    } // should_throw_exception_410_getSpecificFundingSource()

    @Test
    public void should_throw_exception_418_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("I'm a teapot");
    } // should_throw_exception_418_getSpecificFundingSource()

    @Test
    public void should_throw_exception_429_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getSpecificFundingSource()

    @Test
    public void should_throw_exception_500_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Internal Server Error");
    } // should_throw_exception_500_getSpecificFundingSource()

    @Test
    public void should_throw_exception_503_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Service Unavailable");
    } // should_throw_exception_503_getSpecificFundingSource()

    @Test
    public void should_throw_exception_1337_getSpecificFundingSource()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Error 1337");
    } // should_throw_exception_1337_getSpecificFundingSource()

    @Test
    public void test_getAllFundingSource_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> fundingSource.getAllFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapExecutionException.class);
    }// test_getAllFundingSource_should_throw_SnapExecutionException()

    @Test
    public void test_getSpecificFundingSource_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> fundingSource.getSpecificFundingSource(oAuthAccessToken, id))
                .isInstanceOf(SnapExecutionException.class);
    }// test_getSpecificFundingSource_should_throw_SnapExecutionException()
} // SnapFundingSourceTest
