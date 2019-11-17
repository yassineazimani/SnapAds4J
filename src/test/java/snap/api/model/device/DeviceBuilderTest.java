package snap.api.model.device;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceBuilderTest {

    private DeviceBuilder builder;
    
    @Before
    public void init() {
	builder = new DeviceBuilder();
    }// init()
    
    @Test
    public void test_demographics_builder_1() {
	builder.setConnectionType("CELL");
	Device demo = builder.build();
	Assertions.assertThat(demo).isNotNull();
	Assertions.assertThat(demo.getConnectionType()).isEqualTo("CELL");
    }// test_demographics_builder_1()
    
    @Test
    public void test_demographics_builder_2() {
	builder.setId(1l);
	Device device = builder.build();
	Assertions.assertThat(device).isNotNull();
	Assertions.assertThat(device.getId()).isEqualTo(1l);
    }// test_demographics_builder_2()
    
    @Test
    public void test_demographics_builder_3() {
	List<String> carrierIds = new ArrayList<>();
	carrierIds.add("US_ATT");
	builder.setCarrierId(carrierIds);
	Device device = builder.build();
	Assertions.assertThat(device).isNotNull();
	Assertions.assertThat(device.getCarrierIds()).isNotNull();
	Assertions.assertThat(device.getCarrierIds()).isNotEmpty();
	Assertions.assertThat(device.getCarrierIds().get(0)).isEqualTo("US_ATT");
    }// test_demographics_builder_3()
    
    @Test
    public void test_demographics_builder_4() {
	List<String> marketingNames = new ArrayList<>();
	marketingNames.add("Apple/iPhone 7 Plus");
	builder.setMake(marketingNames);
	Device device = builder.build();
	Assertions.assertThat(device).isNotNull();
	Assertions.assertThat(device.getMarketingNames()).isNotNull();
	Assertions.assertThat(device.getMarketingNames()).isNotEmpty();
	Assertions.assertThat(device.getMarketingNames().get(0)).isEqualTo("Apple/iPhone 7 Plus");
    }// test_demographics_builder_4()
    
    @Test
    public void test_demographics_builder_5() {
	builder.setOSMaxVersion(1.2);
	Device device = builder.build();
	Assertions.assertThat(device).isNotNull();
	Assertions.assertThat(device.getOsMaxVersion()).isEqualTo(1.2);
    }// test_demographics_builder_5()
    
    @Test
    public void test_demographics_builder_6() {
	builder.setOSMinVersion(1.0);
	Device device = builder.build();
	Assertions.assertThat(device).isNotNull();
	Assertions.assertThat(device.getOsMinVersion()).isEqualTo(1.0);
    }// test_demographics_builder_6()
    
    @Test
    public void test_demographics_builder_7() {
	builder.setOSType("iOS");
	Device device = builder.build();
	Assertions.assertThat(device).isNotNull();
	Assertions.assertThat(device.getOsType()).isEqualTo("iOS");
    }// test_demographics_builder_7()
    
    public void test_demographics_builder_8() {
	builder.setOSVersion(7.5);
	Device device = builder.build();
	Assertions.assertThat(device).isNotNull();
	Assertions.assertThat(device.getOsVersion()).isEqualTo(7.5);
    }// test_demographics_builder_8()
    
    @Test
    public void test_demographics_builder_9() {
	builder.setOSType("iOS");
	Device device = builder.build();
	Assertions.assertThat(device).isNotNull();
	Assertions.assertThat(device.toString()).isNotEmpty();
    }// test_demographics_builder_9()
}// DeviceBuilderTest
