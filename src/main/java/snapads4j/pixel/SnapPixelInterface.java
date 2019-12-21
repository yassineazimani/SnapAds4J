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
package snapads4j.pixel;

import com.fasterxml.jackson.core.JsonProcessingException;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.pixel.Pixel;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface SnapPixelInterface {

    Optional<Pixel> getSpecificPixelAssociatedByAdAccount(String oAuthAccessToken, String adAccountId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException;

    Optional<Pixel> getSpecificPixel(String oAuthAccessToken, String pixelId) throws SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException;

    Optional<Pixel> updatePixel(String oAuthAccessToken, Pixel pixel)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;

}// SnapPixelInterface
