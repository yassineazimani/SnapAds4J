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

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import snapads4j.enums.GenderEnum;

@RunWith(MockitoJUnitRunner.class)
public class DemographicsBuilderTest {

    private DemographicsBuilder builder;
    
    @Before
    public void init() {
	builder = new DemographicsBuilder();
    }// init()
    
    @Test
    public void test_demographics_builder_1() {
	builder.setGender(GenderEnum.MALE);
	Demographics demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.getGender()).isEqualTo(GenderEnum.MALE);
    }// test_demographics_builder_1()
    
    @Test
    public void test_demographics_builder_2() {
	builder.setId(1l);
	Demographics demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.getId()).isEqualTo(1l);
    }// test_demographics_builder_2()
    
    @Test
    public void test_demographics_builder_3() {
	List<String> advancedDemographics = new ArrayList<>();
	advancedDemographics.add("DLXD_100");
	builder.setAdvancedDemographics(advancedDemographics);
	Demographics demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.getAdvancedDemographics()).isNotNull();
	Assertions.assertThat(demo.getAdvancedDemographics()).isNotEmpty();
	Assertions.assertThat(demo.getAdvancedDemographics().get(0)).isEqualTo("DLXD_100");
    }// test_demographics_builder_3()
    
    @Test
    public void test_demographics_builder_4() {
	List<String> ageGroups = new ArrayList<>();
	ageGroups.add("18-27");
	builder.setAgeGroups(ageGroups);
	Demographics demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.getAgeGroups()).isNotNull();
	Assertions.assertThat(demo.getAgeGroups()).isNotEmpty();
	Assertions.assertThat(demo.getAgeGroups().get(0)).isEqualTo("18-27");
    }// test_demographics_builder_4()
    
    @Test
    public void test_demographics_builder_5() {
	List<String> languages  = new ArrayList<>();
	languages.add("en");
	builder.setLanguages(languages);
	Demographics demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.getLanguages()).isNotNull();
	Assertions.assertThat(demo.getLanguages()).isNotEmpty();
	Assertions.assertThat(demo.getLanguages().get(0)).isEqualTo("en");
    }// test_demographics_builder_5()
    
    @Test
    public void test_demographics_builder_6() {
	builder.setMinAge(16.);
	Demographics demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.getMinAge()).isEqualTo(16.);
    }// test_demographics_builder_6()
    
    @Test
    public void test_demographics_builder_7() {
	builder.setMaxAge(28.);
	Demographics demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.getMaxAge()).isEqualTo(28.);
    }// test_demographics_builder_7()
    
    @Test
    public void test_demographics_builder_8() {
	builder.setMaxAge(22.);
	Demographics demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.toString()).isNotEmpty();
    }// test_demographics_builder_8()
}// DemographicsBuilderTest
