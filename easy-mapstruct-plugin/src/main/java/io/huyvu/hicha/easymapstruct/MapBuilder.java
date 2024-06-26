package io.huyvu.hicha.easymapstruct;

import java.util.ArrayList;
import java.util.List;

public class MapBuilder {
    public int sourceIndex;
    public int targetIndex;

    public final List<KeyMap> keyMapList;

    public MapBuilder() {
        this.keyMapList = new ArrayList<>();
    }
}
