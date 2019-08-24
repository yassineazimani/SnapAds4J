package snap.api.adsquads;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import lombok.Setter;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.adsquads.AdSquad;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

/**
 * SnapAdSquads
 *
 * @author Yassine
 */
@Getter
@Setter
public class SnapAdSquads implements SnapAdSquadsInterface {

  private FileProperties fp;

  private String apiUrl;

  private String endpointAllAdSquadsCampaign;

  private String endpointAllAdSquadsAdAccount;

  private String endpointSpecificAdSquad;

  private String endpointCreationAdSquad;

  private String endpointUpdateAdSquad;

  private String endpointDeleteAdSquad;

  private HttpClient httpClient;

  private static final Logger LOGGER = LogManager.getLogger(SnapAdSquads.class);

  /** Constructor */
  public SnapAdSquads() {
    this.fp = new FileProperties();
    this.apiUrl = (String) fp.getProperties().get("api.url");
    this.endpointAllAdSquadsCampaign =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.all");
    this.endpointAllAdSquadsAdAccount =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.all2");
    this.endpointSpecificAdSquad =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.one");
    this.endpointCreationAdSquad =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.create");
    this.endpointUpdateAdSquad =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.update");
    this.endpointDeleteAdSquad =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.delete");
    this.httpClient = HttpClient.newHttpClient();
  } // SnapAdSquads()

  @Override
  public void createAdSquad(String oAuthAccessToken, AdSquad adSquad) {
    // TODO Auto-generated method stub

  } // createAdSquad()

  @Override
  public void updateAdSquad(String oAuthAccessToken, AdSquad adSquad) {
    // TODO Auto-generated method stub

  } // updateAdSquad()

  @Override
  public List<AdSquad> getAllAdSquadsFromCampaign(String oAuthAccessToken, String campaignId) {
    // TODO Auto-generated method stub
    return null;
  } // getAllAdSquadsFromCampaign()

  @Override
  public List<AdSquad> getAllAdSquadsFromAdAccount(String oAuthAccessToken, String adAccountId) {
    // TODO Auto-generated method stub
    return null;
  } // getAllAdSquadsFromAdAccount()

  @Override
  public Optional<AdSquad> getSpecificAdSquad(String oAuthAccessToken, String id) {
    // TODO Auto-generated method stub
    return null;
  } // getSpecificAdSquad()

  @Override
  public void deleteAdSquad(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    if (StringUtils.isEmpty(id)) {
      throw new SnapArgumentException("The Ad Squad ID is mandatory");
    }
    final String url = this.endpointDeleteAdSquad + id;
    HttpRequest request = HttpUtils.prepareDeleteRequest(url, oAuthAccessToken);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
    } catch (IOException | InterruptedException e) {
      LOGGER.error("Impossible to delete specific ad squad, id = {}", id, e);
    }
  } // deleteAdSquad()
} // SnapAdSquads
