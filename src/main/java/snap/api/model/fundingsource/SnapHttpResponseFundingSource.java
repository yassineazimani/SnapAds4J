package snap.api.model.fundingsource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SnapHttpResponseFundingSource
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
public class SnapHttpResponseFundingSource {

  private List<SnapInnerFundingSource> fundingsources;

  public Optional<FundingSource> getSpecificFundingSource() {
    return (CollectionUtils.isNotEmpty(fundingsources) && fundingsources.get(0) != null)
        ? Optional.of(fundingsources.get(0).getFundingsource())
        : Optional.empty();
  } // getSpecificFundingSource()

  public List<FundingSource> getAllFundingSource() {
    return fundingsources.stream().map(org -> org.getFundingsource()).collect(Collectors.toList());
  } // getAllFundingSource()
} // SnapHttpResponseFundingSource
