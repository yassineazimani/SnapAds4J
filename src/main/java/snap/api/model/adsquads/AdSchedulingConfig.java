package snap.api.model.adsquads;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import snap.api.enums.DayEnum;

/**
 * AdSchedulingConfig
 *
 * @author Yassine
 */
public class AdSchedulingConfig {

  @JsonProperty("ad_scheduling_config")
  private Map<DayEnum, HourOfDay> ad_scheduling_config;
} // AdSchedulingConfig
