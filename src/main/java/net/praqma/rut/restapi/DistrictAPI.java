package net.praqma.rut.restapi;

import com.google.gson.Gson;
import net.praqma.rut.Rut;
import net.praqma.rut.model.District;
import net.praqma.rut.model.Organisation;
import net.praqma.rut.model.Permission;
import net.praqma.rut.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * The REST API for handling {@link net.praqma.rut.model.Organisation}.
 *
 * @author cwolfgang
 */
@Path( "/organisation/{organisation}/district/{district}" )
public class DistrictAPI {

    private static Logger logger = Logger.getLogger( DistrictAPI.class.getName() );

    @PUT
    public Response create( @PathParam( "organisation" ) String organisationName, @PathParam( "district" ) String districtName, @CookieParam( "session" ) String session ) {

        logger.info( "Creating the district " + districtName + " for " + organisationName );

        User user = Rut.getInstance().authenticate( session );
        Organisation organisation = Rut.getInstance().getOrganisations().get( organisationName );

        if( !Rut.getInstance().isAllowed( user, organisation, Permission.WRITE ) ) {
            return Response.status( Response.Status.UNAUTHORIZED ).entity( user + " is not allowed to create districts for " + organisationName ).build();
        }

        if( districtName == null || districtName.isEmpty() ) {
            return Response.status( Response.Status.PRECONDITION_FAILED ).build();
        } else {
            // TODO verify that the organisation does not exist
            District district = District.create( districtName );
            organisation.addDistrict( district );
            return Response.status( 201 ).entity( "/organisation/" + organisationName + "/district/" + districtName ).build();
        }
    }

    /**
     * Add a writer
     */
    @PUT
    @Path( "/writer/{username}" )
    public Response addWriter( @PathParam( "organisation" ) String organisationName, @PathParam( "username" ) String username ) {

        Organisation organisation = Rut.getInstance().getOrganisations().get( organisationName );
        User user = Rut.getInstance().getUsers().get( username );
        organisation.getACL().addWriter( user );

        return Response.ok().build();
    }

    @GET
    @Produces( { MediaType.APPLICATION_JSON + ";charset=UTF-8" })
    @Path( "/writer/{username}" )
    public Response getWriter( @PathParam( "organisation" ) String organisationName, @PathParam( "username" ) String username ) {
        Organisation organisation = Rut.getInstance().getOrganisations().get( organisationName );
        User user = Rut.getInstance().getUsers().get( username );
        if( organisation.hasAccess( user, Permission.WRITE ) ) {
            return Response.ok().entity( new Gson().toJson( user ) ).build();
        } else {
            return Response.noContent().build();
        }
    }
}
