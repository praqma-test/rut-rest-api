package net.praqma.rut.restapi;

import net.praqma.rut.Rut;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author cwolfgang
 */
@Path( "/bootstrap" )
public class BootstrapTests {

    @GET
    @Path( "/clean" )
    public Response basic1() {
        Rut.getInstance().clean();
        return Response.ok().build();
    }
}
