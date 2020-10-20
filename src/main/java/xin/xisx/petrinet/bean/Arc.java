package xin.xisx.petrinet.bean;

import xin.xisx.petrinet.common.exception.IllegalStatusException;
import xin.xisx.petrinet.common.exception.IllegalWeightException;

/**
 * @author Xi Song
 */
public class Arc {

    private static Integer nums = 0;

    private String name;
    private Boolean fromPlace;
    private Integer weight;
    private Place place;
    private Transition transition;

    // Automatically generate name
    public Arc() {
        this("A" + ++nums);
    }

    /**
     * Instantiation with the given name.
     * @param name The name of this instance
     */
    public Arc(String name) {
        this.name = name;
    }

    /**
     * Instantiation with also the attatchment
     * @param fromPlace True if this arc goes from a place to a transition.
     * @param weight The weight of this arc.
     * @param place The place this arc connects.
     * @param transition The transition this arc connects.
     */
    public Arc(Boolean fromPlace, Integer weight, Place place, Transition transition) {
        this();
        if (weight < 0) {
            throw new IllegalWeightException();
        }
        this.fromPlace = fromPlace;
        this.weight = weight;
        this.place = place;
        this.transition = transition;
    }

    /**
     * Attach the arc with the given place and transition.
     * @param place
     * @param transition
     * @param fromPlace
     */
    public void attachArc(Place place, Transition transition, Boolean fromPlace) {
        this.setPlace(place);
        this.setTransition(transition);
        this.setFromPlace(fromPlace);
    }

    /**
     * Detach the arc from place.
     */
    public void detachPlace() {
        setPlace(null);
    }

    /**
     * Detach the arc from transition.
     */
    public void detachTransition() {
        setTransition(null);
    }

    /**
     * Detach the arc from place and transition
     */
    public void detach() {
        detachPlace();
        detachTransition();
    }

    /**
     * Add coins to the attached place if this arc is attached with a place and a transition
     */
    public void addCoins() {
        if (place == null || transition == null) {
            throw new IllegalStatusException();
        }
        if (!fromPlace) {
            place.addCoin(weight);
        }
    }

    /**
     * Remove coins in the place if this arc starts from it.
     * @return
     */
    public Integer checkAndSubCoins() {
        if (!isTriggerable()) {
            return -1;
        }
        if (fromPlace) {
            place.subCoin(weight);
        }
        return weight;
    }

    /**
     * To test if this arc is executable
     * @return
     */
    public Boolean isTriggerable() {
        if (place == null || transition == null) {
            return false;
        }
        if (fromPlace) {
            return place.getCoin() >= weight;
        }
        return true;
    }

    /**
     * To add or remove coins from the place based on the direction
     */
    public void fire() {
        if (!isTriggerable()) {
            throw new IllegalStatusException();
        }
        if (fromPlace) {
            checkAndSubCoins();
        } else {
            addCoins();
        }
    }

    // Getter and setter methods
    public static Integer getNums() {
        return nums;
    }

    public static void setNums(Integer nums) {
        Arc.nums = nums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(Boolean fromPlace) {
        this.fromPlace = fromPlace;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "name='" + name + '\'' +
                ", fromPlace=" + fromPlace +
                ", weight=" + weight +
                ", place=" + place +
                ", transition=" + transition +
                '}';
    }

    /**
     * Use the direction(fromPlace), place and transition as criteria to judge if two Arcs are equal.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Arc arc = (Arc) o;

        if (getFromPlace() != null ? !getFromPlace().equals(arc.getFromPlace()) : arc.getFromPlace() != null) {
            return false;
        }
        if (getPlace() != null ? !getPlace().equals(arc.getPlace()) : arc.getPlace() != null) {
            return false;
        }
        return getTransition() != null ? getTransition().equals(arc.getTransition()) : arc.getTransition() == null;
    }

    @Override
    public int hashCode() {
        int result = getFromPlace() != null ? getFromPlace().hashCode() : 0;
        result = 31 * result + (getPlace() != null ? getPlace().hashCode() : 0);
        result = 31 * result + (getTransition() != null ? getTransition().hashCode() : 0);
        return result;
    }
}
