package com.xpath.insxpathgenerator.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xpath.insxpathgenerator.domain.CsvData;
import com.xpath.insxpathgenerator.service.XPathGenerateService;

@RestController
@RequestMapping("/xpath")
public class XPathController {
	
	public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingDir/";
	@Autowired
	private XPathGenerateService service;
	
	@RequestMapping(method = RequestMethod.POST,headers = "Content-Type= multipart/form-data")
    public String uploadingPost(@RequestParam("file") MultipartFile file1,@PathVariable String fileType) throws IOException {
        
            File file = new File(uploadingDir + file1.getOriginalFilename());
            file1.transferTo(file);
        

        return "redirect:/";
    }
	
	@RequestMapping(value = "/asjson",method =RequestMethod.GET)
	public List<CsvData> getXPathAsJson(){
		return service.generate();
	}

}
