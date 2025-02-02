package com.jasper.server.web;

import com.jasper.server.jasper.JasperReportService;
import java.io.IOException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportController {

  @Autowired
  JasperReportService jasperReportService;

  @GetMapping("reports/{reportName}")
  public ResponseEntity<Resource> getReport(@PathVariable String reportName, @RequestParam String fileFormat, @RequestParam Map<String, Object> reportParameters) throws JRException, IOException {
    ByteArrayResource resource = null;

    try {
      
      byte[] reportContent = jasperReportService.getReport(reportName, fileFormat, reportParameters);
  
      resource = new ByteArrayResource(reportContent);
    }
    catch (RuntimeException e) {
      System.out.println(e);

      return ResponseEntity.status(500).body(null);
    }

    return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .contentLength(resource.contentLength())
      .header(HttpHeaders.CONTENT_DISPOSITION,
          ContentDisposition.attachment()
              .filename(reportName + "." + fileFormat)
              .build().toString())
      .body(resource);
  }
}
