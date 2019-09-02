package snap.api.model.segment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.SourceTypeEnum;
import snap.api.enums.StatusEnum;
import snap.api.enums.TargetableStatusEnum;
import snap.api.enums.UploadStatusEnum;

@Getter
@Setter
@ToString
public class Segment {

  private Long id;

  @JsonProperty("ad_account_id")
  private String adAccountId;

  private String description;

  private String name;

  @JsonProperty("retention_in_days")
  private int retention_in_days;

  @JsonProperty("source_type")
  private SourceTypeEnum sourceType;

  @JsonProperty("approximate_number_users")
  private long approximate_number_users;

  private StatusEnum status;

  @JsonProperty("upload_status")
  private UploadStatusEnum uploadStatus;

  @JsonProperty("targetable_status")
  private TargetableStatusEnum targetableStatus;
} // Segment
