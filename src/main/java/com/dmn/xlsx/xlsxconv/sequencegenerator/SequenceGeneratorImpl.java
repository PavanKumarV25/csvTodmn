package com.dmn.xlsx.xlsxconv.sequencegenerator;

public class SequenceGeneratorImpl implements SequenceGenerator {
    private int value = 1;

    @Override
    public int getNext() {
        return value++;
    }
}
