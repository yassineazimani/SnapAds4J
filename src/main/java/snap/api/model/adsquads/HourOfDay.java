package snap.api.model.adsquads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HourOfDay {

  @JsonProperty("hour_of_day")
  private int[] hourOfDay;
} // HourOfDay
