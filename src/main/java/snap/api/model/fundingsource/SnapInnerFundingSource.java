package snap.api.model.fundingsource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SnapInnerFundingSource
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"sub_request_status"})
public class SnapInnerFundingSource {

  private FundingSource fundingsource;
} // SnapInnerFundingSource
