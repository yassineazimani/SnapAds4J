package snap.api.model.thirdparty;

public class PaidImpressionTrackingUrlBuilder {

	private PaidImpressionTrackingUrl instance;

	public PaidImpressionTrackingUrlBuilder() {
		this.instance = new PaidImpressionTrackingUrl();
	}// PaidImpressionTrackingUrlBuilder()
	
	public PaidImpressionTrackingUrlBuilder setExpandedTrackingUrl(String expandedTrackingUrl) {
		this.instance.setExpandedTrackingUrl(expandedTrackingUrl);
		return this;
	}// setExpandedTrackingUrl()
	
	public PaidImpressionTrackingUrlBuilder setTrackingUrl(String trackingUrl) {
		this.instance.setTrackingUrl(trackingUrl);
		return this;
	}// setTrackingUrl()
	
	public PaidImpressionTrackingUrlBuilder setTrackingUrlMetadata(String trackingUrlMetadata) {
		this.instance.setTrackingUrlMetadata(trackingUrlMetadata);
		return this;
	}// setTrackingUrlMetadata()

	public PaidImpressionTrackingUrl build() {
		return this.instance;
	}// build()

}// PaidImpressionTrackingUrlBuilder
