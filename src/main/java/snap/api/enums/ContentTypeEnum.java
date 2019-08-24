package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContentTypeEnum
 *
 * @author Yassine
 */
public enum ContentTypeEnum {
  /** NEWS */
  @JsonProperty("NEWS")
  NEWS,
  /** ENTERTAINMENT */
  @JsonProperty("ENTERTAINMENT")
  ENTERTAINMENT,
  /** SCIENCE_TECHNOLOGY */
  @JsonProperty("SCIENCE_TECHNOLOGY")
  SCIENCE_TECHNOLOGY,
  /** BEAUTY_FASHION */
  @JsonProperty("BEAUTY_FASHION")
  BEAUTY_FASHION,
  /** MENS_LIFESTYLE */
  @JsonProperty("MENS_LIFESTYLE")
  MENS_LIFESTYLE,
  /** WOMENS_LIFESTYLE */
  @JsonProperty("WOMENS_LIFESTYLE")
  WOMENS_LIFESTYLE,
  /** GENERAL_LIFESTYLE */
  @JsonProperty("GENERAL_LIFESTYLE")
  GENERAL_LIFESTYLE,
  /** FOOD */
  @JsonProperty("FOOD")
  FOOD,
  /** SPORTS */
  @JsonProperty("SPORTS")
  SPORTS,
  /** YOUNG_BOLD */
  @JsonProperty("YOUNG_BOLD")
  YOUNG_BOLD;
} // ContentTypeEnum
