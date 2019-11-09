package snap.api.model.media;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.MediaStatusTypeEnum;
import snap.api.enums.MediaTypeEnum;

/**
 * CreativeMedia
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class CreativeMedia {
    
    /** Ad account ID */
    @JsonProperty("id")
    private String id;
    
    /** Last date update of Creative Media */
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date updatedAt;

    /** Date creation of Creative Media */
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdAt;
    
    @JsonProperty("ad_account_id")
    private String adAccountId;
    
    @JsonProperty("download_link")
    private String downloadLink;
    
    @JsonProperty("media_status")
    private MediaStatusTypeEnum mediaStatus;
    
    private String name;
    
    private MediaTypeEnum type;
    
    /**
     * Doesn't know contents, it doesn't exist in the documentation API.
     */
    @JsonProperty("lens_package_metadata")
    private String lensPackageMetadata;

}// CreativeMedia
