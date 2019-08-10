package snap.api.model.organization;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.CurrencyEnum;
import snap.api.enums.StatusEnum;
import snap.api.enums.TypeOrganizationEnum;

@Getter
@Setter
@ToString
public class AdAccount {

  private String id;

  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  private String name;

  private TypeOrganizationEnum type;

  private StatusEnum status;

  private CurrencyEnum currency;

  private String timezone;

  private List<String> roles;
} // AdAccount
