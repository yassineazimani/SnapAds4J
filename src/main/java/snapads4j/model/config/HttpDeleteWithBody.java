package snapads4j.model.config;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HttpDeleteWithBody  extends HttpEntityEnclosingRequestBase{

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
