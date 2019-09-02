package snap.api.model.device;

import java.util.List;

/**
 * Used to build Device instance (${@link Device})
 *
 * @author Yassine
 */
public class DeviceBuilder {

  private Device deviceInstance;

  public DeviceBuilder() {
    this.deviceInstance = new Device();
  } // DeviceBuilder()

  /**
   * Set ID
   *
   * @param id
   * @return
   */
  public DeviceBuilder setId(Long id) {
    this.deviceInstance.setId(id);
    return this;
  } // setId()

  /**
   * Example : "WIFI" or "CELL"
   *
   * @param connectionType
   * @return
   */
  public DeviceBuilder setConnectionType(String connectionType) {
    this.deviceInstance.setConnectionType(connectionType);
    return this;
  } // setConnectionType()

  /**
   * Example : "ANDROID" or "iOS"
   *
   * @param osType
   * @return
   */
  public DeviceBuilder setOSType(String osType) {
    this.deviceInstance.setOsType(osType);
    return this;
  } // setOSType()

  /**
   * Example : 10.3.2
   *
   * @param osVersion
   * @return
   */
  public DeviceBuilder setOSVersion(Double osVersion) {
    this.deviceInstance.setOsVersion(osVersion);
    return this;
  } // setOSVersion()

  /**
   * Example : 10.3.2
   *
   * @param osVersion
   * @return
   */
  public DeviceBuilder setOSMinVersion(Double osVersion) {
    this.deviceInstance.setOsMinVersion(osVersion);
    return this;
  } // setOSMinVersion()

  /**
   * Example : 10.3.2
   *
   * @param osVersion
   * @return
   */
  public DeviceBuilder setOSMaxVersion(Double osVersion) {
    this.deviceInstance.setOsMaxVersion(osVersion);
    return this;
  } // setOSMaxVersion()

  /**
   * Example : "US_ATT"
   *
   * @param carrierIds
   * @return
   */
  public DeviceBuilder setCarrierId(List<String> carrierIds) {
    this.deviceInstance.setCarrierIds(carrierIds);
    return this;
  } // setCarrierId()

  /**
   * Example : "Apple/iPhone 7 Plus/", "Apple/iPhone 6s Plus/"
   *
   * @param marketingNames
   * @return
   */
  public DeviceBuilder setMake(List<String> marketingNames) {
    this.deviceInstance.setMarketingNames(marketingNames);
    return this;
  } // setMake()

  public Device build() {
    return this.deviceInstance;
  } // build()
} // DeviceBuilder
