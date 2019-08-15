package snap.api.model.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Authenticated user informations
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class AuthenticatedUser {

  /** User ID */
  private String id;

  /** Last date update of the user */
  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  /** Date creation of the user */
  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  /** Email */
  private String email;

  /** Organization ID */
  @JsonProperty("organization_id")
  private String organizationId;

  /** Display name */
  @JsonProperty("display_name")
  private String displayName;
} // AuthenticatedUser
