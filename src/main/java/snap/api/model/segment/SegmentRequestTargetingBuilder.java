package snap.api.model.segment;

import java.util.List;

import snap.api.enums.OperationEnum;

/**
 * Used to build SegmentRequestTargeting instance (${@link SegmentRequestTargeting})
 *
 * @author Yassine
 */
public class SegmentRequestTargetingBuilder {

  private SegmentRequestTargeting segmentInstance;

  public SegmentRequestTargetingBuilder() {
    this.segmentInstance = new SegmentRequestTargeting();
  }

  public SegmentRequestTargetingBuilder setId(Long id) {
    this.segmentInstance.setId(id);
    return this;
  } // setId()

  public SegmentRequestTargetingBuilder setSegmentIds(List<String> segmentIds) {
    this.segmentInstance.setSegmentIds(segmentIds);
    return this;
  } // setSegmentIds()

  public SegmentRequestTargetingBuilder setOperation(OperationEnum op) {
    this.setOperation(op);
    return this;
  } // setOperation()

  public SegmentRequestTargeting build() {
    return this.segmentInstance;
  } // build()
} // SegmentRequestTargetingBuilder
