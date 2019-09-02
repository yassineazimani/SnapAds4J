package snap.api.model.geolocation;

import java.util.List;

import snap.api.enums.OperationEnum;

/**
 * Used to build Geolocation instance ({@link GeoLocation})
 *
 * @author Yassine
 */
public class GeolocationBuilder {

  private GeoLocation geoLocationInstance;

  public GeolocationBuilder() {
    this.geoLocationInstance = new GeoLocation();
  } // GeolocationBuilder()

  public GeolocationBuilder setCountryCode(String countryCode) {
    this.geoLocationInstance.setCountryCode(countryCode);
    return this;
  } // setCountryCode()

  public GeolocationBuilder setPostalCodes(List<Integer> postalCodes) {
    this.geoLocationInstance.setPostalCodes(postalCodes);
    return this;
  } // setPostalCodes()

  public GeolocationBuilder setMetroIds(List<Integer> metroIds) {
    this.geoLocationInstance.setMetroIds(metroIds);
    return this;
  } // setMetroIds()

  public GeolocationBuilder setOperation(OperationEnum operation) {
    this.geoLocationInstance.setOperation(operation);
    return this;
  } // setOperation()

  public GeoLocation build() {
    return this.geoLocationInstance;
  } // build()
} // GeolocationBuilder
