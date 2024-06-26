package io.huyvu.hicha.easymapstruct;

public class KeyMap {
    private final String s;
    private final String t;

    public KeyMap(String s, String t) {
        this.s = s;
        this.t = t;
    }

    public String getS() {
        return s;
    }

    public String getT() {
        return t;
    }


    @Override
    public String toString() {
        return "KeyMap{" +
                "'" + s + '\'' +
                ", '" + t + '\'' +
                '}';
    }

}
