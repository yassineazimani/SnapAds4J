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
package snapads4j.model.adsquads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import snapads4j.model.Paging;
import snapads4j.model.SnapHttpResponse;
import snapads4j.model.SnapHttpResponsePaging;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SnapHttpResponseAdSquad
 *
 * @author Yassine
 */
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SnapHttpResponseAdSquad extends SnapHttpResponse implements SnapHttpResponsePaging {

    @Getter
    private Paging paging;

    private List<SnapInnerAdSquad> adsquads;

    /**
     * Get the single AdSquad from Array Json
     * @return Optional of {@link AdSquad}
     */
    public Optional<AdSquad> getSpecificAdSquad() {
        return (CollectionUtils.isNotEmpty(adsquads) && adsquads.get(0) != null)
                ? Optional.of(adsquads.get(0).getAdsquad())
                : Optional.empty();
    } // getSpecificAdSquad()

    /**
     * Get all AdSquad from Array Json
     * @return list of {@link AdSquad}
     */
    public List<AdSquad> getAllAdSquads() {
        return adsquads.stream().map(SnapInnerAdSquad::getAdsquad).collect(Collectors.toList());
    } // getAllAdSquads()

    @Override
    public boolean hasPaging() {
        return paging != null && StringUtils.isNotEmpty(paging.getNextLink());
    }
}// SnapHttpResponseAdSquad
