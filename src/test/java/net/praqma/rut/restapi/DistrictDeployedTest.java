package net.praqma.rut.restapi;

import org.apache.http.HttpResponse;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author cwolfgang
 */
public class DistrictDeployedTest {

    @ClassRule
    public static RutRestRule env = new RutRestRule();

    @Test
    public void create() throws IOException {
        env.loginAdmin();
        env.createOrganisation( "KB" );
        env.createDistrict( "KB", "SouthPark" );

    }
}
