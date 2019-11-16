package snap.api.model.segment;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import snap.api.enums.OperationEnum;

@RunWith(MockitoJUnitRunner.class)
public class SegmentRequestTargetingBuilderTest {
    
    private SegmentRequestTargetingBuilder builder;
    
    @Before
    public void init() {
	builder = new SegmentRequestTargetingBuilder();
    }// init()
    
    @Test
    public void test_segment_request_targeting_builder_1() {
	builder.setId(1l);
	SegmentRequestTargeting sTarget = builder.build();
	Assertions.assertThat(sTarget).isNotNull();
	Assertions.assertThat(sTarget.getId()).isEqualTo(1l);
    }// test_segment_request_targeting_builder_1()
    
    @Test
    public void test_segment_request_targeting_builder_2() {
	builder.setOperation(OperationEnum.INCLUDE);
	SegmentRequestTargeting sTarget = builder.build();
	Assertions.assertThat(sTarget).isNotNull();
	Assertions.assertThat(sTarget.getOperation()).isEqualTo(OperationEnum.INCLUDE);
    }// test_segment_request_targeting_builder_2()
    
    @Test
    public void test_segment_request_targeting_builder_3() {
	List<String> segmentsIds = new ArrayList<>();
	segmentsIds.add("segment-1");
	builder.setSegmentIds(segmentsIds);
	SegmentRequestTargeting sTarget = builder.build();
	Assertions.assertThat(sTarget).isNotNull();
	Assertions.assertThat(sTarget.getSegmentIds()).isNotNull();
	Assertions.assertThat(sTarget.getSegmentIds()).isNotEmpty();
	Assertions.assertThat(sTarget.getSegmentIds()).size().isEqualTo(1);
	Assertions.assertThat(sTarget.getSegmentIds().get(0)).isEqualTo(segmentsIds.get(0));
    }// test_segment_request_targeting_builder_3()

    @Test
    public void test_segment_request_targeting_builder_4() {
	builder.setOperation(OperationEnum.INCLUDE);
	SegmentRequestTargeting sTarget = builder.build();
	Assertions.assertThat(sTarget).isNotNull();
	Assertions.assertThat(sTarget.toString()).isNotEmpty();
    }// test_segment_request_targeting_builder_4()
}// SegmentRequestTargetingBuilderTest
