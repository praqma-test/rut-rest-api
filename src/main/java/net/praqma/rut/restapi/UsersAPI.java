package net.praqma.rut.restapi;

import com.google.gson.Gson;
import net.praqma.rut.Rut;
import net.praqma.rut.model.Permission;
import net.praqma.rut.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * The REST API for handling {@link net.praqma.rut.model.User}.
 *
 * @author cwolfgang
 */
@Path( "/user/{username}" )
public class UsersAPI {

    private static Logger logger = Logger.getLogger( UsersAPI.class.getName() );

    @PUT
    public Response createUser( @PathParam( "username" ) String username, @CookieParam( "session" ) String session ) {

        logger.info( "Creating the user " + username );

        User user = Rut.getInstance().authenticate( session );
        if( !Rut.getInstance().isAllowed( user, Rut.getInstance(), Permission.WRITE ) ) {
            return Response.status( Response.Status.UNAUTHORIZED ).entity( user + " is not allowed to create users" ).build();
        }

        if( username == null || username.isEmpty() ) {
            return Response.status( Response.Status.BAD_REQUEST ).entity( "Username not provided" ).build();
        } else {
            // TODO verify that the user does not exist
            User newUser = User.create( username );
            Rut.getInstance().getUsers().put( username, newUser );
            return Response.status( 201 ).entity( "/user/" + username ).build();
        }
    }

    @GET
    @Produces( { MediaType.APPLICATION_JSON + ";charset=UTF-8" })
    public Response getUser( @PathParam( "username" ) String username ) {
        User user = Rut.getInstance().getUsers().get( username );
        if( user != null ) {
            Gson gson = new Gson();
            String json = gson.toJson( user );
            return Response.ok().entity( json ).build();
        } else {
            return Response.noContent().build();
        }
    }
}
