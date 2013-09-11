package net.praqma.rut.restapi;

import net.praqma.rut.Rut;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author cwolfgang
 */
public class RutRestRule implements TestRule {

    private static Logger logger = Logger.getLogger( RutRestRule.class.getName() );

    private String protocol = "http";
    private String domain = "localhost";
    private String port = "8080";
    private String path = "/rrapi/rest";
    private String address = "";

    private Rut rut;

    private HttpClient client;

    public RutRestRule() {

        /* If the entire address is provided */
        String optionAddress = System.getProperty( "address", "" );
        if( optionAddress.isEmpty() ) {

            /* If the port is provided */
            String optionPort = System.getProperty( "port", "" );
            if( !optionPort.isEmpty() ) {
                port = optionPort;
            }

            /* If the domain is provided */
            String optionDomain = System.getProperty( "domain", "" );
            if( !optionDomain.isEmpty() ) {
                domain = optionDomain;
            }

            address = protocol + "://" + domain + ":" + port + path;
        } else {
            address = optionAddress;
        }

        client = new DefaultHttpClient(  );
    }

    public String getAddress() {
        return address;
    }

    public HttpClient getClient() {
        return client;
    }

    public HttpResponse issueGet( String subPath ) throws IOException {
        HttpGet request = new HttpGet( address + subPath );
        return client.execute( request );
    }

    public HttpResponse issuePost( String subPath, List<NameValuePair> urlParameters ) throws IOException {
        HttpPost request = new HttpPost( address + subPath );
        request.setEntity( new UrlEncodedFormEntity( urlParameters ) );
        return client.execute( request );
    }

    public HttpResponse issuePut( String subPath ) throws IOException {
        HttpPut request = new HttpPut( address + subPath );
        return client.execute( request );
    }

    public Rut getRut() {
        return rut;
    }

    public void print( HttpResponse response ) throws IOException {
        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent() ) );

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
    }

    public void loginAdmin() throws IOException {
        callLogin( "admin", "pass" );
    }

    public void callLogin( String username, String password ) throws IOException {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add( new BasicNameValuePair( "username", username ) );
        urlParameters.add( new BasicNameValuePair( "password", password ) );
        HttpResponse response = issuePost( "/login", urlParameters );

        assertNotNull( response );
        String output = IOUtils.toString( response.getEntity().getContent() );
        assertThat( output, response.getStatusLine().getStatusCode(), is( 200 ) );
    }

    public void clean() throws IOException {
        HttpResponse response = issueGet( "/clean" );
        assertNotNull( response );
        String output = IOUtils.toString( response.getEntity().getContent() );
        assertThat( output, response.getStatusLine().getStatusCode(), is( 200 ) );
    }

    public void createOrganisation( String name ) throws IOException {
        HttpResponse response = issuePut( "/organisation/" + name );
        assertCode( response, 201, "/organisation/" + name );
    }

    public void createDistrict( String oname, String dname ) throws IOException {
        HttpResponse response = issuePut( "/organisation/" + oname + "/district/" + dname );
        assertCode( response, 201, "/organisation/" + oname + "/district/" + dname );
    }

    public void createUser( String name ) throws IOException {
        HttpResponse response = issuePut( "/user/" + name );
        assertCode( response, 201, "/user/" + name );
    }

    public void assertCode( HttpResponse response, int code ) throws IOException {
        assertCode( response, code, null );
    }

    public void assertCode( HttpResponse response, int code, String content ) throws IOException {
        String output = IOUtils.toString( response.getEntity().getContent() );
        EntityUtils.consumeQuietly( response.getEntity() );

        assertNotNull( response );
        assertThat( output, response.getStatusLine().getStatusCode(), is( code ) );
        if( content != null ) {
            assertThat( output, is( content ) );
        }
    }

    public void printContent( HttpResponse response ) throws IOException {
        String output = IOUtils.toString( response.getEntity().getContent() );
        System.out.println( "Content: " + output );
    }

    private void before() throws IOException {
        rut = new Rut().initialize();

        /* Reset at deployed site */
        loginAdmin();
        clean();
    }

    private void after() {
        rut.clean();
    }

    @Override
    public Statement apply( final Statement base, final Description description ) {

        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                Thread t = Thread.currentThread();
                String o = t.getName();
                t.setName( "Executing " + description.getDisplayName() );
                System.out.println( " ===== Setting up Rut REST " + address + " =====" );

                try {
                    before();
                    System.out.println( " ===== Running test: " + description.getDisplayName() + " =====" );
                    base.evaluate();
                } catch( Exception e ) {
                    e.printStackTrace();
                } finally {
                    System.out.println( " ===== Tearing down Rut REST =====" );
                    after();
                    t.setName( o );
                }
            }
        };
    }
}
