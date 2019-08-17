package snap.api.model.campaigns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SnapInnerCampaign
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"sub_request_status"})
public class SnapInnerCampaign {

  private Campaign campaign;
} // SnapInnerCampaign
