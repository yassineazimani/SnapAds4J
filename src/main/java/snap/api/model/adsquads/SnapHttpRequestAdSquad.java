package snap.api.model.adsquads;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SnapHttpRequestAdSquad
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class SnapHttpRequestAdSquad {

  private List<AdSquad> adsquads;

  /** Constructor */
  public SnapHttpRequestAdSquad() {
    this.adsquads = new ArrayList<>();
  } // SnapHttpRequestAdSquad

  public void addAdSquad(AdSquad adSquad) {
    this.adsquads.add(adSquad);
  } // addAdSquad()
} // SnapHttpRequestAdSquad
