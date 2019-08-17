package snap.api.model.adaccount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import snap.api.model.SnapHttpResponse;

/**
 * SnapHttpResponseAdAccount
 *
 * @author Yassine
 */
public class SnapHttpResponseAdAccount extends SnapHttpResponse {

  private List<SnapInnerAdAccount> adaccounts;

  public Optional<AdAccount> getSpecificAdAccount() {
    return (CollectionUtils.isNotEmpty(adaccounts) && adaccounts.get(0) != null)
        ? Optional.of(adaccounts.get(0).getAdAccount())
        : Optional.empty();
  } // getSpecificAdAccount()

  public List<AdAccount> getAllAdAccounts() {
    return adaccounts.stream().map(org -> org.getAdAccount()).collect(Collectors.toList());
  } // getAllAdAccounts()
} // SnapHttpResponseAdAccount
