package snapads4j.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PlaybackTypeEnum
 * @author yassine
 *
 */
public enum PlaybackTypeEnum {
    /**
     * AUTO_ADVANCING 
     */
    @JsonProperty("AUTO_ADVANCING ")
    AUTO_ADVANCING, 
    /**
     * SIX_SECONDS
     */
    @JsonProperty("LOOPING")
    LOOPING 
}// PlaybackTypeEnum
