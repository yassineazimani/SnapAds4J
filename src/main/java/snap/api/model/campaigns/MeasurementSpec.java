package snap.api.model.campaigns;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MeasurementSpec {

  @JsonProperty("ios_app_id")
  private String iosAppId;

  @JsonProperty("android_app_url")
  private String androidAppUrl;
} // MeasurementSpec
