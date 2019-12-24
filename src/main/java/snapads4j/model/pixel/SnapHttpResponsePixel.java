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
package snapads4j.model.pixel;

import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import snapads4j.model.SnapHttpResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
public class SnapHttpResponsePixel extends SnapHttpResponse {

    private List<SnapInnerPixel> pixels;

    public List<Pixel> getAllPixels() {
        return pixels.stream().map(SnapInnerPixel::getPixel).collect(Collectors.toList());
    } // getAllPixels()

    public Optional<Pixel> getPixel() {
        return CollectionUtils.isNotEmpty(pixels) && pixels.get(0) != null ? Optional.of(pixels.get(0).getPixel())
                : Optional.empty();
    } // getPixel()

}// SnapHttpResponsePixel
