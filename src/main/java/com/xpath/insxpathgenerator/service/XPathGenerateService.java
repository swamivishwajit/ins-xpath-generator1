package com.xpath.insxpathgenerator.service;

import java.io.File;
import java.util.List;

import com.xpath.insxpathgenerator.domain.CsvData;

public interface XPathGenerateService {
	public List<CsvData> generate(File f,String fileType);

}
