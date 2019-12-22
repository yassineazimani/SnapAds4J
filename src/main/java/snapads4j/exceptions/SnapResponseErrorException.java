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
package snapads4j.exceptions;

import lombok.Getter;

/**
 * Exception thrown when the endpoint returns a bad status code HTTP.
 *
 * @author Yassine
 */
@Getter
public class SnapResponseErrorException extends Exception {

    private static final long serialVersionUID = 8501189439029665947L;

    /**
     * Status code HTTP
     */
    private final int statusCode;

    /**
     * Constructor
     *
     * @param message    Message exception
     * @param statusCode status code HTTP
     */
    public SnapResponseErrorException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    } // SnapResponseErrorException()
} // SnapResponseErrorException
