package net.praqma.rut.model;

/**
 * @author cwolfgang
 */
public enum Permission {
    NONE( "None" ),
    READ( "Read" ),
    WRITE( "Write" );

    private String title;
    Permission( String title ) {
        this.title = title;
    }

    public boolean has( Permission permission ) {
        if( permission == null ) {
            return false;
        } else {
            return permission.ordinal() <= this.ordinal();
        }
    }
}
