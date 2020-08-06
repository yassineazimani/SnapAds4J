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
package snapads4j.model.device;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DeviceBuilderTest {

    @Test
    public void test_demographics_builder_1() {
        Device demo = new Device.Builder().setConnectionType("CELL").build();
        Assertions.assertThat(demo).isNotNull();
        Assertions.assertThat(demo.getConnectionType()).isEqualTo("CELL");
    }// test_demographics_builder_1()

    @Test
    public void test_demographics_builder_2() {
        Device device = new Device.Builder().setId(1L).build();
        Assertions.assertThat(device).isNotNull();
        Assertions.assertThat(device.getId()).isEqualTo(1L);
    }// test_demographics_builder_2()

    @Test
    public void test_demographics_builder_3() {
        List<String> carrierIds = new ArrayList<>();
        carrierIds.add("US_ATT");
        Device device = new Device.Builder().setCarrierId(carrierIds).build();
        Assertions.assertThat(device).isNotNull();
        Assertions.assertThat(device.getCarrierIds()).isNotNull();
        Assertions.assertThat(device.getCarrierIds()).isNotEmpty();
        Assertions.assertThat(device.getCarrierIds().get(0)).isEqualTo("US_ATT");
    }// test_demographics_builder_3()

    @Test
    public void test_demographics_builder_4() {
        List<String> marketingNames = new ArrayList<>();
        marketingNames.add("Apple/iPhone 7 Plus");
        Device device = new Device.Builder().setMake(marketingNames).build();
        Assertions.assertThat(device).isNotNull();
        Assertions.assertThat(device.getMarketingNames()).isNotNull();
        Assertions.assertThat(device.getMarketingNames()).isNotEmpty();
        Assertions.assertThat(device.getMarketingNames().get(0)).isEqualTo("Apple/iPhone 7 Plus");
    }// test_demographics_builder_4()

    @Test
    public void test_demographics_builder_5() {
        Device device = new Device.Builder().setOSMaxVersion(1.2).build();
        Assertions.assertThat(device).isNotNull();
        Assertions.assertThat(device.getOsMaxVersion()).isEqualTo(1.2);
    }// test_demographics_builder_5()

    @Test
    public void test_demographics_builder_6() {
        Device device = new Device.Builder().setOSMinVersion(1.0).build();
        Assertions.assertThat(device).isNotNull();
        Assertions.assertThat(device.getOsMinVersion()).isEqualTo(1.0);
    }// test_demographics_builder_6()

    @Test
    public void test_demographics_builder_7() {
        Device device = new Device.Builder().setOSType("iOS").build();
        Assertions.assertThat(device).isNotNull();
        Assertions.assertThat(device.getOsType()).isEqualTo("iOS");
    }// test_demographics_builder_7()

    public void test_demographics_builder_8() {
        Device device = new Device.Builder().setOSVersion(7.5).build();
        Assertions.assertThat(device).isNotNull();
        Assertions.assertThat(device.getOsVersion()).isEqualTo(7.5);
    }// test_demographics_builder_8()

    @Test
    public void test_demographics_builder_9() {
        Device device = new Device.Builder().setOSType("iOS").build();
        Assertions.assertThat(device).isNotNull();
        Assertions.assertThat(device.toString()).isNotEmpty();
    }// test_demographics_builder_9()
}// DeviceBuilderTest
