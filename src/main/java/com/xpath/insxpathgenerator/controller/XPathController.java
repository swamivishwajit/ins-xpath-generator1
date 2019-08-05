package com.xpath.insxpathgenerator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.io.InputStream;
import java.util.List;

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

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/xpath")
public class XPathController {
	
	public static final String uploadingDir = "c:\\users\\"+System.getProperty("user.name")+"\\XPath\\uploaded\\";
	@Autowired
	private XPathGenerateService service;
	
	@RequestMapping(method = RequestMethod.POST)
    public List<CsvData> uploadingPost(@RequestPart("file") MultipartFile file1,@RequestParam("fileType") String fileType ) throws IOException {
        
		
		    Path filepath = Paths.get("c:\\users\\"+System.getProperty("user.name")+"\\XPath\\", file1.getOriginalFilename());

		    try (OutputStream os = Files.newOutputStream(filepath)) {
		        os.write(file1.getBytes());
		    }
		
		    
            //file1.transferTo(file);
            File inputFile=new File(filepath.toString());
            System.out.println("Exists"+inputFile.exists()+"Name"+inputFile.getName());
            List<CsvData> data=service.generate(inputFile,fileType);
            

        return data;
    }
	
	@RequestMapping(value = "/asjson",method =RequestMethod.GET)
	public List<CsvData> getXPathAsJson(){
		return service.generate(null,null);
	}

}
