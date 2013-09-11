package net.praqma.rut.restapi;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The REST API for handling a specific route.
 *
 * @author cwolfgang
 */
@Path( "/organisation/{organisation}/area/{area}/district/{district}/route/{route}" )
public class Route {

    private static Logger logger = Logger.getLogger( Route.class.getName() );

    /**
     * Get a JSON array of the roads in the route
     */
    @GET
    @Produces( { MediaType.APPLICATION_JSON + ";charset=UTF-8" })
    @Path( "/roads" )
    public Response createRoute( @PathParam( "organisation" ) String organisation, @PathParam( "area" ) String area, @PathParam( "district" ) String district, @PathParam( "route" ) String route ) {

        System.out.println( "ORGANISATION: " + organisation );
        System.out.println( "AREA        : " + area );
        System.out.println( "DISTRICT    : " + district );
        System.out.println( "ROUTE       : " + route );

        /* TODO call the real object model to get the list of roads from the route */
        List<String> roads = new ArrayList<String>(  );
        roads.add( "Nørregårdsvej" );
        roads.add( "Ejbyvej" );
        roads.add( "Korsdalsvej" );

        /* Get the list as json */
        Gson gson = new Gson();
        String json = gson.toJson( roads );

        return Response.status( 200 ).entity( json ).build();
    }
}
