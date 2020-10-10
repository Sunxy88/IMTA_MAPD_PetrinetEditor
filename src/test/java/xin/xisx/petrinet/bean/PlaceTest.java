package xin.xisx.petrinet.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xin.xisx.petrinet.common.exception.NotEnoughCoinException;

import static org.junit.Assert.*;

public class PlaceTest {

    private Place place;

    @Before
    public void setUp() {
        place = new Place();
        place.setCoin(3);
    }

    @Test
    public void addCoinTest() {
        place.addCoin(3);
        Assert.assertEquals(6L, (long)place.getCoin());

        place.addCoin(-6);
        Assert.assertEquals(0L, (long)place.getCoin());

        try {
            place.addCoin(-1);
            fail("NotEnoughCoinException is not thrown as expected");
        } catch (Exception e) {
            assertTrue(e instanceof NotEnoughCoinException);
        }
    }

    @Test
    public void subCoinTest() {
        place.subCoin(2);
        Assert.assertEquals(1L, (long)place.getCoin());

        try {
            place.subCoin(2);
            fail("NotEnoughCoinException is not thrown as expected");
        } catch (Exception e) {
            assertTrue(e instanceof NotEnoughCoinException);
        }
    }

    @Test
    public void equalsTest() {
        Place place1 = new Place(1);
        Place place2 = new Place(1);
        Place place3 = new Place(2);

        Assert.assertNotEquals(place1, place2);
        Assert.assertNotEquals(place1, place3);
    }
}