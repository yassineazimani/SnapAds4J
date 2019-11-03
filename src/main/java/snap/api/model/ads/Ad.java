package snap.api.model.ads;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import snap.api.enums.AdTypeEnum;
import snap.api.enums.StatusEnum;
import snap.api.model.thirdparty.PaidImpressionTrackingUrl;
import snap.api.model.thirdparty.SwipeTrackingUrl;

@Getter
@Setter
public class Ad {

	private String id;
	
	private String name;
	
	@JsonProperty("created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date createdAt;
	
	@JsonProperty("updated_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date updatedAt;
	
	private StatusEnum status;
	
	private AdTypeEnum type;

	@JsonProperty("ad_squad_id")
	private String adSquadId;
	
	@JsonProperty("creative_id")
	private String creativeId;
	
	@JsonProperty("third_party_on_swipe_tracking_urls")
	private SwipeTrackingUrl thirdPartyOnSwipeTrackingUrls;
	
	@JsonProperty("third_party_paid_impression_tracking_urls")
	private PaidImpressionTrackingUrl thirdPartyPaidImpressionTrackingUrls;

}// Ad
