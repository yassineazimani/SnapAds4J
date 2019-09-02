package snap.api.model.geolocation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.OperationEnum;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class GeoLocation {

  @JsonProperty("country_code")
  private String countryCode;

  /** Postal code */
  @JsonProperty("postal_code")
  private List<Integer> postalCodes;

  /** Metropole IDS */
  @JsonProperty("metro_id")
  private List<Integer> metroIds;

  private OperationEnum operation;
} // GeoLocation
