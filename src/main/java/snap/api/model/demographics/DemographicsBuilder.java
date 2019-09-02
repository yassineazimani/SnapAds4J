package snap.api.model.demographics;

import java.util.List;

import snap.api.enums.GenderEnum;

/**
 * Used to build Demographics instance (${@link Demographics})
 *
 * @author Yassine
 */
public class DemographicsBuilder {

  private Demographics demographicsInstance;

  /** Constructor */
  public DemographicsBuilder() {
    this.demographicsInstance = new Demographics();
  } // DemographicsBuilder()

  public DemographicsBuilder setId(Long id) {
    this.demographicsInstance.setId(id);
    return this;
  } // setId()

  /**
   * Set Age groups Example : "10-30", "35-45"
   *
   * @param ageGroups
   * @return
   */
  public DemographicsBuilder setAgeGroups(List<String> ageGroups) {
    this.demographicsInstance.setAgeGroups(ageGroups);
    return this;
  } // setAgeGroups()

  /**
   * Set min age
   *
   * @param minAge
   * @return
   */
  public DemographicsBuilder setMinAge(Double minAge) {
    this.setMinAge(minAge);
    return this;
  } // setMinAge()

  /**
   * Set max age
   *
   * @param maxAge
   * @return
   */
  public DemographicsBuilder setMaxAge(Double maxAge) {
    this.setMaxAge(maxAge);
    return this;
  } // setMaxAge()

  /**
   * Set gender
   *
   * @param gender
   * @return
   */
  public DemographicsBuilder setGender(GenderEnum gender) {
    this.setGender(gender);
    return this;
  } // setMaxAge()

  /**
   * Set languages Example : "es", "fr"
   *
   * @param languages
   * @return
   */
  public DemographicsBuilder setLanguages(List<String> languages) {
    this.setLanguages(languages);
    return this;
  } // setLanguages()

  /**
   * Set advanced demographics Example : "DLXD_100", "DLXD_300"
   *
   * @param advancedDemographics
   * @return
   */
  public DemographicsBuilder setAdvancedDemographics(List<String> advancedDemographics) {
    this.setAdvancedDemographics(advancedDemographics);
    return this;
  } // setAdvancedDemographics()

  /**
   * Get Demographics (Request) instance.
   *
   * @return ${@link Demographics}
   */
  public Demographics build() {
    return this.demographicsInstance;
  } // build()
} // DemographicsBuilder
