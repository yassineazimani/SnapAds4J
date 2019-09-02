package snap.api.model.segment;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.OperationEnum;
import snap.api.model.targeting.Targeting;

/**
 * Segment used to build {@link Targeting} instance.
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class SegmentRequestTargeting {

  private Long id;

  @JsonProperty("segment_id")
  private List<String> segmentIds;

  private OperationEnum operation;
} // SegmentRequestTargeting
