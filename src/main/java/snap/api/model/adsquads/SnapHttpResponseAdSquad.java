package snap.api.model.adsquads;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.model.SnapHttpResponse;

/**
 * SnapHttpResponseAdSquad
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
public class SnapHttpResponseAdSquad extends SnapHttpResponse {

    private List<SnapInnerAdSquad> adsquads;

    public Optional<AdSquad> getSpecificAdSquad() {
	return (CollectionUtils.isNotEmpty(adsquads) && adsquads.get(0) != null)
		? Optional.of(adsquads.get(0).getAdsquad())
		: Optional.empty();
    } // getSpecificAdSquad()

    public List<AdSquad> getAllAdSquads() {
	return adsquads.stream().map(org -> org.getAdsquad()).collect(Collectors.toList());
    } // getAllAdSquads()

}// SnapHttpResponseAdSquad
