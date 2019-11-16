package snap.api.model.geolocation;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import snap.api.enums.OperationEnum;

@RunWith(MockitoJUnitRunner.class)
public class GeoLocationBuilderTest {

    private GeolocationBuilder builder;
    
    @Before
    public void init() {
	builder = new GeolocationBuilder();
    }// init()
    
    @Test
    public void test_geolocation_builder_1() {
	builder.setOperation(OperationEnum.EXCLUDE);
	GeoLocation geo = builder.build();
	Assertions.assertThat(geo).isNotNull();
	Assertions.assertThat(geo.getOperation()).isEqualTo(OperationEnum.EXCLUDE);
    }// test_geolocation_builder_1()
    
    @Test
    public void test_geolocation_builder_2() {
	List<Integer> metroIds = new ArrayList<>();
	metroIds.add(1);
	builder.setMetroIds(metroIds);
	GeoLocation geo = builder.build();
	Assertions.assertThat(geo).isNotNull();
	Assertions.assertThat(geo.getMetroIds()).isNotNull();
	Assertions.assertThat(geo.getMetroIds()).isNotEmpty();
	Assertions.assertThat(geo.getMetroIds().get(0)).isEqualTo(1);
    }// test_geolocation_builder_2()
    
    @Test
    public void test_geolocation_builder_3() {
	List<Integer> postalCodes = new ArrayList<>();
	postalCodes.add(13);
	builder.setPostalCodes(postalCodes);
	GeoLocation geo = builder.build();
	Assertions.assertThat(geo).isNotNull();
	Assertions.assertThat(geo.getPostalCodes()).isNotNull();
	Assertions.assertThat(geo.getPostalCodes()).isNotEmpty();
	Assertions.assertThat(geo.getPostalCodes().get(0)).isEqualTo(13);
    }// test_geolocation_builder_3()
    
    @Test
    public void test_geolocation_builder_4() {
	builder.setCountryCode("us");
	GeoLocation geo = builder.build();
	Assertions.assertThat(geo).isNotNull();
	Assertions.assertThat(geo.getCountryCode()).isEqualTo("us");
    }// test_geolocation_builder_4()

    @Test
    public void test_geolocation_builder_5() {
	builder.setOperation(OperationEnum.EXCLUDE);
	GeoLocation geo = builder.build();
	Assertions.assertThat(geo).isNotNull();
	Assertions.assertThat(geo.toString()).isNotEmpty();
    }// test_geolocation_builder_5()
}// GeoLocationBuilderTest
