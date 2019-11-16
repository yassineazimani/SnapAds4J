package snap.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SnapHTTPResponse
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
public class SnapHttpResponse {

  @JsonProperty("request_status")
  private String requestStatus;

  @JsonProperty("request_id")
  private String requestId;
} // SnapHttpResponse
