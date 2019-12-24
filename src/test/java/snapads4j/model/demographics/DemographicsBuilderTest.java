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

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import snapads4j.enums.GenderEnum;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DemographicsBuilderTest {
    @Test
    public void test_demographics_builder_1() {
        Demographics demo = new Demographics.Builder().setGender(GenderEnum.MALE).build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.getGender()).isEqualTo(GenderEnum.MALE);
    }// test_demographics_builder_1()

    @Test
    public void test_demographics_builder_2() {
        Demographics demo = new Demographics.Builder().setId(1L).build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.getId()).isEqualTo(1L);
    }// test_demographics_builder_2()

    @Test
    public void test_demographics_builder_3() {
        List<String> advancedDemographics = new ArrayList<>();
        advancedDemographics.add("DLXD_100");
        Demographics demo = new Demographics.Builder().setAdvancedDemographics(advancedDemographics).build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.getAdvancedDemographics()).isNotNull();
        Assertions.assertThat(demo.getAdvancedDemographics()).isNotEmpty();
        Assertions.assertThat(demo.getAdvancedDemographics().get(0)).isEqualTo("DLXD_100");
    }// test_demographics_builder_3()

    @Test
    public void test_demographics_builder_4() {
        List<String> ageGroups = new ArrayList<>();
        ageGroups.add("18-27");
        Demographics demo = new Demographics.Builder().setAgeGroups(ageGroups).build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.getAgeGroups()).isNotNull();
        Assertions.assertThat(demo.getAgeGroups()).isNotEmpty();
        Assertions.assertThat(demo.getAgeGroups().get(0)).isEqualTo("18-27");
    }// test_demographics_builder_4()

    @Test
    public void test_demographics_builder_5() {
        List<String> languages = new ArrayList<>();
        languages.add("en");
        Demographics demo = new Demographics.Builder().setLanguages(languages).build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.getLanguages()).isNotNull();
        Assertions.assertThat(demo.getLanguages()).isNotEmpty();
        Assertions.assertThat(demo.getLanguages().get(0)).isEqualTo("en");
    }// test_demographics_builder_5()

    @Test
    public void test_demographics_builder_6() {
        Demographics demo = new Demographics.Builder().setMinAge(16.).build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.getMinAge()).isEqualTo(16.);
    }// test_demographics_builder_6()

    @Test
    public void test_demographics_builder_7() {
        Demographics demo = new Demographics.Builder().setMaxAge(28.).build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.getMaxAge()).isEqualTo(28.);
    }// test_demographics_builder_7()

    @Test
    public void test_demographics_builder_8() {
        Demographics demo = new Demographics.Builder().setMaxAge(22.).build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.toString()).isNotEmpty();
    }// test_demographics_builder_8()
}// DemographicsBuilderTest
