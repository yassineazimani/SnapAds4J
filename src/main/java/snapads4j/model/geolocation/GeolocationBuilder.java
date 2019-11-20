/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.model.geolocation;

import java.util.List;

import snapads4j.enums.OperationEnum;

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
