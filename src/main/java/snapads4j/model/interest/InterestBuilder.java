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
package snapads4j.model.interest;

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
