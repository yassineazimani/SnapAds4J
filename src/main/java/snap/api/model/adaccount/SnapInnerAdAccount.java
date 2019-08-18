package snap.api.model.adaccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SnapInnerAdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"sub_request_status"})
public class SnapInnerAdAccount {

  private AdAccount adaccount;
} // SnapInnerAdAccount
