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
package snapads4j.model.organization;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Setter;
import snapads4j.model.SnapHttpResponse;

/**
 * SnapHttpResponseOrganization
 *
 * @author Yassine
 */
@Setter
public class SnapHttpResponseOrganization extends SnapHttpResponse {

  private List<SnapInnerOrganizations> organizations;

  public Optional<Organization> getOrganization() {
    return CollectionUtils.isNotEmpty(organizations) && organizations.get(0) != null
        ? Optional.of(organizations.get(0).getOrganization())
        : Optional.empty();
  } // getOrganization()

  public List<Organization> getAllOrganizations() {
    return organizations.stream().map(org -> org.getOrganization()).collect(Collectors.toList());
  } // getAllOrganizations()
} // SnapHttpResponseOrganization
