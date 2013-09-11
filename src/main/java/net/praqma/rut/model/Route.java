package net.praqma.rut.model;

/**
 * @author cwolfgang
 */
public class Route extends AccessControlled {
    private String name;
    private int id;

    private Organisation organisation;
    private Area area;
    private District district;

    public Route( String name, int id ) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation( Organisation organisation ) {
        this.organisation = organisation;
    }

    public Area getArea() {
        return area;
    }

    public void setArea( Area area ) {
        this.area = area;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict( District district ) {
        this.district = district;
    }
}
