package snap.api.model.adaccount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.model.SnapHttpResponse;

/**
 * SnapHttpResponseAdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
public class SnapHttpResponseAdAccount extends SnapHttpResponse {

  private List<SnapInnerAdAccount> adaccounts;

  public Optional<AdAccount> getSpecificAdAccount() {
    return (CollectionUtils.isNotEmpty(adaccounts) && adaccounts.get(0) != null)
        ? Optional.of(adaccounts.get(0).getAdaccount())
        : Optional.empty();
  } // getSpecificAdAccount()

  public List<AdAccount> getAllAdAccounts() {
    return adaccounts.stream().map(org -> org.getAdaccount()).collect(Collectors.toList());
  } // getAllAdAccounts()
} // SnapHttpResponseAdAccount
