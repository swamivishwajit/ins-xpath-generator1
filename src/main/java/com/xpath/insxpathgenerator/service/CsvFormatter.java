package com.xpath.insxpathgenerator.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.xpath.insxpathgenerator.domain.CsvData;

public class CsvFormatter {
	static Logger logger=Logger.getLogger(CsvFormatter.class);
	public static final char DEFAULT_SEPERATOR=',';
	public static List<CsvData> generateRefinedFile(String filePath,String inputfiletype) {
		List<CsvData> csvList=new ArrayList<CsvData>();
		
		List<String> data=new ArrayList<String>();
		
		try(Stream<String> stream=Files.lines(Paths.get(filePath))){
			stream.forEach(line->data.add(line));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		/**
		 * Loop for remove special characters
		 */
		for(String str:data) {
			CsvData data1=new CsvData();
			String[] arr=str.replace("\u00A0", " ").split("    ");
			data1.setKey(arr[0]);
			if(arr.length>1) {
				data1.setValue(arr[1]);
			}
			csvList.add(data1);
		}
		/**
		 * COde to write formatted csvFile
		 */
		
		try {
			String outputfilename=inputfiletype.toUpperCase()+"_xpathOutPut_refined.csv";
			FileWriter writter=new FileWriter(outputfilename);
			
			for(CsvData cd:csvList) {
				List<String> values=new ArrayList<String>();
				values.add(cd.getKey());
				if(null!=cd.getValue()) {
					values.add(cd.getValue());
				}
				writeLine(writter,values);
			}
			writter.flush();
			writter.close();
			//logger.info("Refined output file generated successfully");
			
		}
		catch(Exception e) {
			//logger.error(e.getMessage());
		}
		return csvList;
	}
	public static void writeLine(Writer w,List<String> values) throws IOException {
		writeLine(w,values,DEFAULT_SEPERATOR,' ');
	}
	public static void writeLine(Writer w,List<String> values,char seperators) throws IOException {
		writeLine(w,values,seperators,' ');
	}
	
	public static String followCsvFormat(String value) {
		String result=value;
		if(result.contains("\"")) {
			result=result.replace("\"", "\"\"");
		}
		return result;
	}
	
	public static void writeLine(Writer w,List<String> values,char seperators,char customQuote) throws IOException {
		boolean first=true;
		if(seperators==' ') {
			seperators=DEFAULT_SEPERATOR;
		}
		StringBuilder sb=new StringBuilder();
		for(String str:values) {
			
			if(!first) {
				sb.append(seperators);
			}
			if(customQuote==' ') {
				sb.append(followCsvFormat(str));
			}
			else {
				sb.append(customQuote).append(followCsvFormat(str)).append(customQuote);
			}
			first=false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}

}
