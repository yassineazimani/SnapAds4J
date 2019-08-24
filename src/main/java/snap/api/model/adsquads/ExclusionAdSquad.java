package snap.api.model.adsquads;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ExclusionAdSquad
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class ExclusionAdSquad {

  @JsonProperty("content_types")
  private List<String> contentTypes;
} // ExclusionAdSquad
