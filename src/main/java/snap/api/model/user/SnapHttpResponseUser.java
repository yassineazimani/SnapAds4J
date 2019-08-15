package snap.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.model.SnapHttpResponse;

/**
 * SnapHttpResponseUser
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapHttpResponseUser extends SnapHttpResponse {

  private AuthenticatedUser me;
} // SnapHttpResponseUser
