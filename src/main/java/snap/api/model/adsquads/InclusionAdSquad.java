package snap.api.model.adsquads;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * InclusionAdSquad
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class InclusionAdSquad {

  @JsonProperty("content_types")
  private List<String> contentTypes;
} // InclusionAdSquad
