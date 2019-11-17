package snap.api.model.targeting;

import java.util.List;
import java.util.Map;

import snap.api.model.demographics.Demographics;
import snap.api.model.device.Device;
import snap.api.model.geolocation.GeoLocation;
import snap.api.model.interest.Interest;
import snap.api.model.segment.SegmentRequestTargeting;

/**
 * Used to build Targeting instance (${@link Targeting})
 *
 * @author Yassine
 */
public class TargetingBuilder {

    private Targeting targetingInstance;

    public TargetingBuilder() {
	this.targetingInstance = new Targeting();
    } // TargetingBuilder()

    public TargetingBuilder setDemographics(List<Demographics> demographics) {
	this.targetingInstance.setDemographics(demographics);
	return this;
    } // setDemographics()

    public TargetingBuilder setDevices(List<Device> devices) {
	this.targetingInstance.setDevices(devices);
	return this;
    } // setDevices()

    public TargetingBuilder setGeolocation(List<GeoLocation> geos) {
	this.targetingInstance.setGeos(geos);
	return this;
    } // setGeolocation()

    public TargetingBuilder setInterests(List<Interest> interests) {
	this.targetingInstance.setInterests(interests);
	return this;
    } // setDevices()

    public TargetingBuilder setRegulatedContent(boolean regulatedContent) {
	this.targetingInstance.setRegulatedContent(regulatedContent);
	return this;
    } // setRegulatedContent()

    public TargetingBuilder setSegments(List<SegmentRequestTargeting> segments) {
	this.targetingInstance.setSegments(segments);
	return this;
    } // setSegments

    public TargetingBuilder setLocations(List<Map<String, Object>> locations) {
	this.targetingInstance.setLocations(locations);
	return this;
    } // setLocations

    public Targeting build() {
	return this.targetingInstance;
    } // build()
} // TargetingBuilder
