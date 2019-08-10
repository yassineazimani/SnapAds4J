package snap.api.organization;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

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
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

@Getter
@Setter
public class SnapOrganization implements SnapOrganizationInterface {

  private FileProperties fp;

  private String apiUrl;

  private String endpointAllOrganizations;

  private String endpointSpecificOrganization;

  private HttpClient httpClient;

  public SnapOrganization() {
    this.fp = new FileProperties();
    this.apiUrl = (String) fp.getProperties().get("api.url");
    this.endpointAllOrganizations = (String) fp.getProperties().get("api.url.organizations.all");
    this.endpointSpecificOrganization =
        (String) fp.getProperties().get("api.url.organizations.one");
    this.httpClient = HttpClient.newHttpClient();
  } // SnapOrganization()

  @Override
  public List<Organization> getAllOrganizations(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    List<Organization> organizations = new ArrayList<>();
    final String url = this.apiUrl + this.endpointAllOrganizations;
    HttpRequest request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      String body = response.body();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      SnapHttpResponseOrganization responseFromJson =
          mapper.readValue(body, SnapHttpResponseOrganization.class);
      if (responseFromJson != null) {
        organizations = responseFromJson.getAllOrganizations();
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return organizations;
  } // getAllOrganizations()

  @Override
  public List<OrganizationWithAdAccount> getAllOrganizationsWithAdAccounts(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    List<OrganizationWithAdAccount> organizations = new ArrayList<>();
    final String url = this.apiUrl + this.endpointAllOrganizations + "?with_ad_accounts?true";
    HttpRequest request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      String body = response.body();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      SnapHttpResponseOrganizationWithAdAccount responseFromJson =
          mapper.readValue(body, SnapHttpResponseOrganizationWithAdAccount.class);
      if (responseFromJson != null) {
        organizations = responseFromJson.getAllOrganizations();
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return organizations;
  } // getAllOrganizationsWithAdAccounts()

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
    HttpRequest request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      String body = response.body();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      SnapHttpResponseOrganization responseFromJson =
          mapper.readValue(body, SnapHttpResponseOrganization.class);
      if (responseFromJson != null) {
        result = responseFromJson.getOrganization();
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  } // getSpecificOrganization()
} // SnapOrganization
