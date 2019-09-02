package snap.api.model.location;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.OperationEnum;
import snap.api.enums.ProximityUnitEnum;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class Location {

  @JsonProperty("location_type")
  private List<String> locationTypes;

  private List<Circle> circles;

  private OperationEnum operation;

  @JsonProperty("proximity_unit")
  private ProximityUnitEnum proximityUnit;

  private Integer proximity;
} // Location
