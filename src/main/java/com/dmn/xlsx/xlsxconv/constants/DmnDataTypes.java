package com.dmn.xlsx.xlsxconv.constants;

public enum DmnDataTypes {
    STRING("string"),
    BOOLEAN("boolean"),
    NUMBER("number"),
    DATE("date"),
    TIME("time"),
    DATE_TIME("dateTime"),
    DATE_TIME_DUR("dateTimeDuration"),
    YR_MON_DUR("yearMonthDuration"),
    ANY("Any");

    private final String value;

    DmnDataTypes(String value) {
        this.value = value;
    }

    public static boolean dataTypeExists(String dataType) {
        for (DmnDataTypes dmnDataTypes : DmnDataTypes.values()) {
            if (dmnDataTypes.value().equals(dataType.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public String value() {
        return this.value;
    }
}
