package io.huyvu.hicha.easymapstruct;

import java.util.ArrayList;
import java.util.List;

public class MapBuilder {
    public int sourceIndex;
    public String targetType;

    public final List<KeyMap> keyMapList;
    public String instanceId;
    public String sourceType;

    public MapBuilder() {
        this.keyMapList = new ArrayList<>();
    }
}
