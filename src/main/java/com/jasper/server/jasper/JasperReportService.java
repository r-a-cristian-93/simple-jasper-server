package com.jasper.server.jasper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class JasperReportService {
  public byte[] getReport(String reportName, String format) throws RuntimeException {

    JasperReport jasperReport = null;

    try {
      File file = ResourceUtils.getFile("classpath:reports/" + reportName + ".jrxml");
      jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
      JRSaver.saveObject(jasperReport, reportName + ".jasper");
    } catch (FileNotFoundException | JRException e) {
      System.out.println(e);
      throw new RuntimeException("Could not get report template");
    }

    byte[] reportContent = null;

    try {
      JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(null);
      Map<String, Object> parameters = new HashMap<>();
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
      switch (format) {
        case "pdf" -> reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
        case "xml" -> reportContent = JasperExportManager.exportReportToXml(jasperPrint).getBytes();
        default -> throw new RuntimeException("Unknown report format");
      }
    } catch (JRException e) {
      System.out.println(e);
      throw new RuntimeException("Could not generate report");
    }

    return reportContent;
  }
}
