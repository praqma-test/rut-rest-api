package net.praqma.rut.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cwolfgang
 */
public class ACL {

    private Map<User, Permission> userPermissions = new HashMap<User, Permission>(  );

    public boolean hasWriteAccess( User user ) {
        if( userPermissions.containsKey( user ) ) {
            return userPermissions.get( user ).has( Permission.WRITE );
        } else {
            return false;
        }
    }

    public boolean hasReadAccess( User user ) {
        if( userPermissions.containsKey( user ) ) {
            return userPermissions.get( user ).has( Permission.WRITE );
        } else {
            return false;
        }
    }

    public boolean allowed( User user, Permission permission ) {
        if( userPermissions.containsKey( user ) ) {
            return userPermissions.get( user ).has( permission );
        } else {
            return false;
        }
    }

    public ACL addReader( User user ) {
        userPermissions.put( user, Permission.READ );
        return this;
    }

    public ACL removeReader( User user ) {
        userPermissions.remove( user );
        return this;
    }

    public ACL addWriter( User user ) {
        userPermissions.put( user, Permission.WRITE );
        return this;
    }

    public ACL removeWriter( User user ) {
        userPermissions.remove( user );
        return this;
    }
}
