package net.praqma.rut.model;

/**
 * @author cwolfgang
 */
public class User implements Verifier {
    private String username;
    private String hashPassword;

    private boolean active = true; // TODO should be false

    /**
     * The {@link Organisation} this {@link User} is associate with.
     */
    private Organisation organisation;

    public User( String username, String hashPassword ) {
        this.username = username;
        this.hashPassword = hashPassword;
    }

    public static User create( String username ) {
        return new User( username, "pass" ); // FIXME do something appropriate
    }

    public User setOrganisation( Organisation organisation ) {
        this.organisation = organisation;
        return this;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public boolean isActive() {
        return active;
    }

    public User setActive( boolean active ) {
        this.active = active;
        return this;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals( Object other ) {
        if( other == null ) {
            return false;
        }

        if( other == this ) {
            return true;
        }

        if( other instanceof User ) {
            return (( User )other).username.equals( this.username );
        } else {
            return false;
        }
    }

    @Override
    public boolean verify( User user ) {
        return this.equals( user );
    }

    @Override
    public String toString() {
        return username;
    }
}
