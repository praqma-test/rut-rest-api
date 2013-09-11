package net.praqma.rut.model;

/**
 * @author cwolfgang
 */
public abstract class AccessControlled {
    private ACL acl = new ACL();

    public ACL getACL() {
        return acl;
    }

    public boolean hasAccess( User user, Permission permission ) {
        return acl.allowed( user, permission );
    }
}
