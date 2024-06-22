package com.dmn.xlsx.xlsxconv.dmnconf;

import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.stereotype.Component;

import com.dmn.xlsx.xlsxconv.entity.CsvPojo;
import com.dmn.xlsx.xlsxconv.service.CsvReader;
import com.dmn.xlsx.xlsxconv.service.DmnCreator;
import com.dmn.xlsx.xlsxconv.service.DmnFileExporter;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import jakarta.annotation.PostConstruct;
@Component
public class Dmnconfig {

	@PostConstruct
		public void convertToCSVToDMN(){
				String inputFile="D:/camunda8poccsv2dmn/input/test.csv";
				String outputFile="D:/camunda8poccsv2dmn/output/test.dmn";
				boolean includeCamundaVariables=false;
				convertCsvToDmn(inputFile,outputFile,includeCamundaVariables);
				System.out.println("In postconstuct");
		}
		  void convertCsvToDmn(String inputFile, String outputFile, boolean includeCamundaVariables) {
		        final CsvReader csvReader = new CsvReader(includeCamundaVariables);
		        System.out.println("aftercsvread");
		        final CsvPojo csvPojo = csvReader.readCsv(inputFile);
		        System.out.println("after pojo");
		        final DmnCreator dmnCreator = new DmnCreator(includeCamundaVariables);
		        System.out.println("after dmncre");
		        final DmnModelInstance dmnModelInstance = dmnCreator.createDmnFromCsvPojo(csvPojo);
		        System.out.println("aftermodeinst");
		        final DmnFileExporter dmnFileExporter = new DmnFileExporter();
		        
		        dmnFileExporter.exportToDmnFile(dmnModelInstance, outputFile);
		        System.out.println("afterex[p");
	}
//	DmnModelInstance modelInstance = Dmn.createEmptyModel();
//	
//	 Definitions definitions= modelInstance.newInstance(Definitions.class);
//	 definitions.setNamespace("http://camunda.org/schema/1.0/dmn");
//	 definitions.setName("definitions");
//	 definitions.setId("definitions");
//	 
//	 Decision decision = modelInstance.newInstance(Decision.class);
//	 decision.setId("testGenerated");
//	 decision.setName("generationtest");
//	 definitions.addChildElement(decision);
//	 modelInstance.setDefinitions(definitions);
//	 
//	 Dmn.convertToString(modelInstance);
//	}
//	 //finitions.setNameSpace("ki");
//		 definitions.setNamespace("http://camunda.org/schema/1.0/dmn");
//		 definitions.setName("definitions");
//		 definitions.setId("definitions");
//		 
//	 
//	 Dmn.convertToString(dmnModelInstance);
//	 
//	
//	 definitions.setNamespace("http://camunda.org/schema/1.0/dmn");
//	 definitions.setName("definitions");
//	 definitions.setId("definitions");
//	 modelInstance.setDefinitions(definitions);
	 
//	 Decision decision = modelInstance.newInstance(null, null)
//	 decision.setId("testGenerated");
//	 decision.setName("generationtest");
//	 definitions.addChildElement(decision);

}
