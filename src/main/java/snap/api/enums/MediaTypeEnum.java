package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MediaTypeEnum {
    /** Video */
    @JsonProperty("VIDEO")
    VIDEO,
    /** Image */
    @JsonProperty("IMAGE")
    IMAGE,
    /** Lens package */
    @JsonProperty("LENS_PACKAGE")
    LENS_PACKAGE;
}// MediaTypeEnum
