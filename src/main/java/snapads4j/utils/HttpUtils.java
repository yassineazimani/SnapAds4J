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
package snapads4j.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import snapads4j.model.auth.Auth;
import snapads4j.model.config.HttpDeleteWithBody;

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
     * Prepare POST request HTTP
     *
     * @param url              url
     * @params args Data to send (Only String, no binary)
     * @return HttpRequest
     * @throws UnsupportedEncodingException
     */
    public static HttpPost preparePostRequestAuth(String url, Auth auth)
	    throws JsonProcessingException, UnsupportedEncodingException {
	HttpPost request = new HttpPost(url);
	List<BasicNameValuePair> params = new ArrayList<>();
	params.add(new BasicNameValuePair("grant_type", auth.getGrantType()));
	params.add(new BasicNameValuePair("code", auth.getCode()));
	params.add(new BasicNameValuePair("redirect_uri", auth.getRedirectUri()));
	params.add(new BasicNameValuePair("client_id", auth.getClientId()));
	params.add(new BasicNameValuePair("client_secret", auth.getClientSecret()));
	UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "utf-8");
	request.setEntity(urlEncodedFormEntity);
	return request;
    } // preparePostRequestAuth()
    
    /**
     * Prepare POST request HTTP (Upload file)
     * @param url
     * @param oAuthAccessToken
     * @param fileToUpload
     * @return
     */
    public static HttpPost preparePostUpload(String url, String oAuthAccessToken, File fileToUpload) {
	HttpPost request = new HttpPost(url);
	request.addHeader("Content-Type", "multipart/form-data");
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	builder.addBinaryBody(fileToUpload.getName(), fileToUpload);
	HttpEntity multipart = builder.build();
	request.setEntity(multipart);
	return request;
    }// preparePostUpload()
    
    /**
     * Prepare POST request HTTP (Upload file)
     * @param url
     * @param oAuthAccessToken
     * @param fileToUpload
     * @return
     */
    public static HttpPost preparePostUpload(String url, String oAuthAccessToken, File fileToUpload, Map<String, String> metaData) {
	HttpPost request = new HttpPost(url);
	request.addHeader("Content-Type", "multipart/form-data");
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	if(fileToUpload != null) {
	    builder.addBinaryBody(fileToUpload.getName(), fileToUpload);
	}
	for(Map.Entry<String, String> entry : metaData.entrySet()) {
	    builder.addTextBody(entry.getKey(), entry.getValue());
	}
	HttpEntity multipart = builder.build();
	request.setEntity(multipart);
	return request;
    }// preparePostUpload()

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
    
    /**
     * Prepare DELETE request HTTP
     * (Imao, It's a wrong way in design SnapChat API..., but i have no choice to do like this)
     * @param url              url
     * @param oAuthAccessToken oAuthAccessToken
     * @params args Data to send (Only String, no binary)
     * @return HttpRequest
     * @throws UnsupportedEncodingException
     */
    public static HttpDeleteWithBody prepareDeleteRequestObject(String url, String oAuthAccessToken, Object args)
	    throws JsonProcessingException, UnsupportedEncodingException {
	HttpDeleteWithBody request = new HttpDeleteWithBody(url);
	ObjectMapper mapper = new ObjectMapper();
	String requestBody = mapper.writeValueAsString(args);
	request.setEntity(new StringEntity(requestBody));
	request.addHeader("Content-Type", "application/json");
	request.addHeader("Authorization", "Bearer " + oAuthAccessToken);
	return request;
    } // prepareDeleteRequestObject()
} // HttpUtils
