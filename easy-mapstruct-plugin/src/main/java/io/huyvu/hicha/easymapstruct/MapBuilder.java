package io.huyvu.hicha.easymapstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MapBuilder {
    public int sourceIndex;
    public String targetType;

    public final List<KeyMap> keyMapList;
    public final String instanceId;
    public String sourceType;

    public MapBuilder() {
        instanceId = "mbi_" + UUID.randomUUID();
        this.keyMapList = new ArrayList<>();
    }
}
