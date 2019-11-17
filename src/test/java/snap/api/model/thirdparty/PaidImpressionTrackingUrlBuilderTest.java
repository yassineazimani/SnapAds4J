package snap.api.model.thirdparty;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PaidImpressionTrackingUrlBuilderTest {
    
    private PaidImpressionTrackingUrlBuilder builder;
    
    @Before
    public void init() {
	builder = new PaidImpressionTrackingUrlBuilder();
    }// init()
    
    @Test
    public void test_paid_impression_tracking_url_builder_1() {
	builder.setExpandedTrackingUrl("url");
	PaidImpressionTrackingUrl s = builder.build();
	Assertions.assertThat(s).isNotNull();
	Assertions.assertThat(s.getExpandedTrackingUrl()).isEqualTo("url");
    }// test_paid_impression_tracking_url_builder_1
    
    @Test
    public void test_paid_impression_tracking_url_builder_2() {
	builder.setTrackingUrl("url2");
	PaidImpressionTrackingUrl s = builder.build();
	Assertions.assertThat(s).isNotNull();
	Assertions.assertThat(s.getTrackingUrl()).isEqualTo("url2");
    }// test_paid_impression_tracking_url_builder_2
    
    @Test
    public void test_paid_impression_tracking_url_builder_3() {
	builder.setTrackingUrlMetadata("url3");
	PaidImpressionTrackingUrl s = builder.build();
	Assertions.assertThat(s).isNotNull();
	Assertions.assertThat(s.getTrackingUrlMetadata()).isEqualTo("url3");
    }// test_paid_impression_tracking_url_builder_3
    
    @Test
    public void test_paid_impression_tracking_url_builder_4() {
	builder.setTrackingUrlMetadata("url3");
	PaidImpressionTrackingUrl s = builder.build();
	Assertions.assertThat(s).isNotNull();
	Assertions.assertThat(s.toString()).isNotEmpty();
    }// test_paid_impression_tracking_url_builder_4

}// PaidImpressionTrackingUrlBuilderTest
