package tr.com.mindworks.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import tr.com.mindworks.model.TOrderOffering;
import tr.com.mindworks.model.TProductInstance;
import tr.com.mindworks.model.TProposal;

public class PdfExporter {

	public static final String RPRT_PROPOSAL = "RPRT_PROPOSAL";
	public static final String RPRT_PRODUCT = "RPRT_PRODUCT";
	public static final String RPRT_PRODUCT_SINGLE = "RPRT_PRODUCT_SINGLE";
	public static final String RPRT_PATH = "RPRT_PATH";
	public static final String IMG_PATH = "IMG_PATH";

	private static File getReport(String reportType, String exportFileName, Integer recId)
			throws JRException, FileNotFoundException {
		String reportPath = PropertiesUtil.init().getPropertyValue(RPRT_PATH);
		String imagePath = PropertiesUtil.init().getPropertyValue(IMG_PATH);
		ConnectionProvider.init();

		String reportSource = reportPath + reportType + ".jrxml";
		String reportDest = reportPath + exportFileName + ".pdf";
		File file = new File(reportDest);
		if (!file.exists()) {
			Map m = new HashMap();
			m.put("Id", recId);
			m.put("IMG_PATH", imagePath);
			JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, m, ConnectionProvider.getConnection());
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);
		}
		return file;
	}

	public static DefaultStreamedContent downloadProposalReport(TProposal proposal) {
		String reportType = PropertiesUtil.init().getPropertyValue(RPRT_PROPOSAL);
		try {
			String exportFileName = proposal.getProposalNo() + "_" + proposal.getRevisionNo();

			File file = getReport(reportType, exportFileName, proposal.getOrder().getId());

			InputStream stream = new FileInputStream(file);
			DefaultStreamedContent dsc = new DefaultStreamedContent(stream, "application/pdf", exportFileName + ".pdf");

			return dsc;
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getProposalReportFileInfo(TProposal proposal) {
		String reportType = PropertiesUtil.init().getPropertyValue(RPRT_PROPOSAL);
		try {
			String exportFileName = proposal.getProposalNo() + "_" + proposal.getRevisionNo();
			File file = getReport(reportType, exportFileName, proposal.getOrder().getId());
			return file.getAbsolutePath();
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DefaultStreamedContent downloadOrderOfferingProductionReport(TOrderOffering oo) {
		String reportType = PropertiesUtil.init().getPropertyValue(RPRT_PRODUCT);
		try {
			
			Random r = new Random( System.currentTimeMillis() );
		    String exportFileName = oo.getOrder().getOrderNo() + "_" + (10000 + r.nextInt(20000));

			File file = getReport(reportType, exportFileName, oo.getId());

			InputStream stream = new FileInputStream(file);
			DefaultStreamedContent dsc = new DefaultStreamedContent(stream, "application/pdf", exportFileName + ".pdf");

			return dsc;
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static DefaultStreamedContent downloadOrderOfferingProductionReport(TProductInstance pi) {
		String reportType = PropertiesUtil.init().getPropertyValue(RPRT_PRODUCT_SINGLE);
		try {
			String exportFileName = pi.getProductNo();

			File file = getReport(reportType, exportFileName, pi.getId());

			InputStream stream = new FileInputStream(file);
			DefaultStreamedContent dsc = new DefaultStreamedContent(stream, "application/pdf", exportFileName + ".pdf");

			return dsc;
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		}
		return null;
	}

}
