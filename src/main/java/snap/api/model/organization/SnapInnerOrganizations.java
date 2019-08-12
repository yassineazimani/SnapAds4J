package snap.api.model.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SnapInnerOrganizations
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"sub_request_status"})
public class SnapInnerOrganizations {

  private Organization organization;
} // SnapInnerOrganizations
