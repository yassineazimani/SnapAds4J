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
package snapads4j.model.media;

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

@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SnapHttpResponseMedia extends SnapHttpResponse implements SnapHttpResponsePaging {

    @Getter
    private Paging paging;

    private List<SnapInnerMedia> media;

    public Optional<CreativeMedia> getSpecificMedia() {
        return (CollectionUtils.isNotEmpty(media) && media.get(0) != null) ? Optional.of(media.get(0).getMedia())
                : Optional.empty();
    }// getSpecificMedia()

    public List<CreativeMedia> getAllMedia() {
        return media.stream().map(SnapInnerMedia::getMedia).collect(Collectors.toList());
    }// getAllMedia()

    @Override
    public boolean hasPaging() {
        return paging != null && StringUtils.isNotEmpty(paging.getNextLink());
    }// hasPaging()

}// SnapHttpResponseMedia
