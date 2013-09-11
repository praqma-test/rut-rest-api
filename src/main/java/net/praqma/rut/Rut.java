package net.praqma.rut;

import net.praqma.rut.model.*;
import net.praqma.rut.restapi.Route;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author cwolfgang
 */
public class Rut extends AccessControlled {

    private static Logger logger = Logger.getLogger( Rut.class.getName() );

    private Map<String, User> users = new HashMap<String, User>(  );

    private Map<String, Organisation> organisations = new HashMap<String, Organisation>(  );

    private Map<String, Area> areas = new HashMap<String, Area>(  );

    private Set<Route> routes = new HashSet<Route>(  ); // FIXME Not correct

    private Map<String, User> sessions = new HashMap<String, User>(  );

    private User administrator;
    private User guest;

    private static Rut instance;

    public Rut() {
        if( instance != null ) {
            throw new IllegalStateException( "Only one instance allowed" );
        }

        instance = this;
    }

    public static Rut getInstance() {
        return instance;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator( User administrator ) {
        this.administrator = administrator;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest( User guest ) {
        this.guest = guest;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Organisation> getOrganisations() {
        return organisations;
    }

    public Map<String, Area> getAreas() {
        return areas;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public boolean isAllowed( User user, AccessControlled object, Permission permission ) {
        logger.info( "ADMIN IS " + administrator );
        if( administrator.equals( user ) ) {
            return true;
        } else {
            return object.hasAccess( user, permission );
        }
    }

    public User authenticate( String session ) {
        if( sessions.containsKey( session ) ) {
            return users.get( session );
        } else {
            return guest;
        }
    }

    public String login( String username, String password ) {
        if( users.containsKey( username ) ) {
            /* TODO Hash password */
            if( users.get( username ).getHashPassword().equals( password ) ) {
                /* TODO A better value is needed */
                sessions.put( username, users.get( username ) );
                return username; /* TODO Should be the session ID */
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void clean() {
        instance = null;
    }

    public Rut initialize() {
        /* Admin */
        User admin = new User( "admin", "pass" ).setActive( true );
        users.put( "admin", admin );
        setAdministrator( admin );

        /* Guest */
        User guest = new User( "guest", "pass" ).setActive( true );
        users.put( "guest", guest );
        setGuest( guest );

        return this;
    }
}
