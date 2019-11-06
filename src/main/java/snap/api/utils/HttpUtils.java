package snap.api.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Http Utils
 *
 * @author Yassine
 */
public class HttpUtils {

    /**
     * Prepare GET request HTTP
     *
     * @param url              url
     * @param oAuthAccessToken oAuthAccessToken
     * @return HttpRequest
     */
    public static HttpGet prepareGetRequest(String url, String oAuthAccessToken) {
	HttpGet request = new HttpGet(url);
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	return request;
    } // prepareGetRequest()

    /**
     * Prepare POST request HTTP
     *
     * @param url              url
     * @param oAuthAccessToken oAuthAccessToken
     * @params args Data to send (Only String, no binary)
     * @return HttpRequest
     * @throws UnsupportedEncodingException
     */
    public static HttpPost preparePostRequest(String url, Map<String, String> args)
	    throws JsonProcessingException, UnsupportedEncodingException {
	HttpPost request = new HttpPost(url);
	ObjectMapper mapper = new ObjectMapper();
	String requestBody = mapper.writeValueAsString(args);
	request.setEntity(new StringEntity(requestBody));
	request.addHeader("Content-Type", "application/json");
	return request;
    } // preparePostRequest()

    /**
     * Prepare POST request HTTP
     *
     * @param url              url
     * @param oAuthAccessToken oAuthAccessToken
     * @params args Data to send (Only String, no binary)
     * @return HttpRequest
     * @throws UnsupportedEncodingException
     */
    public static HttpPost preparePostRequest(String url, String oAuthAccessToken, Map<String, String> args)
	    throws JsonProcessingException, UnsupportedEncodingException {
	HttpPost request = new HttpPost(url);
	ObjectMapper mapper = new ObjectMapper();
	String requestBody = mapper.writeValueAsString(args);
	request.setEntity(new StringEntity(requestBody));
	request.addHeader("Content-Type", "application/json");
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	return request;
    } // preparePostRequest()

    /**
     * Prepare POST request HTTP
     *
     * @param url              url
     * @param oAuthAccessToken oAuthAccessToken
     * @params args Data to send (Only String, no binary)
     * @return HttpRequest
     * @throws UnsupportedEncodingException
     */
    public static HttpPost preparePostRequestObject(String url, String oAuthAccessToken, Object args)
	    throws JsonProcessingException, UnsupportedEncodingException {
	HttpPost request = new HttpPost(url);
	ObjectMapper mapper = new ObjectMapper();
	String requestBody = mapper.writeValueAsString(args);
	request.setEntity(new StringEntity(requestBody));
	request.addHeader("Content-Type", "application/json");
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	return request;
    } // preparePostRequest()

    /**
     * Prepare PUT request HTTP
     *
     * @param url              url
     * @param oAuthAccessToken oAuthAccessToken
     * @params args Data to send (Only String, no binary)
     * @return HttpRequest
     * @throws UnsupportedEncodingException
     */
    public static HttpPut preparePutRequest(String url, String oAuthAccessToken, Map<String, String> args)
	    throws JsonProcessingException, UnsupportedEncodingException {
	HttpPut request = new HttpPut(url);
	ObjectMapper mapper = new ObjectMapper();
	String requestBody = mapper.writeValueAsString(args);
	request.setEntity(new StringEntity(requestBody));
	request.addHeader("Content-Type", "application/json");
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	return request;
    } // preparePutRequest()

    /**
     * Prepare PUT request HTTP
     *
     * @param url              url
     * @param oAuthAccessToken oAuthAccessToken
     * @params args Data to send (Only String, no binary)
     * @return HttpRequest
     * @throws UnsupportedEncodingException 
     */
    public static HttpPut preparePutRequestObject(String url, String oAuthAccessToken, Object args)
	    throws JsonProcessingException, UnsupportedEncodingException {
	HttpPut request = new HttpPut(url);
	ObjectMapper mapper = new ObjectMapper();
	String requestBody = mapper.writeValueAsString(args);
	request.setEntity(new StringEntity(requestBody));
	request.addHeader("Content-Type", "application/json");
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	return request;
    } // preparePutRequest()

    /**
     * Prepare DELETE request HTTP
     *
     * @param url              url
     * @param oAuthAccessToken oAuthAccessToken
     * @return HttpRequest
     */
    public static HttpDelete prepareDeleteRequest(String url, String oAuthAccessToken) {
	HttpDelete request = new HttpDelete(url);
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	return request;
    } // prepareDeleteRequest()
} // HttpUtils
