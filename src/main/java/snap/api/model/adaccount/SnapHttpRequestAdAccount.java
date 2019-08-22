package snap.api.model.adaccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnapHttpRequestAdAccount {

  private List<Map<String, String>> adaccounts;

  public SnapHttpRequestAdAccount() {
    this.adaccounts = new ArrayList<>();
  } // SnapHttpRequestAdAccount()

  public void addAdAccount(Map<String, String> adAccount) {
    this.adaccounts.add(adAccount);
  } // addAdAccount()
} // SnapHttpRequestAdAccount
