package snap.api.user;

import java.net.http.HttpClient;

import snap.api.utils.FileProperties;

public class SnapUser implements SnapUserInterface {

  private FileProperties fp;

  private String apiUrl;

  private HttpClient httpClient;

  public SnapUser() {
    this.fp = new FileProperties();
    this.apiUrl = (String) fp.getProperties().get("api.url.user.me");
    this.httpClient = HttpClient.newHttpClient();
  } // SnapUser()
} // SnapUser
