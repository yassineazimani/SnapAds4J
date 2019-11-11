package snap.api.model.ads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.model.ads.Ad;

/**
 * SnapInnerAdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "sub_request_status" })
public class SnapInnerAd {

    private Ad ad;

}// SnapInnerAd
