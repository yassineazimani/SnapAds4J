package snapads4j.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CreativeToCreateEnum
 *
 * @author yassine
 */
public enum CreativeToCreateEnum {
    @JsonProperty("DEFAULT")
    DEFAULT,
    @JsonProperty("LONG_FORM_VIDEO")
    LONG_FORM_VIDEO,
    @JsonProperty("APP_INSTALL")
    APP_INSTALL,
    @JsonProperty("WEB_VIEW")
    WEB_VIEW,
    @JsonProperty("DEEP_LINKS")
    DEEP_LINKS
}// CreativeToCreateEnum
