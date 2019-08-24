package snap.api.model.targeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Targeting {

  public Targeting() {
    this.demographics = new ArrayList<>();
    this.devices = new ArrayList<>();
    this.geos = new ArrayList<>();
    this.interests = new ArrayList<>();
    this.segments = new ArrayList<>();
  }

  /**
   * Demographics. <br>
   * Example : "demographics": [{ "age_groups": [ "21-24", "25-34" ], "gender": "MALE" }]
   * demographics.put("age_groups", new String[]{"21-24", "25-34"}); demographics.put("gender",
   * "MALE");
   */
  private List<Map<String, Object>> demographics;

  /**
   * Devices <br>
   * Example : [{ "os_type": "iOS", "os_version_min": "9.0", "os_version_max": "10.3.2" }]
   */
  private List<Map<String, Object>> devices;

  /**
   * Geo <br>
   * Example : [{ "country_code": "us" }]
   */
  private List<Map<String, Object>> geos;

  /**
   * Interests <br>
   * Example : [{ "category_id": ["DLXS_1"] }]
   */
  private List<Map<String, Object>> interests;

  @JsonProperty("regulated_content")
  private boolean regulated_content;

  private List<Map<String, Object>> segments;
} // Targeting
