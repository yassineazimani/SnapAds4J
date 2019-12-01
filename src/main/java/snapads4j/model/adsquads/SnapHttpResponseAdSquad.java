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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Setter;
import snapads4j.model.SnapHttpResponse;

/**
 * SnapHttpResponseAdSquad
 *
 * @author Yassine
 */
@Setter
public class SnapHttpResponseAdSquad extends SnapHttpResponse {

    private List<SnapInnerAdSquad> adsquads;

    public Optional<AdSquad> getSpecificAdSquad() {
	return (CollectionUtils.isNotEmpty(adsquads) && adsquads.get(0) != null)
		? Optional.of(adsquads.get(0).getAdsquad())
		: Optional.empty();
    } // getSpecificAdSquad()

    public List<AdSquad> getAllAdSquads() {
	return adsquads.stream().map(org -> org.getAdsquad()).collect(Collectors.toList());
    } // getAllAdSquads()

}// SnapHttpResponseAdSquad
