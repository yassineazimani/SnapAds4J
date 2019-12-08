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
package snapads4j.model.creatives.elements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;
import lombok.Setter;
import snapads4j.model.SnapHttpResponse;

/**
 * SnapHttpResponseCreative
 *
 * @author Yassine
 */
@Setter
@NoArgsConstructor
public class SnapHttpResponseInteractionZone extends SnapHttpResponse {
    
    @JsonProperty("interaction_zones")
    private List<SnapInnerInteractionZone> interactionZones;

    public Optional<InteractionZone> getSpecificInteractionZone() {
      return (CollectionUtils.isNotEmpty(interactionZones) && interactionZones.get(0) != null)
          ? Optional.of(interactionZones.get(0).getInteractionZone())
          : Optional.empty();
    } // getSpecificInteractionZone()

    public List<InteractionZone> getInteractionZones() {
      return interactionZones.stream().map(org -> org.getInteractionZone()).collect(Collectors.toList());
    } // getInteractionZones()
    
}// SnapHttpResponseInteractionZone
