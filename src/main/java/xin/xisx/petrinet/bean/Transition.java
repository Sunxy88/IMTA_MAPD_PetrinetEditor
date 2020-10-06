package xin.xisx.petrinet.bean;

import xin.xisx.petrinet.common.exception.IllegalIndexException;
import xin.xisx.petrinet.common.exception.NotEnoughCoinException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xi Song
 */
public class Transition {

    private static Integer nums = 0;

    private String name;
    private List<Arc> arcList;

    public Transition() {
        this("T" + ++nums);
    }

    public Transition(String name) {
        this.name = name;
        this.arcList = new ArrayList<>();
    }

    public void addArc(Arc arc) {
        this.arcList.add(arc);
    }

    public void removeArc(Integer index) {
        if (index < 0 || index >= arcList.size()) {
            throw new IllegalIndexException("Illegal index, index should be between" + 0 + " and " + arcList.size());
        }
        arcList.remove(index);
    }

    /**
     * Judge if this transition could be triggered.
     * @return Return true if it is possible, otherwise throw an exception.
     */
    public Boolean isTriggerable() {
        for (Arc arc : arcList) {
            if (!arc.isTriggerable()) {
                throw new NotEnoughCoinException();
            }
        }
        return true;
    }

    /**
     * Trigger this transition.
     * @return Return true if it is possible, otherwise throw an exception.
     */
    public Boolean trigger() {
        isTriggerable();
        for (Arc arc : arcList) {
            arc.fire();
        }
        return true;
    }

    public static Integer getNums() {
        return nums;
    }

    public String getName() {
        return name;
    }

    public List<Arc> getArcList() {
        return arcList;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transition that = (Transition) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
