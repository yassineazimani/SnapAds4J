package snap.api.model.adsquads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.ConfigPlacementEnum;
import snap.api.enums.PlatformPlacementEnum;
import snap.api.enums.SnapChatPositionsEnum;

/**
 * PlacementV2
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class PlacementV2 {

  /** Configuration for placement */
  private ConfigPlacementEnum config;

  /** The platform to place the ads */
  private PlatformPlacementEnum platforms;

  /** List of possible placement positions */
  @JsonProperty("snapchat_positions")
  private SnapChatPositionsEnum snapchatPositions;

  /** Details about the content types to be included */
  private InclusionAdSquad inclusion;

  /** Details about the content types to be included */
  private ExclusionAdSquad exclusion;
} // PlacementV2
