/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.model.demographics;

import java.util.List;

import snapads4j.enums.GenderEnum;

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
    this.demographicsInstance.setMinAge(minAge);
    return this;
  } // setMinAge()

  /**
   * Set max age
   *
   * @param maxAge
   * @return
   */
  public DemographicsBuilder setMaxAge(Double maxAge) {
    this.demographicsInstance.setMaxAge(maxAge);
    return this;
  } // setMaxAge()

  /**
   * Set gender
   *
   * @param gender
   * @return
   */
  public DemographicsBuilder setGender(GenderEnum gender) {
    this.demographicsInstance.setGender(gender);
    return this;
  } // setMaxAge()

  /**
   * Set languages Example : "es", "fr"
   *
   * @param languages
   * @return
   */
  public DemographicsBuilder setLanguages(List<String> languages) {
    this.demographicsInstance.setLanguages(languages);
    return this;
  } // setLanguages()

  /**
   * Set advanced demographics Example : "DLXD_100", "DLXD_300"
   *
   * @param advancedDemographics
   * @return
   */
  public DemographicsBuilder setAdvancedDemographics(List<String> advancedDemographics) {
    this.demographicsInstance.setAdvancedDemographics(advancedDemographics);
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
