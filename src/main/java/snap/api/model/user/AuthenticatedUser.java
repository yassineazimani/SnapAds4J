package snap.api.model.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthenticatedUser {

  private String id;
  
  @JsonFormat
  (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;
  
  @JsonFormat
  (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;
  
  private String email;
  
  private String organizationId;
  
  private String displayName;
  
} // AuthenticatedUser
