package xin.xisx.petrinet.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xin.xisx.petrinet.bean.Arc;
import xin.xisx.petrinet.bean.Place;
import xin.xisx.petrinet.bean.Transition;
import xin.xisx.petrinet.common.exception.IllegalStatusException;

import static org.junit.Assert.fail;

/**
 * @author Xi Song
 */
public class ArcTest {

    private Place p1;
    private Transition t1;

    @Before
    public void setUp() {
        p1 = new Place();
        p1.setCoin(3);
        t1 = new Transition();
    }

    @Test
    public void isTriggerableTest() {
        Arc arc = new Arc();
        Assert.assertFalse(arc.isTriggerable());

        arc.attachArc(p1, t1, false);
        Assert.assertTrue(arc.isTriggerable());

        arc.detach();
        arc.setWeight(3);
        arc.attachArc(p1, t1, true);
        Assert.assertTrue(arc.isTriggerable());

        arc.setWeight(4);
        Assert.assertFalse(arc.isTriggerable());
    }

    @Test
    public void addCoinsTest() {
        Arc arc = new Arc();
        try {
            arc.addCoins();
            fail("IllegalStatusException is not thrown as expected");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStatusException);
        }

        arc.attachArc(p1, t1, false);
        arc.setWeight(3);
        arc.addCoins();
        Assert.assertEquals(p1.getCoin(), Integer.valueOf(6));

        arc.detach();
        arc.attachArc(p1, t1, true);
        arc.setWeight(3);
        arc.addCoins();
        Assert.assertEquals(p1.getCoin(), Integer.valueOf(6));
    }

}
