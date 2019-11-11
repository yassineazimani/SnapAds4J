package snap.api.model.media;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import snap.api.model.SnapHttpResponse;

@Getter
@Setter
public class SnapHttpResponseLinkMedia extends SnapHttpResponse{

    @JsonProperty("request_status")
    private String requestStatus;
    
    @JsonProperty("request_id")
    private String requestId;
    
    @JsonProperty("expires_at")
    private Date expiresAt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String link;

}// SnapHttpResponseLinkMedia
