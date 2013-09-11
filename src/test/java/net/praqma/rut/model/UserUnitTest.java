package net.praqma.rut.model;

import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author cwolfgang
 */
public class UserUnitTest {

    @Test
    public void constructorTest1() {
        User user = new User( "wolle", "pass" );
        assertNotNull( user );
    }

    @Test
    public void constructorTest2() {
        User user = User.create( "wolle" );
        assertNotNull( user );
    }

    @Test
    public void organisation() {
        User user = new User( "wolle", "pass" );
        assertNotNull( user );
        Organisation o = Mockito.mock( Organisation.class );
        User u2 = user.setOrganisation( o );
        assertNotNull( u2 );
        assertThat( u2, is( user ) );
    }

    @Test
    public void password() {
        User user = User.create( "wolle" );
        assertNotNull( user );
        assertThat( user.getHashPassword(), is( "pass" ) );
    }

    @Test
    public void active() {
        User user = User.create( "wolle" );
        assertNotNull( user );
        assertTrue( user.isActive() );
    }

    @Test
    public void username() {
        User user = User.create( "wolle" );
        assertNotNull( user );
        assertThat( user.getUsername(), is( "wolle" ) );
    }
}
