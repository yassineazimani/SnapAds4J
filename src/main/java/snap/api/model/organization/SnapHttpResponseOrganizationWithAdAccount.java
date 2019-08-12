package snap.api.model.organization;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.model.SnapHttpResponse;

/**
 * SnapHttpResponseOrganizationWithAdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapHttpResponseOrganizationWithAdAccount extends SnapHttpResponse {

  private List<SnapInnerOrganizationsWithAdAccount> organizations;

  public List<OrganizationWithAdAccount> getAllOrganizations() {
    return organizations.stream().map(org -> org.getOrganization()).collect(Collectors.toList());
  } // getAllOrganizations()
} // SnapHttpResponseOrganization
