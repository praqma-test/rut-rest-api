package net.praqma.rut.model;

/**
 * @author cwolfgang
 */
public class District extends AccessControlled {
    private String name;
    private int id;
    private int customizedId;

    private Area area;

    public District( String name, int id, int customizedId ) {
        this.name = name;
        this.id = id;
        this.customizedId = customizedId;
    }

    public static District create( String name ) {
        return new District( name, 1, 1 ); // FIXME generate the ids
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCustomizedId() {
        return customizedId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea( Area area ) {
        this.area = area;
    }
}
