/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.model.segment;

import java.util.List;

import snapads4j.enums.OperationEnum;

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
    this.segmentInstance.setOperation(op);
    return this;
  } // setOperation()

  public SegmentRequestTargeting build() {
    return this.segmentInstance;
  } // build()
} // SegmentRequestTargetingBuilder
