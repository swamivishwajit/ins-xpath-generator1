package com.xpath.insxpathgenerator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.xpath.insxpathgenerator.domain.CsvData;

@Service
public class XPathGenerate {
	@Value("classpath:data/ACCORD_EWC.txt")
	Resource inputResourceXmlFile;

	static Logger logger=Logger.getLogger(XPathGenerate.class);
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
	
	public List<CsvData> generate() {
		List<CsvData> data=null;
		String baseDir="C:\\XPathGenerator";
		//PropertyConfigurator.configure("log4j.properties");
		logger.info("X Path gen started");
		Properties props=new Properties();
		InputStream input=null;
//		String inputfiletype=null;
//		String inputFileTypeSelect=null;
		String inputfiletype="ACO";//TO Be Taken from UI
		 String xPathFileExtension="csv";//format to be taken from UI
		String inputFileTypeSelect=null;
		try {
			//input =new FileInputStream(baseDir+"config.properties");
			//props.load(input);
			//File inputXmlFile=new File(baseDir+props.getProperty("inputxmlFile")+".xml");
			//
			
			File inputXmlFile = ResourceUtils.getFile("classpath:ACCORD_EWC.xml");//TO DO:-Get Uploaded File
			
			
			String inputXmlFileName=inputXmlFile.getName();
			inputXmlFileName=inputXmlFileName.substring(0, inputXmlFileName.lastIndexOf("."));
			
			//File outPutXmlFile=new File(baseDir+props.getProperty("inputxmlFile")+"_Refined_Out.xml");
//			File outPutXmlFile=new File(inputXmlFileName+"_Refined_Out.xml");
			File outPutXmlFile=new File("c:\\users\\"+System.getProperty("user.name")+"\\XPath\\"+inputXmlFileName+"_Refined_Out.xml");
			
			
			
			if(inputfiletype.equalsIgnoreCase("ACO")) {
				//accord
				//File xslFile=new File("RemoveRootNode.xslt");
				File xslFile = ResourceUtils.getFile("classpath:RemoveRootNode.xsl");
				xslRemoveNode(inputXmlFile.toString(), outPutXmlFile.toString(), xslFile.toString());
			}
			
			if(inputfiletype.equalsIgnoreCase("BOM")) {
				//BOM
				//File xslFile=new File("StripNamespace.xslt");
				File xslFile = ResourceUtils.getFile("classpath:StripNamespace.xslt");
				xslRemoveNameSpace(inputXmlFile.toString(), outPutXmlFile.toString(), xslFile.toString());
			}
			
			if(inputfiletype.equalsIgnoreCase("CDM")) {
				//CDM
				//File xslFile=new File("StripNamespace.xslt");
				File xslFile = ResourceUtils.getFile("classpath:StripNamespace.xslt");
				xslRemoveNameSpace(inputXmlFile.toString(), outPutXmlFile.toString(), xslFile.toString());
			}
			
			if(outPutXmlFile.exists()) {
				logger.info("Output file created");
			}
			
			File inputXmlFileMod=new File(outPutXmlFile.toString());
			
//			File outputXmlFileMod=new File(baseDir+props.getProperty("inputxmlFile")+"_xPathOutput."+props.getProperty("xPathFileExtension"));
			File outputXmlFileMod=new File("c:\\users\\"+System.getProperty("user.name")+"\\XPath\\"+inputXmlFileName+"."+xPathFileExtension);
			
//			File xslFileMod=new File("GenerateXpath.xsl");
			
			System.out.println("Out Put FIle Exist's"+outputXmlFileMod.exists()+" Input Xml FIle Exists"+inputXmlFileMod.exists());
			File xslFileMod = ResourceUtils.getFile("classpath:GenerateXpath.xsl");
			
			xslXPathGen(inputXmlFileMod.toString(), outputXmlFileMod.toString(), xslFileMod.toString());
			
			if(outputXmlFileMod.exists()) {
				String fileName=outputXmlFileMod.toString();
				//data=CsvFormatter.generateRefinedFile(fileName,inputfiletype);
				logger.info("Xpath file created....");
				logger.info("Xpath GenerationSuccessfull....");
				
			}
		}
		catch(FileNotFoundException e)
		{
			logger.error(e.getMessage());
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return data;
	}
	public static void main(String[] args) {
		XPathGenerate g=new XPathGenerate();
		g.generate();
	}
	
	

}
