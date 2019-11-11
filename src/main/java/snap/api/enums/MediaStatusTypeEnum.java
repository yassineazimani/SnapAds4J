package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MediaStatusTypeEnum {
    /** Pending upload */
    @JsonProperty("PENDING_UPLOAD")
    PENDING_UPLOAD,
    /** Ready */
    @JsonProperty("READY")
    READY;
}// MediaStatusTypeEnum
