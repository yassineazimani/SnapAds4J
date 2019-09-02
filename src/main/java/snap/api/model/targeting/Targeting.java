package snap.api.model.targeting;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.model.demographics.Demographics;
import snap.api.model.device.Device;
import snap.api.model.geolocation.GeoLocation;
import snap.api.model.interest.Interest;
import snap.api.model.segment.SegmentRequestTargeting;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class Targeting {

  /** List of Demographic Targets. */
  private List<Demographics> demographics;

  /** List of Device Targets */
  private List<Device> devices;

  /** List of Geo Targets */
  private List<GeoLocation> geos;

  /** List of Location Targets */
  private List<Map<String, Object>> locations;

  /** List of Interest Targets */
  private List<Interest> interests;

  /** Flag to mark content within the Ad Squad as Regulated Content */
  @JsonProperty("regulated_content")
  private boolean regulatedContent;

  /** List of Snap Audience Match Segment Targets */
  private List<SegmentRequestTargeting> segments;
} // Targeting
