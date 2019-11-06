package snap.api.user;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.user.AuthenticatedUser;
import snap.api.model.user.SnapHttpResponseUser;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

@Getter
@Setter
public class SnapUser implements SnapUserInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointMe;

    private CloseableHttpClient httpClient;
    
    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapUser.class);

    /** Constructor */
    public SnapUser() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointMe = this.apiUrl + (String) fp.getProperties().get("api.url.user.me");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
    } // SnapUser()

    /**
     * Get informations about the authenticated user.
     *
     * @see <a href="https://developers.snapchat.com/api/docs/#user">User</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @return AuthenticatedUser {@link #AuthenticatedUser}
     * @throws SnapOAuthAccessTokenException
     * @throws SnapResponseErrorException
     */
    @Override
    public Optional<AuthenticatedUser> aboutMe(String oAuthAccessToken)
	    throws SnapOAuthAccessTokenException, SnapResponseErrorException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	Optional<AuthenticatedUser> result = Optional.empty();
	HttpGet request = HttpUtils.prepareGetRequest(this.endpointMe, oAuthAccessToken);
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
		SnapHttpResponseUser responseFromJson = mapper.readValue(body, SnapHttpResponseUser.class);
		if (responseFromJson != null) {
		    result = Optional.ofNullable(responseFromJson.getMe());
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get informations about me", e);
	}
	return result;
    } // aboutMe()
} // SnapUser
