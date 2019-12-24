package snapads4j.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Date;

/**
 * @author Yassine AZIMANI
 */
public class JsonUtils {

    public static ObjectMapper initMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule dateModule = new SimpleModule();
        dateModule.addDeserializer(Date.class, new CustomDateDeserializer());
        mapper.registerModule(dateModule);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }// initMapper()

}// JsonUtils
