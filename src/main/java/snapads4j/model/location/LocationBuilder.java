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
package snapads4j.model.location;

import java.util.List;

import snapads4j.enums.OperationEnum;
import snapads4j.enums.ProximityUnitEnum;

/**
 * Used to build Location instance ({@link Location})
 *
 * @author Yassine
 */
public class LocationBuilder {

  private Location locationInstance;

  public LocationBuilder() {
    this.locationInstance = new Location();
  } // LocationBuilder()

  public LocationBuilder setLocationTypes(List<String> locationTypes) {
    this.locationInstance.setLocationTypes(locationTypes);
    return this;
  } // setLocationTypes()

  public LocationBuilder setCircles(List<Circle> circles) {
    this.locationInstance.setCircles(circles);
    return this;
  } // setCircles()

  public LocationBuilder setOperation(OperationEnum operation) {
    this.locationInstance.setOperation(operation);
    return this;
  } // setOperation()

  public LocationBuilder setUnit(ProximityUnitEnum proximityUnit) {
    this.locationInstance.setProximityUnit(proximityUnit);
    return this;
  } // setUnit()

  public LocationBuilder setProximity(Integer proximity) {
    this.locationInstance.setProximity(proximity);
    return this;
  } // setProximity()

  public Location build() {
    return this.locationInstance;
  } // build()
} // LocationBuilder
