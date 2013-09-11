package net.praqma.rut.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cwolfgang
 */
public class Organisation extends AccessControlled {
    private String name;

    private Set<District> districts = new HashSet<District>(  );

    public Organisation( String name ) {
        this.name = name;
    }

    public static Organisation create( String name ) {
        return new Organisation( name );
    }

    public String getName() {
        return name;
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public void addDistrict( District district ) {
        districts.add( district );
    }
}
