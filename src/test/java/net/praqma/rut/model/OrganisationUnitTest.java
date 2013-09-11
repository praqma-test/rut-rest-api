package net.praqma.rut.model;

import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author cwolfgang
 */
public class OrganisationUnitTest {

    @Test
    public void constructor1() {
        Organisation o = new Organisation( "KB" );
        assertNotNull( o );
    }

    @Test
    public void constructor2() {
        Organisation o = Organisation.create( "KB" );
        assertNotNull( o );
    }

    @Test
    public void name() {
        Organisation o = new Organisation( "KB" );
        assertNotNull( o );
        assertThat( o.getName(), is( "KB" ) );
    }

    @Test
    public void addDistrict() {
        Organisation o = new Organisation( "KB" );
        assertNotNull( o );
        assertThat( o.getDistricts().size(), is( 0 ) );
        District d = Mockito.mock( District.class );
        o.addDistrict( d );
        assertThat( o.getDistricts().size(), is( 1 ) );
    }
}
