package snap.api.organization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

import snap.api.enums.CurrencyEnum;
import snap.api.enums.StatusEnum;
import snap.api.enums.TypeOrganizationEnum;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.organization.AdAccount;
import snap.api.model.organization.Organization;
import snap.api.model.organization.OrganizationWithAdAccount;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.SnapResponseUtils;

/** Unit tests mocked for SnapOrganizationTest. */
@RunWith(MockitoJUnitRunner.class)
public class SnapOrganizationTest {

    @Spy
    private SnapOrganization snapOrganization;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private StatusLine statusLine;

    @Mock
    private HttpEntity httpEntity;
    
    @Mock
    private EntityUtilsWrapper entityUtilsWrapper;

    private final String oAuthAccessToken = "meowmeowmeow";

    private final String id = "40d6719b-da09-410b-9185-0cc9c0dfed1d";

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(snapOrganization);
	snapOrganization.setHttpClient(httpClient);
	snapOrganization.setEntityUtilsWrapper(entityUtilsWrapper);
    } // setUp()

    @Test
    public void should_given_all_organizations()
	    throws IOException, InterruptedException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAllOrganizations());

	List<Organization> organizations = snapOrganization.getAllOrganizations(oAuthAccessToken);
	assertThat(organizations.isEmpty()).isFalse();
	assertThat(organizations.size()).isEqualTo(2);
	Organization org1 = organizations.get(0);
	Organization org2 = organizations.get(1);
	assertThat(org1).isNotNull();
	assertThat(org1.getId()).isEqualTo(id);
	assertThat(org1.getAddressLine1()).isEqualTo("101 Stewart St");
	assertThat(org1.getAdministrativeDistrictLevel1()).isEqualTo("WA");
	assertThat(org1.getCountry()).isEqualTo("US");
	assertThat(org1.getLocality()).isEqualTo("Seattle");
	assertThat(org1.getName()).isEqualTo("My Organization");
	assertThat(org1.getPostalCode()).isEqualTo("98134");
	assertThat(org1.getType()).isEqualTo(TypeOrganizationEnum.ENTERPRISE);
	assertThat(org2).isNotNull();
	assertThat(org2.getId()).isEqualTo("507d7a57-94de-4239-8a74-e93c00ca53e6");
	assertThat(org2.getAddressLine1()).isEqualTo("1100 Silicon Vallety Rd");
	assertThat(org2.getAdministrativeDistrictLevel1()).isEqualTo("CA");
	assertThat(org2.getCountry()).isEqualTo("US");
	assertThat(org2.getLocality()).isEqualTo("San Francisco");
	assertThat(org2.getName()).isEqualTo("Hooli");
	assertThat(org2.getPostalCode()).isEqualTo("94110");
	assertThat(org2.getType()).isEqualTo(TypeOrganizationEnum.ENTERPRISE);
    } // should_given_all_organizations()

    @Test
    public void should_throw_exception_argument_oAuth_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(null))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // should_throw_exception_argument_oAuth_all_organizations()

    @Test
    public void should_throw_exception_401_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_all_organizations()

    @Test
    public void should_throw_exception_403_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_all_organizations()

    @Test
    public void should_throw_exception_404_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_all_organizations()

    @Test
    public void should_throw_exception_405_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_all_organizations()

    @Test
    public void should_throw_exception_406_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_all_organizations()

    @Test
    public void should_throw_exception_410_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_all_organizations()

    @Test
    public void should_throw_exception_418_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_all_organizations()

    @Test
    public void should_throw_exception_429_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_all_organizations()

    @Test
    public void should_throw_exception_500_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_all_organizations()

    @Test
    public void should_throw_exception_503_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_all_organizations()

    @Test
    public void should_throw_exception_1337_all_organizations() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizations(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_all_organizations()

    @Test
    public void should_given_all_organizations_with_adaccounts()
	    throws IOException, InterruptedException, SnapResponseErrorException, SnapOAuthAccessTokenException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getSnapAllOrganizationsWithAdAccount());
	List<OrganizationWithAdAccount> organizations = snapOrganization
		.getAllOrganizationsWithAdAccounts(oAuthAccessToken);
	assertThat(organizations.isEmpty()).isFalse();
	assertThat(organizations.size()).isEqualTo(1);
	OrganizationWithAdAccount org = organizations.get(0);
	assertThat(org.getId()).isEqualTo("40d6719b-da09-410b-9185-0cc9c0dfed1d");
	assertThat(org.getName()).isEqualTo("Hooli Inc");
	assertThat(org.getCountry()).isEqualTo("US");
	assertThat(org.getPostalCode()).isEqualTo("90291");
	assertThat(org.getLocality()).isEqualTo("Venice");
	assertThat(org.getContact_name()).isEmpty();
	assertThat(org.getContact_email()).isEmpty();
	assertThat(org.getContact_phone()).isEmpty();
	assertThat(org.getTaxType()).isEqualTo("NONE");
	assertThat(org.getAddressLine1()).isEqualTo("64 Market St");
	assertThat(org.getAdministrativeDistrictLevel1()).isEqualTo("CA");
	assertThat(org.getPartnerOrgs()).isNotEmpty();
	assertThat(org.getPartnerOrgs().size()).isEqualTo(5);
	assertThat(org.getPartnerOrgs().get("507d7a57-94ae-4239-8a74-e93c00aa53e6")).isEqualTo("Cyberdyne Corp. ");
	assertThat(org.getPartnerOrgs().get("306bb6f4-e80c-4c9c-9e39-2a4d673998a8")).isEqualTo("Initech Inc");
	assertThat(org.getPartnerOrgs().get("8c68b16f-03a6-4203-9480-f969fa1492d9")).isEqualTo("Nextably App");
	assertThat(org.getPartnerOrgs().get("5b3e970a-fa32-4a6a-a51d-bbf483a6d405")).isEqualTo("Givester App");
	assertThat(org.getPartnerOrgs().get("94da8871-921a-4e45-b032-8045795aecaf")).isEqualTo("Pied Piper corp");
	assertThat(org.getAcceptedTermVersion()).isEqualTo(8.0);
	assertThat(org.isAgency()).isEqualTo(true);
	assertThat(org.getType()).isEqualTo(TypeOrganizationEnum.ENTERPRISE);
	assertThat(org.getState()).isEqualTo("ACTIVE");
	assertThat(org.getConfigurationSettings()).isNotNull();
	assertThat(org.getConfigurationSettings().isNotificationsEnabled()).isTrue();
	assertThat(org.getRoles()).isNotNull();
	assertThat(org.getRoles().size()).isEqualTo(1);
	assertThat(org.getRoles().get(0)).isEqualTo("member");

	assertThat(org.getAdAccounts()).isNotEmpty();
	assertThat(org.getAdAccounts().size()).isEqualTo(3);

	AdAccount ad1 = org.getAdAccounts().get(0);
	assertThat(ad1.getId()).isEqualTo("8b8e40af-fc64-455d-925b-ca80f7af6914");
	assertThat(ad1.getName()).isEqualTo("Hooli Originals");
	assertThat(ad1.getCurrency()).isEqualTo(CurrencyEnum.USD);
	assertThat(ad1.getTimezone()).isEqualTo("America/Los_Angeles");
	assertThat(ad1.getType()).isEqualTo(TypeOrganizationEnum.PARTNER);
	assertThat(ad1.getStatus()).isEqualTo(StatusEnum.ACTIVE);
	assertThat(ad1.getRoles()).isNotNull();
	assertThat(ad1.getRoles().size()).isEqualTo(1);
	assertThat(ad1.getRoles().get(0)).isEqualTo("reports");

	AdAccount ad2 = org.getAdAccounts().get(1);
	assertThat(ad2.getId()).isEqualTo("497979f0-ea17-4971-8288-054883f1caca");
	assertThat(ad2.getName()).isEqualTo("Pied piper Test Account");
	assertThat(ad2.getCurrency()).isEqualTo(CurrencyEnum.USD);
	assertThat(ad2.getTimezone()).isEqualTo("America/Los_Angeles");
	assertThat(ad2.getType()).isEqualTo(TypeOrganizationEnum.PARTNER);
	assertThat(ad2.getStatus()).isEqualTo(StatusEnum.ACTIVE);
	assertThat(ad2.getRoles()).isNotNull();
	assertThat(ad2.getRoles().size()).isEqualTo(1);
	assertThat(ad2.getRoles().get(0)).isEqualTo("admin");

	AdAccount ad3 = org.getAdAccounts().get(2);
	assertThat(ad3.getId()).isEqualTo("22ada972-f2aa-4d06-a45a-a7a80f53ae34");
	assertThat(ad3.getName()).isEqualTo("Initech Corp");
	assertThat(ad3.getCurrency()).isEqualTo(CurrencyEnum.USD);
	assertThat(ad3.getTimezone()).isEqualTo("America/Los_Angeles");
	assertThat(ad3.getType()).isEqualTo(TypeOrganizationEnum.PARTNER);
	assertThat(ad3.getStatus()).isEqualTo(StatusEnum.ACTIVE);
	assertThat(ad3.getRoles()).isNotNull();
	assertThat(ad3.getRoles().size()).isEqualTo(1);
	assertThat(ad3.getRoles().get(0)).isEqualTo("creative");

	assertThat(org.getMyDisplayName()).isEqualTo("Honey Badger");
	assertThat(org.getMyInvitedEmail()).isEqualTo("honey.badget@hooli.com");
	assertThat(org.getMyMemberId()).isEqualTo("8454ada6-cec8-4e97-a0a7-c0b262c4137b");
    } // should_given_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_401_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_403_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_404_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_405_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_406_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_410_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_418_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_429_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_500_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_503_all_organizations_with_adaccounts() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_all_organizations_with_adaccounts()

    @Test
    public void should_throw_exception_1337_all_organizations_with_adaccounts() throws IOException,
	    InterruptedException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getAllOrganizationsWithAdAccounts(oAuthAccessToken))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_all_organizations_with_adaccounts()

    @Test
    public void should_given_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapSpecificOrganization());

	Optional<Organization> organization = snapOrganization.getSpecificOrganization(oAuthAccessToken, id);
	assertThat(organization.isPresent()).isTrue();
	organization.ifPresent(org -> {
	    assertThat(org.getId()).isEqualTo(id);
	    assertThat(org.getAddressLine1()).isEqualTo("101 Stewart St");
	    assertThat(org.getAdministrativeDistrictLevel1()).isEqualTo("WA");
	    assertThat(org.getCountry()).isEqualTo("US");
	    assertThat(org.getLocality()).isEqualTo("Seattle");
	    assertThat(org.getName()).isEqualTo("My Organization");
	    assertThat(org.getPostalCode()).isEqualTo("98134");
	    assertThat(org.getType()).isEqualTo(TypeOrganizationEnum.ENTERPRISE);
	});
    } // should_given_specific_organization()

    @Test
    public void should_throw_exception_authentication_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(null, id))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // should_throw_exception_authentication_specific_organization()

    @Test
    public void should_throw_exception_argument_id_2_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The organization ID is mandatory");
    } // should_throw_exception_argument_id_2_specific_organization()

    @Test
    public void should_throw_exception_argument_id_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The organization ID is mandatory");
    } // should_throw_exception_argument_id_specific_organization()

    @Test
    public void should_throw_exception_401_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_specific_organization()

    @Test
    public void should_throw_exception_403_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_specific_organization()

    @Test
    public void should_throw_exception_404_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_specific_organization()

    @Test
    public void should_throw_exception_405_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_specific_organization()

    @Test
    public void should_throw_exception_406_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_specific_organization()

    @Test
    public void should_throw_exception_410_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_specific_organization()

    @Test
    public void should_throw_exception_418_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_specific_organization()

    @Test
    public void should_throw_exception_429_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_specific_organization()

    @Test
    public void should_throw_exception_500_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_specific_organization()

    @Test
    public void should_throw_exception_503_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_specific_organization()

    @Test
    public void should_throw_exception_1337_specific_organization() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapOrganization.getSpecificOrganization(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_specific_organization()
} // SnapOrganizationTest
