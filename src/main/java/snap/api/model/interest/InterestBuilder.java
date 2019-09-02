package snap.api.model.interest;

import java.util.List;

/**
 * Used to build Targeting instance
 *
 * @author Yassine
 */
public class InterestBuilder {

  private Interest interestInstance;

  public InterestBuilder() {
    this.interestInstance = new Interest();
  }

  public InterestBuilder setId(Integer id) {
    this.interestInstance.setId(id);
    return this;
  }

  /**
   * Example : "SLC_36", "DLXS_1"
   *
   * @param categoryIds
   * @return
   */
  public InterestBuilder setCategoryIds(List<String> categoryIds) {
    this.interestInstance.setCategoryIds(categoryIds);
    return this;
  } // setCategoryIds()

  public Interest build() {
    return this.interestInstance;
  } // build()
} // InterestBuilder
