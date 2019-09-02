package snap.api.model.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.ProximityUnitEnum;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Circle {

  private double latitude;

  private double longitude;

  private int radius;

  private ProximityUnitEnum unit;

  public Circle(double latitude, double longitude, int radius) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.radius = radius;
  } // Circle()
} // Circle
