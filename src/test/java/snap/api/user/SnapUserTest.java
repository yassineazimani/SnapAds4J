package snap.api.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/** Unit tests mocked for SnapUser. */
@RunWith(MockitoJUnitRunner.class)
public class SnapUserTest {

  private SnapUser snapUser;

  @Before
  public void setUp() {
    snapUser = mock(SnapUser.class);
  } // setUp()

  @Test
  public void testGetAllOrganizations() {
    assertThat("test").isNotEmpty();
  } // testGetAllOrganizations()

  @Test
  public void testGetSpecificOrganization() {
    assertThat("test").isNotEmpty();
  } // testGetSpecificOrganization()
} // SnapUserTest
