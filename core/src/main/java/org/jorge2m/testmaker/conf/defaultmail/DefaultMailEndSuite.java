package org.jorge2m.testmaker.conf.defaultmail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.jorge2m.testmaker.conf.Log4jConfig;
import org.jorge2m.testmaker.conf.State;
import org.jorge2m.testmaker.domain.InputParamsTM;
import org.jorge2m.testmaker.domain.SenderMailEndSuiteI;
import org.jorge2m.testmaker.domain.suitetree.SuiteTM;

public class DefaultMailEndSuite implements SenderMailEndSuiteI {
	
	private final String from = "Robotest QA<jorge.munoz.sge@mango.com>";

	@Override
	public void sendMail(SuiteTM suite) {
		InputParamsTM inputParams = suite.getInputParams();
		List<String> toMails = inputParams.getMails();
		List<String> ccMails = new ArrayList<>();
		try {
			InternetAddress[] myToList = InternetAddress.parse(String.join(",", toMails));
			InternetAddress[] myCcList = InternetAddress.parse(String.join(",", ccMails));
			ArrayList<AttachMail> listaAttachImages = new ArrayList<>();      
			String mensajeHTML =
				"<p style=\"font:12pt Arial;\">" +
				"Hola, <br><br>" +
				"se ha ejecutado el siguiente script:" +
				"</p>";

			List<SuiteTM>list1Suite = new ArrayList<>();
			list1Suite.add(suite);
			mensajeHTML+=CorreoReport.constuctTableMail(list1Suite);

			Log4jConfig.pLogger.info(". Procedemos a enviar correo!");
			new MailClient().mail(this.from, myToList, myCcList, getSubjectMail(suite), mensajeHTML, listaAttachImages);
			Log4jConfig.pLogger.info("Correo enviado!");
		}
		catch (Exception e) {
			Log4jConfig.pLogger.fatal("Problem sending mail", e);
		}
	}
	
	private String getSubjectMail(SuiteTM suite) {
		InputParamsTM inputParams = suite.getInputParams();
		String subjectMail = 
			"Result TestSuite " + inputParams.getSuiteName() + 
			" (" + inputParams.getApp() + " / " + inputParams.getUrlBase() + ")";
		if (suite.getResult().isMoreCriticThan(State.Warn)) {
			return (subjectMail + " (With Problems)");
		} else {
			return (subjectMail + " (OK)");
		}
	}
}
