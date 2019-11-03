package snap.api.model.thirdparty;

public class SwipeTrackingUrlBuilder {

	private SwipeTrackingUrl instance;
	
	public SwipeTrackingUrlBuilder() {
		this.instance = new SwipeTrackingUrl();
	}// SwipeTrackingUrlBuilder()
	
	public SwipeTrackingUrlBuilder setExpandedTrackingUrl(String expandedTrackingUrl) {
		this.instance.setExpandedTrackingUrl(expandedTrackingUrl);
		return this;
	}// setExpandedTrackingUrl()
	
	public SwipeTrackingUrlBuilder setTrackingUrl(String trackingUrl) {
		this.instance.setTrackingUrl(trackingUrl);
		return this;
	}// setTrackingUrl()
	
	public SwipeTrackingUrlBuilder setTrackingUrlMetadata(String trackingUrlMetadata) {
		this.instance.setTrackingUrlMetadata(trackingUrlMetadata);
		return this;
	}// setTrackingUrlMetadata()
	
	public SwipeTrackingUrl build() {
		return this.instance;
	}// build()
}// SwipeTrackingUrlBuilder
