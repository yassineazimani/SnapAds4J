package snap.api.model.organization;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.TypeOrganizationEnum;

/**
 * Organization
 *
 * @author Yassine
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Organization {

  /** Organization ID */
  private String id;

  /** Last date update of organization */
  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  /** Date creation of organization */
  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  /** Organization's name */
  private String name;

  /** Organization's address */
  @JsonProperty("address_line_1")
  private String addressLine1;

  /** Organization locality */
  private String locality;

  /** Administrative district */
  @JsonProperty("administrative_district_level_1")
  private String administrativeDistrictLevel1;

  /** Organization country */
  private String country;

  /** Organization postal code */
  @JsonProperty("postal_code")
  private String postalCode;

  /** Type organization */
  private TypeOrganizationEnum type;
} // Organization
