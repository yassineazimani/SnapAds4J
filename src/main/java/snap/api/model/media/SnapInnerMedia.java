package snap.api.model.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SnapInnerMedia
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "sub_request_status" })
public class SnapInnerMedia {
    
    private CreativeMedia media;

}// SnapInnerMedia
