package com.github.jorge2m.testmaker.conf.defaultmail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SenderMailEndSuiteI;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;

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

			suite.getLogger().info(". Procedemos a enviar correo!");
			new MailClient().mail(this.from, myToList, myCcList, getSubjectMail(suite), mensajeHTML, listaAttachImages);
			suite.getLogger().info("Correo enviado!");
		}
		catch (Exception e) {
			suite.getLogger().fatal("Problem sending mail", e);
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
