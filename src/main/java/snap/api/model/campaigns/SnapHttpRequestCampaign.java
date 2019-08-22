package snap.api.model.campaigns;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SnapHttpRequestCampaign {

  private List<Map<String, String>> campaigns;

  public SnapHttpRequestCampaign() {
    this.campaigns = new ArrayList<>();
  } // SnapHttpRequestCampaign()

  public void addCampaign(Map<String, String> campaign) {
    this.campaigns.add(campaign);
  } // addCampaign()
} // SnapHttpRequestAdAccount
