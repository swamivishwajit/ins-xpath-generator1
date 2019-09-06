package com.xpath.insxpathgenerator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
//import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xpath.insxpathgenerator.domain.CsvData;
import com.xpath.insxpathgenerator.service.XPathGenerateService;
import com.xpath.insxpathgenerator.service.XPathGenerateServiceImpl;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/xpath")
public class XPathController {
	
	public static final String uploadingDir = "src/main/resources/generatedfiles/";
	static Logger logger=Logger.getLogger(XPathController.class);
	@Autowired
	private XPathGenerateService service;
	
	@RequestMapping(value = "http://192.168.0.104:3000/**", method = RequestMethod.OPTIONS)
	public void corsHeaders(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
		response.addHeader("Access-Control-Max-Age", "3600");
	}
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
    public List<CsvData> uploadingPost(@RequestPart("file") MultipartFile file1,@RequestParam("fileType") String fileType ) throws IOException {
        
			List<CsvData> data=null;
		    Path filepath = Paths.get(uploadingDir, file1.getOriginalFilename());

		    try (OutputStream os = Files.newOutputStream(filepath)) {
		        os.write(file1.getBytes());
	            File inputFile=new File(filepath.toString());
	            System.out.println("Exists"+inputFile.exists()+"Name"+inputFile.getName());
	            data=service.generate(inputFile,fileType);
	            
		    }
		
		    
            
		try {
			File folder = new File(uploadingDir);
			File[] filelists = folder.listFiles();
			for (File file : filelists) {
				if (file.delete()) { // System.out.println(file.getName() + " is deleted!");
					logger.info(file.getName() + " is deleted!");
				} else {
					// System.out.println("Delete operation is failed.");
					logger.error(file.getName() + "Delete operation is failed.");
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(data.size()==0) {
			throw new RuntimeException("No Data To Display Check input file");
		}

        return data;
    }
	@CrossOrigin
	@RequestMapping(value = "/asjson",method =RequestMethod.GET)
	public String getXPathAsJson(){
		return "hiiiii";
	}

}
