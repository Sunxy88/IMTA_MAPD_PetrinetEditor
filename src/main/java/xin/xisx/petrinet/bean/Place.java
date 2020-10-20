package xin.xisx.petrinet.bean;

import xin.xisx.petrinet.common.exception.NotEnoughCoinException;

/**
 * @author Xi Song
 */
public class Place {

    private static Integer nums = 0;

    private String name;
    private Integer coin;

    /**
     * Automatically generate a name for instance and set coin to 0
     */
    public Place() {
        this("P" + ++nums);
    }

    /**
     * Set coin to 0
     * @param name The name of this instance
     */
    public Place(String name) {
        this.name = name;
        this.coin = 0;
    }

    /**
     * Instantiate with given name and the number of coins.
     * @param name The name of this instance.
     * @param coin The number of coins.
     */
    public Place(String name, Integer coin) {
        if (coin < 0) {
            throw new NotEnoughCoinException();
        }
        this.name = name;
        this.coin = coin;
    }

    /**
     * Set coin to the given number
     * @param coin The number of coins.
     */
    public Place(Integer coin) {
        this();
        if (coin < 0) {
            throw new NotEnoughCoinException();
        }
        this.coin = coin;
    }

    /**
     * Add i coins
     * @param i Number of coins
     */
    public void addCoin(Integer i) {
        if (i < 0 && coin + i < 0) {
            throw new NotEnoughCoinException();
        }
        this.setCoin(this.getCoin() + i);
    }

    /**
     * Remove i coins if possible
     * @param i Number of coins
     */
    public void subCoin(Integer i) {
        if (i > coin) {
            throw new NotEnoughCoinException();
        }
        this.addCoin(-i);
    }

    /**
     * Getter of coin
     * @return
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * Setter of coin
     * @param coin
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", coin=" + coin +
                '}';
    }

    /**
     * Only use name as the criteria to judge if two place is the same.
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

        Place place = (Place) o;

        return name != null ? name.equals(place.name) : place.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
