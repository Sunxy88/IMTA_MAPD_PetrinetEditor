package xin.xisx.petrinet.api.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xin.xisx.petrinet.api.IPetriNet;
import xin.xisx.petrinet.bean.Arc;
import xin.xisx.petrinet.bean.Place;
import xin.xisx.petrinet.bean.Transition;
import xin.xisx.petrinet.common.exception.IllegalIndexException;
import xin.xisx.petrinet.common.exception.ItemAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Original net :
 * Petrinet
 * ******** Places ********
 * Place{name='P1', coin=1}
 * Place{name='P2', coin=2}
 * Place{name='P3', coin=3}
 * ******** Transitions ********
 * Transition{name='T1'}
 * Transition{name='T2'}
 * Transition{name='T3'}
 * ******** Arcs ********
 * Arc{name='A1', fromPlace=true, weight=1, place=Place{name='P1', coin=1}, transition=Transition{name='T1'}}
 * Arc{name='A2', fromPlace=false, weight=3, place=Place{name='P2', coin=2}, transition=Transition{name='T1'}}
 * Arc{name='A3', fromPlace=true, weight=2, place=Place{name='P2', coin=2}, transition=Transition{name='T2'}}
 * Arc{name='A4', fromPlace=false, weight=2, place=Place{name='P3', coin=3}, transition=Transition{name='T2'}}
 * Arc{name='A5', fromPlace=true, weight=3, place=Place{name='P3', coin=3}, transition=Transition{name='T3'}}
 * Arc{name='A6', fromPlace=false, weight=4, place=Place{name='P3', coin=3}, transition=Transition{name='T3'}}
 */
public class PetriNetImplTest {

    private final int NUM_OF_PLACES = 3, NUM_OF_TRANSITIONS = 3, NUM_OF_ARCS = 5;
    private Place place1, place2, place3;
    private Transition transition1, transition2, transition3;
    private Arc arc1, arc2, arc3, arc4, arc5, arc6;
    private IPetriNet petriNet;

    @Before
    public void setUp() {
        place1 = new Place(1);
        place2 = new Place(2);
        place3 = new Place(3);
        transition1 = new Transition();
        transition2 = new Transition();
        transition3 = new Transition();
        arc1 = new Arc();
        arc2 = new Arc();
        arc3 = new Arc();
        arc4 = new Arc();
        arc5 = new Arc();
        arc6 = new Arc();
        petriNet = new PetriNetImpl();
        petriNet.addArc(arc1);
        petriNet.addArc(arc2);
        petriNet.addArc(arc3);
        petriNet.addArc(arc4);
        petriNet.addArc(arc5);
        petriNet.addArc(arc6);
        petriNet.addPlace(place1);
        petriNet.addPlace(place2);
        petriNet.addPlace(place3);
        petriNet.addTransition(transition1);
        petriNet.addTransition(transition2);
        petriNet.addTransition(transition3);
    }

    @Test
    public void addArc() {
        try {
            petriNet.addArc(arc1);
            petriNet.addArc(arc2);
            petriNet.addArc(arc3);
            petriNet.addArc(arc4);
            petriNet.addArc(arc5);
            petriNet.addArc(arc6);
            petriNet.addArc(arc6);
            fail("Should throw an ItemAlreadyExistsException");
        } catch (Exception e) {
            assertTrue(e instanceof ItemAlreadyExistsException);
        }
    }

    @Test
    public void addTransition() {
        try {
            petriNet.addTransition(transition1);
            petriNet.addTransition(transition2);
            petriNet.addTransition(transition3);
            petriNet.addTransition(transition3);
            fail("Should throw an ItemAlreadyExistsException");
        } catch (Exception e) {
            assertTrue(e instanceof ItemAlreadyExistsException);
        }
    }

    @Test
    public void addPlace() {
        try {
            petriNet.addPlace(place1);
            petriNet.addPlace(place2);
            petriNet.addPlace(place3);
            petriNet.addPlace(place3);
            fail("Should throw an ItemAlreadyExistsException");
        } catch (Exception e) {
            assertTrue(e instanceof ItemAlreadyExistsException);
        }
    }

    @Test
    public void setCoinOfPlace() {
        for (int i = 0; i < NUM_OF_PLACES; i++) {
            petriNet.setCoinOfPlace(i, i + 1);
        }

        try {
            petriNet.setCoinOfPlace(555, 1);
            fail("Should throw an IllegalIndexException");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalIndexException);
        }
    }

    @Test
    public void setWeightOfArc() {
        petriNet.setWeightOfArc(0, 1);
        petriNet.setWeightOfArc(1, 3);
        petriNet.setWeightOfArc(2, 2);
        petriNet.setWeightOfArc(3, 2);
        petriNet.setWeightOfArc(4, 3);
        petriNet.setWeightOfArc(5, 4);

        try {
            petriNet.setWeightOfArc(-1, 100);
            fail("Should throw an IllegalIndexException");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalIndexException);
        }
    }

    @Test
    public void attachArc() {
        petriNet.attachArc(0, place1, transition1, true);
        petriNet.attachArc(1, place2, transition1, false);
        petriNet.attachArc(2, place2, transition2, true);
        petriNet.attachArc(3, place3, transition2, false);
        petriNet.attachArc(4, place3, transition3, true);
        petriNet.attachArc(5, place3, transition3, false);
    }

    @Test
    public void removePlace() {
        petriNet.removePlace(1);
        Assert.assertNull(petriNet.getArc(1).getPlace());
        Assert.assertNull(petriNet.getArc(2).getPlace());
    }

    @Test
    public void removeTransition() {
        petriNet.removeTransition(1);
        Assert.assertNull(petriNet.getArc(2).getTransition());
        Assert.assertNull(petriNet.getArc(3).getTransition());
    }

    @Test
    public void removeArc() {
        petriNet.attachArc(3, place3, transition2, false);
        Assert.assertTrue(transition2.getArcList().contains(arc4));
        petriNet.removeArc(3);
        Assert.assertFalse(transition2.getArcList().contains(arc4));
    }

    @Test
    public void trigger() {
        petriNet.setWeightOfArc(0, 1);
        petriNet.setWeightOfArc(1, 3);
        petriNet.setWeightOfArc(2, 2);
        petriNet.setWeightOfArc(3, 2);
        petriNet.setWeightOfArc(4, 3);
        petriNet.setWeightOfArc(5, 4);
        petriNet.attachArc(0, place1, transition1, true);
        petriNet.attachArc(1, place2, transition1, false);
        petriNet.attachArc(2, place2, transition2, true);
        petriNet.attachArc(3, place3, transition2, false);
        petriNet.attachArc(4, place3, transition3, true);
        petriNet.attachArc(5, place3, transition3, false);

        petriNet.showNet();
        petriNet.trigger(0);
        Assert.assertEquals(0L, (long)petriNet.getPlace(0).getCoin());
        Assert.assertEquals(5L, (long)petriNet.getPlace(1).getCoin());
        petriNet.showNet();
    }

    @Test
    public void testTrigger() {
        petriNet.setWeightOfArc(0, 1);
        petriNet.setWeightOfArc(1, 3);
        petriNet.setWeightOfArc(2, 2);
        petriNet.setWeightOfArc(3, 2);
        petriNet.setWeightOfArc(4, 3);
        petriNet.setWeightOfArc(5, 4);
        petriNet.attachArc(0, place1, transition1, true);
        petriNet.attachArc(1, place2, transition1, false);
        petriNet.attachArc(2, place2, transition2, true);
        petriNet.attachArc(3, place3, transition2, false);
        petriNet.attachArc(4, place3, transition3, true);
        petriNet.attachArc(5, place3, transition3, false);
        petriNet.showNet();

        List<Transition> transitions = new ArrayList<>();
        transitions.add(transition1);
        transitions.add(transition2);
        transitions.add(transition3);
        transitions.add(transition3);
        petriNet.trigger(transitions);
        petriNet.showNet();
        Assert.assertEquals(0L, (long)petriNet.getPlace(0).getCoin());
        Assert.assertEquals(3L, (long)petriNet.getPlace(1).getCoin());
        Assert.assertEquals(7L, (long)petriNet.getPlace(2).getCoin());
    }

    @Test
    public void showNet() {
        petriNet.showNet();
    }
}