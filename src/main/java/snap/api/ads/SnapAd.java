package snap.api.ads;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import snap.api.adsquads.SnapAdSquads;
import snap.api.enums.CheckAdEnum;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.ads.Ad;
import snap.api.model.ads.SnapHttpRequestAd;
import snap.api.model.ads.SnapHttpResponseAd;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

/**
 * 
 * @author yassine
 *
 */
@Getter
@Setter
public class SnapAd implements SnapAdInterface {

	private FileProperties fp;

	private String apiUrl;

	private String endpointCreateAd;

	private String endpointUpdateAd;
	
	private String endpointDeleteAd;
	
	private String endpointSpecificAd;
	
	private String endpointAllAdsAdSquad;
	
	private String endpointAllAdsAdAccount;

	private HttpClient httpClient;

	private static final Logger LOGGER = LogManager.getLogger(SnapAdSquads.class);

	public SnapAd() {
		this.fp = new FileProperties();
		this.apiUrl = (String) fp.getProperties().get("api.url");
		this.endpointCreateAd = this.apiUrl + (String) fp.getProperties().get("api.url.ad.create");
		this.endpointUpdateAd = this.apiUrl + (String) fp.getProperties().get("api.url.ad.update");
		this.endpointDeleteAd = this.apiUrl + (String) fp.getProperties().get("api.url.ad.delete");
		this.endpointAllAdsAdSquad = this.apiUrl + (String) fp.getProperties().get("api.url.ad.all");
		this.endpointAllAdsAdAccount = this.apiUrl + (String) fp.getProperties().get("api.url.ad.all2");
		this.endpointSpecificAd = this.apiUrl + (String) fp.getProperties().get("api.url.ad.one");
		this.httpClient = HttpClient.newHttpClient();
	}// SnapAd()

	@Override
	public void createAd(String oAuthAccessToken, Ad ad) throws SnapOAuthAccessTokenException, JsonProcessingException,
			SnapResponseErrorException, SnapArgumentException {
		if (StringUtils.isEmpty(oAuthAccessToken)) {
			throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
		}
		checkSnapAd(ad, CheckAdEnum.CREATION);
		final String url = this.endpointCreateAd.replace("{ad_squad_id}", ad.getAdSquadId());
		SnapHttpRequestAd reqBody = new SnapHttpRequestAd();
		reqBody.addAd(ad);
		LOGGER.info("Body create ad => {}", reqBody);
		HttpRequest request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
		try {
			HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
			int statusCode = response.statusCode();
			if (statusCode >= 300) {
				SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
				throw ex;
			}
		} catch (IOException | InterruptedException e) {
			LOGGER.error("Impossible to create ad, ad_squad_id = {}", ad.getAdSquadId(), e);
		}
	}// createAd()

	@Override
	public void updateAd(String oAuthAccessToken, Ad ad) throws SnapOAuthAccessTokenException, JsonProcessingException,
			SnapResponseErrorException, SnapArgumentException {
		if (StringUtils.isEmpty(oAuthAccessToken)) {
			throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
		}
		if (StringUtils.isEmpty(oAuthAccessToken)) {
			throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
		}
		checkSnapAd(ad, CheckAdEnum.UPDATE);
		final String url = this.endpointUpdateAd.replace("{ad_squad_id}", ad.getAdSquadId());
		SnapHttpRequestAd reqBody = new SnapHttpRequestAd();
		reqBody.addAd(ad);
		LOGGER.info("Body update ad squad => {}", reqBody);
		HttpRequest request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
		try {
			HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
			int statusCode = response.statusCode();
			if (statusCode >= 300) {
				SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
				throw ex;
			}
		} catch (IOException | InterruptedException e) {
			LOGGER.error("Impossible to update ad, id = {}", ad.getId(), e);
		}
	}// updateAd()

	@Override
	public List<Ad> getAllAdsFromAdSquad(String oAuthAccessToken, String adSquadId)
			throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException {
		if (StringUtils.isEmpty(oAuthAccessToken)) {
			throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
		}
		if (StringUtils.isEmpty(adSquadId)) {
			throw new SnapArgumentException("The AdSquad ID is mandatory");
		}
		List<Ad> results = new ArrayList<>();
		final String url = this.endpointAllAdsAdSquad.replace("{ad_squad_id}", adSquadId);
		HttpRequest request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
		try {
			HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
			int statusCode = response.statusCode();
			String body = response.body();
			if (statusCode >= 300) {
				SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
				throw ex;
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			SnapHttpResponseAd responseFromJson = mapper.readValue(body, SnapHttpResponseAd.class);
			if (responseFromJson != null) {
				results = responseFromJson.getAllAd();
			}
		} catch (IOException | InterruptedException e) {
			LOGGER.error("Impossible to get all ads, adSquadId = {}", adSquadId, e);
		}
		return results;
	}// getAllAdsFromAdSquad()

	@Override
	public List<Ad> getAllAdsFromAdAccount(String oAuthAccessToken, String adAccountId)
			throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException {
		if (StringUtils.isEmpty(oAuthAccessToken)) {
			throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
		}
		if (StringUtils.isEmpty(adAccountId)) {
			throw new SnapArgumentException("The AdAccount ID is mandatory");
		}
		List<Ad> results = new ArrayList<>();
		final String url = this.endpointAllAdsAdAccount.replace("{ad_account_id}", adAccountId);
		HttpRequest request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
		try {
			HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
			int statusCode = response.statusCode();
			String body = response.body();
			if (statusCode >= 300) {
				SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
				throw ex;
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			SnapHttpResponseAd responseFromJson = mapper.readValue(body, SnapHttpResponseAd.class);
			if (responseFromJson != null) {
				results = responseFromJson.getAllAd();
			}
		} catch (IOException | InterruptedException e) {
			LOGGER.error("Impossible to get all ads, adAccountId = {}", adAccountId, e);
		}
		return results;
	}// getAllAdsFromAdAccount()

	@Override
	public Optional<Ad> getSpecificAd(String oAuthAccessToken, String id)
			throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException {
		if (StringUtils.isEmpty(oAuthAccessToken)) {
			throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
		}
		if (StringUtils.isEmpty(id)) {
			throw new SnapArgumentException("The AdSquad ID is mandatory");
		}
		Optional<Ad> result = Optional.empty();
		final String url = this.endpointSpecificAd + id;
		HttpRequest request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
		try {
			HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
			int statusCode = response.statusCode();
			String body = response.body();
			if (statusCode >= 300) {
				SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
				throw ex;
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			SnapHttpResponseAd responseFromJson = mapper.readValue(body, SnapHttpResponseAd.class);
			if (responseFromJson != null) {
				result = responseFromJson.getSpecificAd();
			}
		} catch (IOException | InterruptedException e) {
			LOGGER.error("Impossible to get specific Ad, id = {}", id, e);
		}
		return result;
	}// getSpecificAd()

	@Override
	public void deleteAd(String oAuthAccessToken, String id)
			throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		if (StringUtils.isEmpty(oAuthAccessToken)) {
			throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
		}
		if (StringUtils.isEmpty(id)) {
			throw new SnapArgumentException("The Ad ID is mandatory");
		}
		final String url = this.endpointDeleteAd + id;
		HttpRequest request = HttpUtils.prepareDeleteRequest(url, oAuthAccessToken);
		try {
			HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
			int statusCode = response.statusCode();
			if (statusCode >= 300) {
				SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
				throw ex;
			}
		} catch (IOException | InterruptedException e) {
			LOGGER.error("Impossible to delete specific ad, id = {}", id, e);
		}
	}// deleteAd()

	private void checkSnapAd(Ad ad, CheckAdEnum check) throws SnapArgumentException {
		if (check == null) {
			throw new SnapArgumentException("Please give type of checking Ad");
		}
		StringBuilder sb = new StringBuilder();
		if (ad == null) {
			sb.append("Ad parameter is not given,");
		} else {
			if(check == CheckAdEnum.UPDATE) {
				if (StringUtils.isEmpty(ad.getId())) {
					sb.append("The Ad ID is required,");
				}
			}
			if (StringUtils.isEmpty(ad.getAdSquadId())) {
				sb.append("Ad Squad ID parameter is not given,");
			}
			if (StringUtils.isEmpty(ad.getName())) {
				sb.append("Ad's name parameter is not given,");
			}
			if (ad.getStatus() == null) {
				sb.append("Ad's status parameter is not given,");
			}
		}
		String finalErrors = sb.toString();
		if (!StringUtils.isEmpty(finalErrors)) {
			finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
			throw new SnapArgumentException(finalErrors);
		}
	}// checkSnapAd()
}// SnapAd
