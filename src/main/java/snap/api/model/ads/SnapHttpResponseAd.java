package snap.api.model.ads;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.model.SnapHttpResponse;

@Getter
@Setter
@NoArgsConstructor
public class SnapHttpResponseAd extends SnapHttpResponse{

    private List<SnapInnerAd> ads;

    public Optional<Ad> getSpecificAd() {
	return (CollectionUtils.isNotEmpty(ads) && ads.get(0) != null) ? Optional.of(ads.get(0).getAd())
		: Optional.empty();
    }// getSpecificAd()

    public List<Ad> getAllAd() {
	return ads.stream().map(org -> org.getAd()).collect(Collectors.toList());
    }// getAllAd()
}// SnapHttpResponseAd
