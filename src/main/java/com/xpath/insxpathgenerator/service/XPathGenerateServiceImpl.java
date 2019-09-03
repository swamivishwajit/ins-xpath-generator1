package com.xpath.insxpathgenerator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.xpath.insxpathgenerator.domain.CsvData;

@Service
public class XPathGenerateServiceImpl implements XPathGenerateService {
	public static final String uploadingDir = "src/main/resources/generatedfiles/";

	static Logger logger=Logger.getLogger(XPathGenerateServiceImpl.class);
	public static void xslRemoveNode(String inFileName,String outFileName,String xslFileName) throws TransformerException
	{
		try {
			TransformerFactory factory=TransformerFactory.newInstance();
			
			Templates template=factory.newTemplates(new StreamSource(new FileInputStream(xslFileName)));
			Transformer xFormer=template.newTransformer();
			
			
			Source source=new StreamSource(new FileInputStream(inFileName));
			Result result=new StreamResult(new FileOutputStream(outFileName));
			
			xFormer.transform(source, result);
		}
		catch(TransformerConfigurationException  te) {
			logger.error(te.getMessage());
		}
		catch(FileNotFoundException fe) {
			logger.error(fe.getMessage());
		}
	}
	
	public static void xslRemoveNameSpace(String inFileName,String outFileName,String xslFileName) throws TransformerException{
		try {
			TransformerFactory factory=TransformerFactory.newInstance();
			
			Templates template=factory.newTemplates(new StreamSource(new FileInputStream(xslFileName)));
			Transformer xFormer=template.newTransformer();
			
			
			Source source=new StreamSource(new FileInputStream(inFileName));
			Result result=new StreamResult(new FileOutputStream(outFileName));
			
			xFormer.transform(source, result);
		}
		catch(TransformerConfigurationException  te) {
			logger.error(te.getMessage());
		}
		catch(FileNotFoundException fe) {
			logger.error(fe.getMessage());
		}
	}
	
	public static void xslXPathGen(String inFileName,String outFileName,String xslFileName) throws TransformerException{
		try {
			TransformerFactory factory=TransformerFactory.newInstance();
			
			Templates template=factory.newTemplates(new StreamSource(new FileInputStream(xslFileName)));
			Transformer xFormer=template.newTransformer();
			
			Source source=new StreamSource(new FileInputStream(inFileName));
			Result result=new StreamResult(new FileOutputStream(outFileName));
			
			xFormer.transform(source, result);
		}
		catch(TransformerConfigurationException  te) {
			logger.error(te.getMessage());
		}
		catch(FileNotFoundException fe) {
			logger.error(fe.getMessage());
		}
	}
	
	@Override
	public List<CsvData> generate(File inputXmlFile,String inputfiletype) {
		List<CsvData> data=null;
		logger.info("X Path gen started");
		Properties props=new Properties();
		 String xPathFileExtension="csv";//format to be taken from UI
		try {
			
		//File inputXmlFile = ResourceUtils.getFile("classpath:ACCORD_EWC.xml");//TO DO:-Get Uploaded File
			
			String inputXmlFileName=inputXmlFile.getName();
			inputXmlFileName=inputXmlFileName.substring(0, inputXmlFileName.lastIndexOf("."));
			
			File outPutXmlFile=new File(uploadingDir+inputXmlFileName+"_Refined_Out.xml");
			
			if(inputfiletype.equalsIgnoreCase("ACO")) {
				//accord
				File xslFile = ResourceUtils.getFile("classpath:RemoveRootNode.xsl");
				xslRemoveNode(inputXmlFile.toString(), outPutXmlFile.toString(), xslFile.toString());
			}
			
			if(inputfiletype.equalsIgnoreCase("BOM")) {
				//BOM
				File xslFile = ResourceUtils.getFile("classpath:StripNamespace.xslt");
				xslRemoveNameSpace(inputXmlFile.toString(), outPutXmlFile.toString(), xslFile.toString());
			}
			
			if(inputfiletype.equalsIgnoreCase("CDM")) {
				//CDM
				File xslFile = ResourceUtils.getFile("classpath:StripNamespace.xslt");
				xslRemoveNameSpace(inputXmlFile.toString(), outPutXmlFile.toString(), xslFile.toString());
			}
			
			if(outPutXmlFile.exists()) {
				logger.info("Output file created");
			}
			
			File inputXmlFileMod=new File(outPutXmlFile.toString());
			
			File outputXmlFileMod=new File(uploadingDir+inputXmlFileName+"."+xPathFileExtension);
			
			
			System.out.println("Out Put FIle Exist's"+outputXmlFileMod.exists()+" Input Xml FIle Exists"+inputXmlFileMod.exists());
			File xslFileMod = ResourceUtils.getFile("classpath:GenerateXpath.xsl");
			
			xslXPathGen(inputXmlFileMod.toString(), outputXmlFileMod.toString(), xslFileMod.toString());
			
			if(outputXmlFileMod.exists()) {
				String fileName=outputXmlFileMod.toString();
				data=CsvFormatter.generateRefinedFile(fileName,inputXmlFileName);
				logger.info("Xpath file created....");
				logger.info("Xpath GenerationSuccessfull....");
				
			}
		}
		
		catch(FileNotFoundException e)
		{
			logger.error(e.getMessage());
		}
		catch(RuntimeException e)
		{
			throw new RuntimeException("Error While Processing Input file");
		}
		catch(Exception e)
		{
			throw new RuntimeException("UnExpected Error Occured");
		}
		return data;
	}
	public static void main(String[] args) {
		XPathGenerateServiceImpl g=new XPathGenerateServiceImpl();
		g.generate(null,null);
	}
	
	

}
