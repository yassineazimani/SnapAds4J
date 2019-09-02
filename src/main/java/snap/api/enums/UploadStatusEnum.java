package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UploadStatusEnum
 *
 * @author Yassine
 */
public enum UploadStatusEnum {
  /** Segment has been created but no uploads have been received yet */
  @JsonProperty("NO_UPLOAD")
  NO_UPLOAD,
  /** Not all uploads to this segment have been processed so audience size might change */
  @JsonProperty("FIRST_PARTY")
  PROCESSING,
  /** All received uploads have been processed and matched. Audience size reflects segment size */
  @JsonProperty("FIRST_PARTY")
  COMPLETE;
} // UploadStatusEnum
