package net.praqma.rut.restapi;

/**
 * @author cwolfgang
 */

import net.praqma.rut.Rut;
import net.praqma.rut.model.User;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationPath( "/rest" )
public class RestServlet extends Application {

    private static Logger logger = Logger.getLogger( RestServlet.class.getName() );

    public RestServlet() {
        logger.info( "Initializing Rut" );
        Rut rut = new Rut();
        rut.initialize();
        logger.info( "Rut initialized" );
    }

    @Override
    public Set<Class<?>> getClasses( ) {
        final Set<Class<?>> returnValue = new HashSet<Class<?>>( );
        returnValue.add( Root.class );

        returnValue.add( Route.class );

        returnValue.add( OrganisationAPI.class );

        returnValue.add( UsersAPI.class );

        returnValue.add( DistrictAPI.class );

        return returnValue;
    }
}
