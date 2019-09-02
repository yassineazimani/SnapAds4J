package snap.api.model.location;

import java.util.List;

import snap.api.enums.OperationEnum;
import snap.api.enums.ProximityUnitEnum;

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
