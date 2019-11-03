package snap.api.model.ads;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnapHttpRequestAd {

	private List<Ad> ads;

	  public SnapHttpRequestAd() {
	    this.ads = new ArrayList<>();
	  }// SnapHttpRequestAd()

	  public void addAd(Ad ad) {
	    this.ads.add(ad);
	  }// addAd()
}// SnapHttpRequestAd
