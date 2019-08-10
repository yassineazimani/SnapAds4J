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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Organization {

  private String id;

  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  private String name;

  @JsonProperty("address_line_1")
  private String addressLine1;

  private String locality;

  @JsonProperty("administrative_district_level_1")
  private String administrativeDistrictLevel1;

  private String country;

  @JsonProperty("postal_code")
  private String postalCode;

  private TypeOrganizationEnum type;
} // Organization
