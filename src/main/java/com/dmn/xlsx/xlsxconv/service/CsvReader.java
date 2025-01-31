package com.dmn.xlsx.xlsxconv.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.dmn.xlsx.xlsxconv.constants.Converter;
import com.dmn.xlsx.xlsxconv.constants.DmnDataTypes;
import com.dmn.xlsx.xlsxconv.entity.CsvPojo;
import com.dmn.xlsx.xlsxconv.entity.InvalidDatatypeException;
import com.opencsv.CSVReader;

public class CsvReader {
    private int inEnd = 0;
    private int outEnd = 0;
    private boolean includeCamundaVariables;

    public CsvReader(){};
    public CsvReader(boolean includeCamundaVariables){
        this.includeCamundaVariables = includeCamundaVariables;
    };

    public CsvPojo readCsv(String path) {
        CsvPojo csvPojo = new CsvPojo();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            List<String[]> csv = reader.readAll();
            System.out.println(csv.get(0).length);
            firstLineToPojo(csvPojo, csv.get(0));
            setInOutEnd(csv.get(1));
            setColumnName(csvPojo, csv.get(2));
            if (includeCamundaVariables) {
            	System.out.println("in if");
                setColumnVariables(csvPojo, csv.get(3));
                setInOutDataType(csvPojo, csv.get(4));
                readInOutValuesFromCsv(csvPojo, csv.subList(5, csv.size()));
            }else {
            	System.out.println("else");
                setInOutDataType(csvPojo, csv.get(3));
                readInOutValuesFromCsv(csvPojo, csv.subList(4, csv.size()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return csvPojo;
    }

    private void firstLineToPojo(CsvPojo csvPojo, String[] line) {
        csvPojo.setDmnId(line[0]);
        csvPojo.setDmnTitle(line[1]);
        csvPojo.setHitPolicy(line[2]);
    }

    private void setColumnName(CsvPojo csvPojo, String[] columnName) {
        List<String> inColName = new ArrayList<>();
        for (int i = 0; i <= inEnd; i++) {
            inColName.add(columnName[i]);
        }
        csvPojo.setInColName(inColName);

        List<String> outColName = new ArrayList<>();
        for (int i = inEnd + 1; i <= outEnd; i++) {
            outColName.add(columnName[i]);
        }
        csvPojo.setOutColName(outColName);
    }

    private void setColumnVariables(CsvPojo csvPojo, String[] columnName) {
        List<String> inColVariable = new ArrayList<>();
        for (int i = 0; i <= inEnd; i++) {
            inColVariable.add(columnName[i]);
        }
        csvPojo.setInColVariable(inColVariable);

        List<String> outColVariable = new ArrayList<>();
        for (int i = inEnd + 1; i <= outEnd; i++) {
            outColVariable.add(columnName[i]);
        }
        csvPojo.setOutColVariable(outColVariable);
    }

    private void setInOutDataType(CsvPojo csvPojo, String[] line) throws InvalidDatatypeException {
        List<String> inDataType = new ArrayList<>();
        List<String> outDataType = new ArrayList<>();

        for (int i = 0; i <= outEnd; i++) {
            if (DmnDataTypes.dataTypeExists(line[i])) {
                if (i <= inEnd) {
                    inDataType.add(line[i].toLowerCase());
                } else {
                    if (i <= outEnd) {
                        outDataType.add(line[i].toLowerCase());
                    }
                }
            } else {
                throw new InvalidDatatypeException("Wrong datatype " + line[i] + " parsed to converter");
            }
        }
        csvPojo.setInColDataType(inDataType);
        csvPojo.setOutColDataType(outDataType);
    }

    private void setInOutEnd(String[] line) {
        String prevInOut = null;
        for (int i = 0; i < line.length; i++) {
            if (line[i].toLowerCase().equals(Converter.OUTPUT.toLowerCase()) && prevInOut.toLowerCase().equals(Converter.INPUT.toLowerCase())) {
                inEnd = i - 1;
            }
            if (line[i].equals("") && prevInOut.toLowerCase().equals(Converter.OUTPUT.toLowerCase())) {
                outEnd = i - 1;
            }
            prevInOut = line[i];
        }
        if (line[line.length - 1].toLowerCase().equals(Converter.OUTPUT.toLowerCase())) {
            outEnd = line.length - 1;
        }
    }

    private void readInOutValuesFromCsv(CsvPojo csvPojo, List<String[]> values) {
        List<List<String>> inData = new ArrayList<>();
        List<List<String>> outData = new ArrayList<>();
        List<String> annotation = new ArrayList<>();

        for (String[] row : values) {
            List<String> rowIn = new ArrayList<>();
            List<String> rowOut = new ArrayList<>();

            for (int i = 0; i < row.length; i++) {
                if (i <= inEnd) {
                    rowIn.add(row[i]);
                } else {
                    if (i <= outEnd) {
                        rowOut.add(row[i]);
                    }
                }
                if (i > outEnd) {
                    annotation.add(row[i]);
                }
            }

            inData.add(rowIn);
            outData.add(rowOut);
        }

        csvPojo.setInData(inData);
        csvPojo.setOutData(outData);
        csvPojo.setRowAnnotation(annotation);
    }
}
