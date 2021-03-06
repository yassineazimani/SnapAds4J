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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.OperationEnum;
import snapads4j.model.targeting.Targeting;

import java.util.List;

/**
 * Segment used to build {@link Targeting} instance.
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class SegmentRequestTargeting {

    private Long id;

    /**
     * Segment Ids
     */
    @JsonProperty("segment_id")
    private List<String> segmentIds;

    /**
     * Operation
     */
    private OperationEnum operation;

    /**
     * Used to build SegmentRequestTargeting instance (${@link SegmentRequestTargeting})
     *
     * @author Yassine
     */
    public static class Builder {

        private final SegmentRequestTargeting segmentInstance;

        public Builder() {
            this.segmentInstance = new SegmentRequestTargeting();
        }

        public Builder setId(Long id) {
            this.segmentInstance.setId(id);
            return this;
        } // setId()

        public Builder setSegmentIds(List<String> segmentIds) {
            this.segmentInstance.setSegmentIds(segmentIds);
            return this;
        } // setSegmentIds()

        public Builder setOperation(OperationEnum op) {
            this.segmentInstance.setOperation(op);
            return this;
        } // setOperation()

        public SegmentRequestTargeting build() {
            return this.segmentInstance;
        } // build()
    } // Builder

} // SegmentRequestTargeting
