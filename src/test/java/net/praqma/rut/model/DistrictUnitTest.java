package net.praqma.rut.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author cwolfgang
 */
public class DistrictUnitTest {

    @Test
    public void constructor() {
        District d = new District( "South Park", 1, 1 );
        assertNotNull( d );
    }

    @Test
    public void constructor2() {
        District d = District.create( "South Park" );
        assertNotNull( d );
    }

    @Test
    public void name() {
        District d = new District( "South Park", 1, 1 );
        assertNotNull( d );
        assertThat( d.getName(), is( "South Park" ) );
    }

    @Test
    public void getId() {
        District d = new District( "South Park", 1, 1 );
        assertNotNull( d );
        assertThat( d.getId(), is( 1 ) );
    }

    @Test
    public void getCustomizedId() {
        District d = new District( "South Park", 1, 1 );
        assertNotNull( d );
        assertThat( d.getCustomizedId(), is( 1 ) );
    }
}
