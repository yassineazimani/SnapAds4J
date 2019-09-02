package snap.api.model.demographics;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.GenderEnum;

/**
 * Demographics
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class Demographics {

  /** Identifiant */
  private Long id;

  /** Age groups */
  @JsonProperty("age_groups")
  private List<String> ageGroups;

  /** Min age */
  @JsonProperty("min_age")
  private Double minAge;

  /** Max age */
  @JsonProperty("max_age")
  private Double maxAge;

  /** Gender */
  private GenderEnum gender;

  /** Languages */
  private List<String> languages;

  /** Advanced demographics */
  @JsonProperty("advanced_demographics")
  private List<String> advancedDemographics;
} // DemographicsRequest
