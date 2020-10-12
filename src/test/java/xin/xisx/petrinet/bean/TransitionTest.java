package xin.xisx.petrinet.bean;

import static org.junit.Assert.fail;




import org.junit.Assert;
import org.junit.Test;

import xin.xisx.petrinet.common.exception.IllegalIndexException;
import xin.xisx.petrinet.common.exception.NotEnoughCoinException;

public class TransitionTest {
	
	
	
	@Test
	public void addArcTest() {
		Arc arc1 = new Arc();
		Arc arc2 = new Arc();
		Transition t1 = new Transition("new");
		t1.addArc(arc1);
		t1.addArc(arc2);
		Assert.assertTrue(t1.getArcList().get(0)==arc1);
		Assert.assertTrue(t1.getArcList().get(1)==arc2);
	}
	
	@Test
	public void removeArcTest() {
		Arc arc1 = new Arc();
		Arc arc2 = new Arc();
		Transition t1 = new Transition("new");
		t1.addArc(arc1);
		t1.addArc(arc2);
		
		try {
            t1.removeArc(-1);
            fail("Illegal index, index should be in the range of list");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalIndexException);
        }
		
		try {
            t1.removeArc(3);
            fail("Illegal index, index should be in the range of list");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalIndexException);
        }
		
		t1.removeArc(1);
		t1.removeArc(0);
		
		Assert.assertTrue(t1.getArcList().size()==1);
		Assert.assertTrue(t1.getArcList().size()==0);
	}
	
	@Test
	public void isTriggerableTest() {
		Arc arc1 = new Arc();
		Arc arc2 = new Arc();
		Transition t1 = new Transition("new");
		t1.addArc(arc1);
		t1.addArc(arc2);
		
		try {
            t1.isTriggerable();
            fail("This transition is not triggerable");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotEnoughCoinException);
        }
		
		Place p1 = new Place();
		Place p2 = new Place();
		arc1.attachArc(p1, t1, false);
		arc2.attachArc(p2, t1, false);
		Assert.assertTrue(t1.isTriggerable());
	}
	
	@Test
	public void equalsTest() {
		Transition t1 = new Transition("new");
		Transition t2 = new Transition("new");
		Transition t3 = new Transition("old");
		Assert.assertTrue(t1.equals(t2));
		Assert.assertFalse(t1.equals(null));
		Assert.assertFalse(t1.equals(t3));
	}
	

}
