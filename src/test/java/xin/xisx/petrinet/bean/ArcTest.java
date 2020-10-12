package xin.xisx.petrinet.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xin.xisx.petrinet.bean.Arc;
import xin.xisx.petrinet.bean.Place;
import xin.xisx.petrinet.bean.Transition;
import xin.xisx.petrinet.common.exception.IllegalStatusException;
import xin.xisx.petrinet.common.exception.IllegalWeightException;

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
    public void ArcConstructionTest() {
    	
    	
    	try {
    		 new Arc(true,0,p1,t1);
            fail("Should throw an IllegalWeightException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalWeightException);
        }
    	
    	try {
    		 new Arc(true,-1,p1,t1);
            fail("Should throw an IllegalWeightException");
        } catch (Exception e) {
        	Assert.assertTrue(e instanceof IllegalWeightException);
        }
    	
    }
    
    @Test
    public void attachArcTest() {
    	Arc arc = new Arc();
    	arc.attachArc(p1,t1,true);
    	Assert.assertTrue(arc.getPlace()==p1&&arc.getTransition()==t1&&arc.getFromPlace()==true);
    }
    
    @Test
    public void detachPlaceTest() {
    	Arc arc = new Arc();
    	arc.attachArc(p1,t1,true);
    	arc.detachPlace();
    	Assert.assertTrue(arc.getPlace()==null);
    }
    
    @Test
    public void detachTransitionTest() {
    	Arc arc = new Arc();
    	arc.attachArc(p1,t1,true);
    	arc.detachTransition();
    	Assert.assertTrue(arc.getTransition()==null);
    }
    
    @Test
    public void detachTest() {
    	Arc arc = new Arc();
    	arc.attachArc(p1,t1,true);
    	arc.detach();
    	Assert.assertTrue(arc.getTransition()==null&&arc.getPlace()==null);
    }

    @Test
    public void isTriggerableTest() {
        Arc arc = new Arc();
        Assert.assertFalse(arc.isTriggerable());

        arc.attachArc(p1, t1, false);
        Assert.assertTrue(arc.isTriggerable());
        
        arc.attachArc(p1, null, false);
        Assert.assertFalse(arc.isTriggerable());
        
        arc.attachArc(null, t1, false);
        Assert.assertFalse(arc.isTriggerable());
        
        arc.attachArc(null, null, false);
        Assert.assertFalse(arc.isTriggerable());
        
        

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
        Assert.assertEquals(p1.getCoin(), Integer.valueOf(3));
    }
    
    @Test
    public void checkAndSubCoinsTest() {
        Arc arc = new Arc();
        Assert.assertEquals(arc.checkAndSubCoins(), Integer.valueOf(-1));
        
        arc.attachArc(p1, t1, false);
        arc.setWeight(3);
        arc.checkAndSubCoins();
        Assert.assertEquals(p1.getCoin(), Integer.valueOf(3));

        arc.detach();
        arc.attachArc(p1, t1, true);
        arc.setWeight(3);
        arc.checkAndSubCoins();
        Assert.assertEquals(p1.getCoin(), Integer.valueOf(0));
    }
    
    @Test
    public void fireTest() {
        Arc arc = new Arc();
        try {
            arc.fire();
            fail("Should throw an IllegalStatusException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStatusException);
        }
        
        arc.attachArc(p1, t1, false);
        arc.setWeight(3);
        arc.fire();
        Assert.assertEquals(p1.getCoin(), Integer.valueOf(6));

        arc.detach();
        arc.attachArc(p1, t1, true);
        arc.setWeight(3);
        arc.fire();
        Assert.assertEquals(p1.getCoin(), Integer.valueOf(0));
    }
    
    @Test
    public void equalsTest() {
    	Transition t2 = new Transition();
    	Place p2 = new Place();
    	Arc arc1 = new Arc(false,1,p1,t1);
    	Arc arc2 = new Arc();
    	Arc arc3 = new Arc(true,1,p1,t1);
    	Arc arc4 = new Arc(false,1,p1,t2);
    	Arc arc5 = new Arc(false,1,p2,t1);
       	arc2.attachArc(p1,t1,false);
    	Assert.assertTrue(arc1.equals(arc2));
    	Assert.assertFalse(arc1.equals(arc3));
    	Assert.assertFalse(arc1.equals(null));
    	Assert.assertFalse(arc1.equals(arc4));
    	Assert.assertFalse(arc1.equals(arc5));
    	
    	
    }

}

