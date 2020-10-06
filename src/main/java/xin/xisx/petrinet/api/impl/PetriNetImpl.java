package xin.xisx.petrinet.api.impl;

import xin.xisx.petrinet.api.IPetriNet;
import xin.xisx.petrinet.bean.Arc;
import xin.xisx.petrinet.bean.Place;
import xin.xisx.petrinet.bean.Transition;
import xin.xisx.petrinet.common.exception.IllegalIndexException;
import xin.xisx.petrinet.common.exception.ItemAlreadyExistsException;
import xin.xisx.petrinet.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xi Song
 */
public class PetriNetImpl implements IPetriNet {

    private List<Arc> arcList;
    private List<Place> placeList;
    private List<Transition> transitionList;

    public PetriNetImpl() {
        arcList = new ArrayList<>();
        placeList = new ArrayList<>();
        transitionList = new ArrayList<>();
    }

    /**
     * Add an arc into the net but there should not be two same arcs (same direction).
     * @param arc The arc to be added.
     */
    @Override
    public void addArc(Arc arc) {
        // To avoid the same arc in the net
        if (arc.getPlace() != null && arc.getTransition() != null && arcList.contains(arc)) {
            throw new ItemAlreadyExistsException(Constants.ARC_ALREADY_EXISTS);
        }
        if (arc.getTransition() != null) {
            Transition transition = arc.getTransition();
            transition.addArc(arc);
        }
        arcList.add(arc);
    }

    /**
     * Add a transition into the net but there should be two transition with the same names.
     * @param transition
     */
    @Override
    public void addTransition(Transition transition) {
        // To avoid the same transition in the net
        if (transitionList.contains(transition)) {
            throw new ItemAlreadyExistsException(Constants.TRANSITION_ALREADY_EXISTS);
        }
        transitionList.add(transition);
    }

    /**
     * Add a place into the net but there should be two transition with the same names.
     * @param place
     */
    @Override
    public void addPlace(Place place) {
        if (placeList.contains(place)) {
            throw new ItemAlreadyExistsException(Constants.PLACE_ALREADY_EXISTS);
        }
        placeList.add(place);
    }

    /**
     * Set the coin of indexth place in the net.
     * @param index The index of the place
     * @param coin The coins
     */
    @Override
    public void setCoinOfPlace(Integer index, Integer coin) {
        isIndexLegalPlace(index);
        placeList.get(index).setCoin(coin);
    }

    /**
     * Set the weight of indexth place in the net.
     * @param index The index of the arc
     * @param weight The weight
     */
    @Override
    public void setWeightOfArc(Integer index, Integer weight) {
        isIndexLegalArc(index);
        arcList.get(index).setWeight(weight);
    }

    /**
     * Attach the indexth arc with the given place and transition. Also add this arc into the transition.
     * @param index The index of arc
     * @param place
     * @param transition
     * @param p2t Indicate the direction of this arc. If it is true, it means that the destination is transition.
     */
    @Override
    public void attachArc(Integer index, Place place, Transition transition, Boolean p2t) {
        isIndexLegalArc(index);
        Arc arc = arcList.get(index);
        // This is flag to tell if this arc is already attached with a place and a transition
        boolean bothSideNotNull = true;
        if (place == null) {
            place = arc.getPlace();
            bothSideNotNull = false;
        }
        if (transition == null) {
            transition = arc.getTransition();
            bothSideNotNull = false;
        }
        // To see if there is already an arc with the same place and transition and direction
        Arc tmpArc = new Arc(p2t, 0, place, transition);
        if (bothSideNotNull && arcList.contains(tmpArc)) {
            throw new ItemAlreadyExistsException(Constants.ARC_ALREADY_EXISTS);
        }
        arc.attachArc(place, transition, p2t);
    }

    /**
     * Attach the indexth arc with the given place. Also add this arc into the transition.
     * @param index
     * @param place
     * @param p2t Set the start and the destination of an arc and corresponding direction
     */
    @Override
    public void attachArc(Integer index, Place place, Boolean p2t) {
        attachArc(index, place, null, p2t);
    }

    /**
     * Attach the indexth arc with the given transition. Also add this arc into the transition.
     * @param index
     * @param transition
     * @param p2t Set the start and the destination of an arc and corresponding direction
     */
    @Override
    public void attachArc(Integer index, Transition transition, Boolean p2t) {
        attachArc(index, null, transition, p2t);
    }

    /**
     * Remove the indexth arc from the net.
     * @param index The index of the place
     */
    @Override
    public void removePlace(Integer index) {
        isIndexLegalPlace(index);
        Place place = placeList.get(index);
        placeList.remove(index);
        for (Arc arc : arcList) {
            // Avoid null pointer exception
            if (place.equals(arc.getPlace())) {
                arc.detachPlace();
            }
        }
    }

    /**
     * Remove the indexth transition from the net.
     * @param index
     */
    @Override
    public void removeTransition(Integer index) {
        isIndexLegalTransition(index);
        Transition transition = transitionList.get(index);
        transitionList.remove(index);
        for (Arc arc : arcList) {
            if (transition.equals(arc.getTransition())) {
                arc.detachTransition();
            }
        }
    }

    /**
     * Remove the indexth arc from the net.
     * @param index The index of the arc
     */
    @Override
    public void removeArc(Integer index) {
        isIndexLegalArc(index);
        Arc arc = arcList.get(index);
        arcList.remove(index);
        for (Transition transition : transitionList) {
            if (transition.getArcList().contains(arc)) {
                transition.getArcList().remove(arc);
            }
        }
    }

    /**
     *  Trigger one transition
     * @param index The index of the transition.
     */
    @Override
    public void trigger(Integer index) {
        isIndexLegalTransition(index);
        Transition transition = transitionList.get(index);
        transition.trigger();

    }

    /**
     * Trigger a list of transition one by one
     * @param transitionList The sequence of transition.
     */
    @Override
    public void trigger(List<Transition> transitionList) {
        for (Transition transition : transitionList) {
            transition.trigger();
        }
    }

    /**
     * Print the net in command line.
     */
    @Override
    public void showNet() {
        System.out.println("Petrinet");
        System.out.println("******** Places ********");

        for (Place p : placeList) {
            System.out.println(p);
        }

        System.out.println("******** Transitions ********");
        for (Transition t : transitionList) {
            System.out.println(t);
        }

        System.out.println("******** Arcs ********");
        for (Arc a : arcList) {
            System.out.println(a);
        }
    }

    /**
     * Driver class
     */
    public static void main(String[] args) {
        System.out.println("Welcome to petrinet editor.");
    }

    /**
     * To see if the given index is legal.
     * @param index The give index.
     * @return Returns true if it is legal.
     */
    private boolean isIndexLegalArc(Integer index) {
        if (index < 0 || index >= arcList.size()) {
            throw new IllegalIndexException(arcList.size());
        }
        return true;
    }

    /**
     * To see if the given index is legal.
     * @param index The give index.
     * @return Returns true if it is legal.
     */
    private boolean isIndexLegalPlace(Integer index) {
        if (index < 0 || index >= placeList.size()) {
            throw new IllegalIndexException(placeList.size());
        }
        return true;
    }

    /**
     * To see if the given index is legal.
     * @param index The give index.
     * @return Returns true if it is legal.
     */
    private boolean isIndexLegalTransition(Integer index) {
        if (index < 0 || index >= transitionList.size()) {
            throw new IllegalIndexException(transitionList.size());
        }
        return true;
    }
}
