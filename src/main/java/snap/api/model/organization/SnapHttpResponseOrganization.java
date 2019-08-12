package snap.api.model.organization;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.model.SnapHttpResponse;

/**
 * SnapHttpResponseOrganization
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapHttpResponseOrganization extends SnapHttpResponse {

  private List<SnapInnerOrganizations> organizations;

  public Optional<Organization> getOrganization() {
    return CollectionUtils.isNotEmpty(organizations) && organizations.get(0) != null
        ? Optional.of(organizations.get(0).getOrganization())
        : Optional.empty();
  } // getOrganization()

  public List<Organization> getAllOrganizations() {
    return organizations.stream().map(org -> org.getOrganization()).collect(Collectors.toList());
  } // getAllOrganizations()
} // SnapHttpResponseOrganization
