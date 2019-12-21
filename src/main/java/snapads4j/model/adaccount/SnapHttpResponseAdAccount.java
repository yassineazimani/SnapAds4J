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
package snapads4j.model.adaccount;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import snapads4j.model.SnapHttpResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SnapHttpResponseAdAccount
 *
 * @author Yassine
 */
@Setter
@NoArgsConstructor
public class SnapHttpResponseAdAccount extends SnapHttpResponse {

    private List<SnapInnerAdAccount> adaccounts;

    public Optional<AdAccount> getSpecificAdAccount() {
        return (CollectionUtils.isNotEmpty(adaccounts) && adaccounts.get(0) != null)
                ? Optional.of(adaccounts.get(0).getAdaccount())
                : Optional.empty();
    } // getSpecificAdAccount()

    public List<AdAccount> getAllAdAccounts() {
        return adaccounts.stream().map(SnapInnerAdAccount::getAdaccount).collect(Collectors.toList());
    } // getAllAdAccounts()
} // SnapHttpResponseAdAccount
