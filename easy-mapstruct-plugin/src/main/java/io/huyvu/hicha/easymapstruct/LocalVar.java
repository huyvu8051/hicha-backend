package io.huyvu.hicha.easymapstruct;

class LocalVar {
                final int index;
                final String type;
                final String varName;

                LocalVar(int index, String type, String varName){
                    this.index = index;
                    this.type = type;
                    this.varName = varName;
                }

                @Override
                public String toString() {
                    return varName;
                }
            }