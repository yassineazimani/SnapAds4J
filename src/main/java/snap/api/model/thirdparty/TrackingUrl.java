package snap.api.model.thirdparty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingUrl {

	private String trackingUrlMetadata;
	
	private String expandedTrackingUrl;
	
	@JsonProperty("tracking_url")
	private String trackingUrl;
	
}// TrackingUrl
