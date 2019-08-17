package snap.api.model.campaigns;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

/**
 * SnapHttpResponseCampaign
 *
 * @author Yassine
 */
public class SnapHttpResponseCampaign {

  private List<SnapInnerCampaign> campaigns;

  public Optional<Campaign> getSpecificCampaign() {
    return (CollectionUtils.isNotEmpty(campaigns) && campaigns.get(0) != null)
        ? Optional.of(campaigns.get(0).getCampaign())
        : Optional.empty();
  } // getSpecificCampaign()

  public List<Campaign> getAllCampaigns() {
    return campaigns.stream().map(org -> org.getCampaign()).collect(Collectors.toList());
  } // getAllCampaigns()
} // SnapHttpResponseCampaign
