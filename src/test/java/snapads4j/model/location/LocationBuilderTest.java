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
package snapads4j.model.location;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import snapads4j.enums.OperationEnum;
import snapads4j.enums.ProximityUnitEnum;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LocationBuilderTest {

    @Test
    public void test_location_builder_1() {
        Location location = new Location.Builder().build();
        Assertions.assertThat(location).isNotNull();
    }// test_location_builder_1()

    @Test
    public void test_location_builder_2() {
        Location location = new Location.Builder().setOperation(OperationEnum.EXCLUDE).build();
        Assertions.assertThat(location.getOperation()).isEqualTo(OperationEnum.EXCLUDE);
    }// test_location_builder_2()

    @Test
    public void test_location_builder_3() {
        Location location = new Location.Builder().setProximity(18).build();
        Assertions.assertThat(location.getProximity()).isEqualTo(18);
    }// test_location_builder_2()

    @Test
    public void test_location_builder_4() {
        Location location = new Location.Builder().setUnit(ProximityUnitEnum.KILOMETERS).build();
        Assertions.assertThat(location.getProximityUnit()).isEqualTo(ProximityUnitEnum.KILOMETERS);
    }// test_location_builder_4()

    @Test
    public void test_location_builder_5() {
        List<String> locationTypes = new ArrayList<>();
        locationTypes.add("country");
        Location location = new Location.Builder().setLocationTypes(locationTypes).build();
        Assertions.assertThat(location.getLocationTypes()).isNotNull();
        Assertions.assertThat(location.getLocationTypes()).isNotEmpty();
        Assertions.assertThat(location.getLocationTypes().size()).isEqualTo(1);
        Assertions.assertThat(location.getLocationTypes().get(0)).isEqualTo("country");
    }// test_location_builder_5()

    @Test
    public void test_location_builder_6() {
        List<Circle> circles = new ArrayList<>();
        circles.add(new Circle(45.33, 8.99, 14));
        circles.add(new Circle(48.33, 7.99, 12, ProximityUnitEnum.METERS));
        Circle c3 = new Circle(48., 9., 2);
        c3.setUnit(ProximityUnitEnum.MILES);
        circles.add(c3);
        Location location = new Location.Builder().setCircles(circles).build();
        Assertions.assertThat(location.getCircles()).isNotNull();
        Assertions.assertThat(location.getCircles()).isNotEmpty();
        Assertions.assertThat(location.getCircles().size()).isEqualTo(3);
        Assertions.assertThat(location.getCircles().get(0).getLatitude()).isEqualTo(45.33);
        Assertions.assertThat(location.getCircles().get(0).getLongitude()).isEqualTo(8.99);
        Assertions.assertThat(location.getCircles().get(0).getRadius()).isEqualTo(14);
        Assertions.assertThat(location.getCircles().get(0).getUnit()).isNull();
        Assertions.assertThat(location.getCircles().get(1).getLatitude()).isEqualTo(48.33);
        Assertions.assertThat(location.getCircles().get(1).getLongitude()).isEqualTo(7.99);
        Assertions.assertThat(location.getCircles().get(1).getRadius()).isEqualTo(12);
        Assertions.assertThat(location.getCircles().get(1).getUnit()).isEqualTo(ProximityUnitEnum.METERS);
        Assertions.assertThat(location.getCircles().get(0).toString()).isNotNull();
        Assertions.assertThat(location.getCircles().get(1).toString()).isNotNull();
        Assertions.assertThat(location.getCircles().get(2).toString()).isNotNull();
        Assertions.assertThat(location.getCircles().get(2).getUnit()).isEqualTo(ProximityUnitEnum.MILES);
        Assertions.assertThat(location.toString()).isNotNull();
    }// test_location_builder_6()

}// LocationBuilderTest
