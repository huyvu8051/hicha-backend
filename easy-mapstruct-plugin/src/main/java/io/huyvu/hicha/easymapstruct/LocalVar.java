package io.huyvu.hicha.easymapstruct;

class LocalVar {
    final int index;
    final String type;
    final String varName;

    LocalVar(int index, String type, String varName, int lineNumber) {
        this.index = index;
        this.type = type;
        this.varName = varName;
    }

    @Override
    public String toString() {
        if (varName.startsWith("mbi_")) {
            return varName.substring(0, 10);
        }
        return varName;
    }
}