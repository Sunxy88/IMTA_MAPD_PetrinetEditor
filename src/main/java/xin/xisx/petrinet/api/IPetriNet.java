package xin.xisx.petrinet.api;

import xin.xisx.petrinet.bean.Arc;
import xin.xisx.petrinet.bean.Place;
import xin.xisx.petrinet.bean.Transition;

import java.util.List;

public interface IPetriNet {

    /**
     * Add an arc to this net.
     */
    void addArc(Arc arc);

    /**
     * Add a transition to this net.
     */
    void addTransition(Transition transition);

    /**
     * Add a place to this net.
     */
    void addPlace(Place place);

    /**
     * Set the number of coin of a place
     * @param index The index of the place
     * @param coin The coins
     */
    void setCoinOfPlace(Integer index, Integer coin);

    /**
     * Set the weight of an arc
     * @param index The index of the arc
     * @param weight The weight
     */
    void setWeightOfArc(Integer index, Integer weight);

    /**
     * Set the start and the destination of an arc and corresponding direction
     * @param place
     * @param transition
     * @param p2t Indicate the direction of this arc. If it is true, it means that the destination is transition.
     */
    void attachArc(Integer index, Place place, Transition transition, Boolean p2t);

    /**
     * Set the place of an arc and corresponding direction
     * @param place
     * @param p2t Set the start and the destination of an arc and corresponding direction
     */
    void attachArc(Integer index, Place place, Boolean p2t);

    /**
     * Set the transition of an arc and corresponding direction
     * @param transition
     * @param p2t Set the start and the destination of an arc and corresponding direction
     */
    void attachArc(Integer index, Transition transition, Boolean p2t);

    /**
     * Remove a place from the net
     * @param index The index of the place
     */
    void removePlace(Integer index);

    /**
     * Remove a transition from the net
     * @param integer The index of the transition
     */
    void removeTransition(Integer integer);

    /**
     * Remove an arc from the net
     * @param index The index of the arc
     */
    void removeArc(Integer index);

    /**
     * Trigger one transition.
     * @param index The index of the transition.
     */
    void trigger(Integer index);

    /**
     * Trigger transitions in order.
     * @param transitionList The sequence of transition.
     */
    void trigger(List<Transition> transitionList);

    /**
     * Print out this net in command line.
     */
    void showNet();
}
