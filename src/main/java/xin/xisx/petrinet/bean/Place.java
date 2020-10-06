package xin.xisx.petrinet.bean;

import xin.xisx.petrinet.common.exception.NotEnoughCoinException;

public class Place {

    private static Integer nums = 0;

    private String name;
    private Integer coin;

    public Place() {
        this("P" + ++nums);
    }

    public Place(String name) {
        this.name = name;
        this.coin = 0;
    }

    public Place(String name, Integer coin) {
        this.name = name;
        this.coin = coin;
    }

    /**
     * Add i coins
     * @param i Number of coins
     */
    public void addCoin(Integer i) {
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

    public Integer getCoin() {
        return coin;
    }

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
