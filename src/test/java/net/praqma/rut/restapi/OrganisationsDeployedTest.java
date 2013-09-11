package net.praqma.rut.restapi;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author cwolfgang
 */
public class OrganisationsDeployedTest {

    @Rule
    public static RutRestRule env = new RutRestRule();

    @Test
    public void create() throws IOException {
        env.loginAdmin();
        HttpResponse response = env.issuePut( "/organisation/KB" );

        env.assertCode( response, 201 );
    }

    @Test
    public void createFailed() throws IOException {
        HttpResponse response = env.issuePut( "/organisation/RED" );

        env.assertCode( response, Response.Status.UNAUTHORIZED.getStatusCode() );
    }
}
