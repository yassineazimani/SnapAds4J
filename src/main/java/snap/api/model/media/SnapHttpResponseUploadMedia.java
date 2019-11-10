package snap.api.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnapHttpResponseUploadMedia {

    @JsonProperty("request_status")
    private String requestStatus;
    
    @JsonProperty("request_id")
    private String requestId;
    
    @JsonProperty("upload_id")
    private String uploadId;
    
    @JsonProperty("add_path")
    private String addPath;
    
    @JsonProperty("finalize_path")
    private String finalizePath;

}// SnapHttpResponseMedia
