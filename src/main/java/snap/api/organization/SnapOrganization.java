package snap.api.organization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.organization.Organization;
import snap.api.model.organization.OrganizationWithAdAccount;
import snap.api.model.organization.SnapHttpResponseOrganization;
import snap.api.model.organization.SnapHttpResponseOrganizationWithAdAccount;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

@Getter
@Setter
public class SnapOrganization implements SnapOrganizationInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointAllOrganizations;

    private String endpointSpecificOrganization;

    private CloseableHttpClient httpClient;
    
    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapOrganization.class);

    public SnapOrganization() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointAllOrganizations = (String) fp.getProperties().get("api.url.organizations.all");
	this.endpointSpecificOrganization = (String) fp.getProperties().get("api.url.organizations.one");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
    } // SnapOrganization()

    /**
     * Get all organizations
     *
     * @see <a href="https://developers.snapchat.com/api/docs/#organizations">All
     *      organizations</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @return All organizations
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     */
    @Override
    public List<Organization> getAllOrganizations(String oAuthAccessToken)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	List<Organization> organizations = new ArrayList<>();
	final String url = this.apiUrl + this.endpointAllOrganizations;
	HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		String body = entityUtilsWrapper.toString(entity);
		if (statusCode >= 300) {
		    SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		    throw ex;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SnapHttpResponseOrganization responseFromJson = mapper.readValue(body,
			SnapHttpResponseOrganization.class);
		if (responseFromJson != null) {
		    organizations = responseFromJson.getAllOrganizations();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get all organizations", e);
	}
	return organizations;
    } // getAllOrganizations()

    /**
     * Get all organizations with ad accounts
     *
     * @see <a href="https://developers.snapchat.com/api/docs/#organizations">All
     *      organizations with ad accounts</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @return All organizations
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     */
    @Override
    public List<OrganizationWithAdAccount> getAllOrganizationsWithAdAccounts(String oAuthAccessToken)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	List<OrganizationWithAdAccount> organizations = new ArrayList<>();
	final String url = this.apiUrl + this.endpointAllOrganizations + "?with_ad_accounts?true";
	HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		String body = entityUtilsWrapper.toString(entity);
		if (statusCode >= 300) {
		    SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		    throw ex;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SnapHttpResponseOrganizationWithAdAccount responseFromJson = mapper.readValue(body,
			SnapHttpResponseOrganizationWithAdAccount.class);
		if (responseFromJson != null) {
		    organizations = responseFromJson.getAllOrganizations();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get all organizations with Ad-accounts", e);
	}
	return organizations;
    } // getAllOrganizationsWithAdAccounts()

    /**
     * Get specific organization
     *
     * @see <a href=
     *      "https://developers.snapchat.com/api/docs/#organizations">Specific
     *      organization</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               Organization ID
     * @return All organizations
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     */
    @Override
    public Optional<Organization> getSpecificOrganization(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(id)) {
	    throw new SnapArgumentException("The organization ID is mandatory");
	}
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	Optional<Organization> result = Optional.empty();
	final String url = this.apiUrl + this.endpointSpecificOrganization + id;
	HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		String body = entityUtilsWrapper.toString(entity);
		if (statusCode >= 300) {
		    SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		    throw ex;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SnapHttpResponseOrganization responseFromJson = mapper.readValue(body,
			SnapHttpResponseOrganization.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getOrganization();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get specific organization with id = {}", id, e);
	}
	return result;
    } // getSpecificOrganization()
} // SnapOrganization
