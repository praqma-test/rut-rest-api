package net.praqma.rut.restapi;

import net.praqma.rut.Rut;
import net.praqma.rut.model.Permission;
import net.praqma.rut.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * @author cwolfgang
 */
@Path( "/" )
public class Root {

    private static Logger logger = Logger.getLogger( Root.class.getName() );

    @POST
    @Path( "login" )
    public Response login( @FormParam( "username" ) String username, @FormParam( "password" ) String password ) {
        logger.info( "Logging in " + username );
        /* TODO verify the input */

        String session = Rut.getInstance().login( username, password );
        if( session != null ) {
            return Response.ok( "Logged in" ).cookie( new NewCookie( "session", session ) ).build();
        } else {
            return Response.status( Response.Status.BAD_REQUEST ).entity( "Failed to log in" ).build();
        }
    }

    @GET
    @Path( "clean" )
    public Response clean( @CookieParam( "session" ) String session ) {
        logger.info( "Cleaning up" );

        User user = Rut.getInstance().authenticate( session );
        if( !Rut.getInstance().isAllowed( user, Rut.getInstance(), Permission.WRITE ) ) {
            return Response.status( Response.Status.BAD_REQUEST ).entity( user + " is not allowed to create users" ).build();
        }

        Rut.getInstance().clean();
        new Rut().initialize();
        return Response.ok( "Cleaning Rut" ).build();
    }
}
