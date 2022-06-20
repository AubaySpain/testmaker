package com.github.jorge2m.testmaker.conf.defaultmail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.internet.InternetAddress;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.SenderReportOutputPort;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.testreports.html.HtmlStatsSuitesBuilder;

public class SenderReportByMailAdapter implements SenderReportOutputPort {
	
	private final String user;
	private final String password;
	private final List<String> toMails;
	private final List<String> ccMails;
	private final String hostTestMaker;
	
	public SenderReportByMailAdapter(String user, String password, List<String> toMails, List<String> ccMails, String hostTestMaker) {
		this.user = user;
		this.password = password;
		this.toMails = toMails==null ? new ArrayList<>() : toMails;				
		this.ccMails = ccMails==null ? new ArrayList<>() : ccMails;
		this.hostTestMaker = hostTestMaker;
	}
	
	@Override
	public boolean send(SuiteBean suite) {
		return send(Arrays.asList(suite));
	}
	
	@Override
	public boolean send(List<SuiteBean> listSuites, List<SuiteBean> listSuitesOld) {
		try {
			String mensajeHTML = getMessageHTML(listSuites, listSuitesOld, hostTestMaker);
			return sendHtml(mensajeHTML, listSuites);
		}
		catch (Exception e) {
			Log4jTM.getGlobal().fatal("Problem sending email", e);
			return false;
		}
	}
	
	@Override
	public boolean send(List<SuiteBean> listSuites) {
		try {
			String mensajeHTML = getMessageHTML(listSuites, null, hostTestMaker);
			return sendHtml(mensajeHTML, listSuites);
		}
		catch (Exception e) {
			Log4jTM.getGlobal().fatal("Problem sending email", e);
			return false;
		}
	}
	
	private boolean sendHtml(String html, List<SuiteBean> listSuites) throws Exception {
		InternetAddress[] myToList = InternetAddress.parse(String.join(",", toMails));
		InternetAddress[] myCcList = InternetAddress.parse(String.join(",", ccMails));
		ArrayList<AttachMail> listaAttachImages = new ArrayList<>();      

		Log4jTM.getGlobal().info(". Sending email...");
		MailClient mailClient = new MailClient();
		boolean sended = mailClient.mail(user, password, myToList, myCcList, getSubjectMail(listSuites), html, listaAttachImages);
		if (sended) {
			Log4jTM.getGlobal().info("Email sended!");
			return true;
		}
		Log4jTM.getGlobal().fatal("Problem sending email");
		return false;
	}

	private String getMessageHTML(List<SuiteBean> listSuites, List<SuiteBean> listSuitesOld, String hostTestMaker) 
			throws Exception {
		String mensajeHTML =
			"<p style=\"font:12pt Arial;\">" +
			"Hi, <br><br>" +
			"these TestSuites had been executed:" +
			"</p>";

		HtmlStatsSuitesBuilder getterHtmlSuites = new HtmlStatsSuitesBuilder(listSuites, listSuitesOld, hostTestMaker);
		mensajeHTML+=getterHtmlSuites.getHtml();
		return mensajeHTML;
	}
	
	private String getSubjectMail(List<SuiteBean> listSuites) {
		if (listSuites.size()==1) {
			return getSubjectMail(listSuites.get(0));
		}
		return String.format("Result %s TestSuites executed", listSuites.size());
	}
	
	private String getSubjectMail(SuiteBean suite) {
		String subjectMail = 
			"Result TestSuite " + suite.getName() + 
			" (" + suite.getApp() + " / " + suite.getUrlBase() + ")";
		if (suite.getResult().isMoreCriticThan(State.Warn)) {
			return (subjectMail + " (With Problems)");
		} else {
			return (subjectMail + " (OK)");
		}
	}
}
