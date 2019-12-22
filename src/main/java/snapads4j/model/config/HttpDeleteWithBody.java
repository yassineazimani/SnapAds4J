package snapads4j.model.config;

import lombok.NoArgsConstructor;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

@NoArgsConstructor
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

    public static final String METHOD_NAME = "DELETE";

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }// getMethod()

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }// HttpDeleteWithBody()

}// HttpDeleteWithBody
