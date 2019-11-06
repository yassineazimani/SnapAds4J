package snap.api.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

/**
 * Pour contourner Mockito (impossible d'utiliser Powermock)
 * 
 * @author yassine
 *
 */
public class EntityUtilsWrapper {

    public EntityUtilsWrapper() {
    } // EntityUtilsWrapper()

    public String toString(HttpEntity httpEntity) {
	try {
	    return EntityUtils.toString(httpEntity);
	} catch (ParseException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return "";
    }

}// EntityUtilsWrapper
