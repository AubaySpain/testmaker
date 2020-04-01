package org.aubay.testmaker.conf.defaultmail;

import java.util.ArrayList;
import java.util.List;


public class InfoMail {

	private String SMTPHost;
	private String from;
	private String[] to;
	private String[] CC;
	private String[] CCO;
	private String[] replyTo;
	private String subject;
	private String text;
	private String[] files;
	
	public InfoMail(
	    String SMTPHost,
	    String from,
	    String[] to,
	    String[] CC,
	    String subject,
	    String text,
	    String[] files) {
		
	    this(SMTPHost, from, to, CC, new String[0], new String[0], subject, text, files);
	}
	
	public InfoMail(
	    String SMTPHost,
	    String from,
	    String[] to,
	    String[] CC,
	    String[] CCO,
	    String[] replyTo,
	    String subject,
	    String text,
	    String[] files) {
		
	    this.SMTPHost = SMTPHost;
	    this.from = from;
	    this.to = to.clone();
	    this.CC = CC.clone();
	    this.CCO = CCO.clone();
	    this.replyTo = replyTo.clone();
	    this.subject = subject;
	    this.text = text;
	    this.files = files.clone();
	}
	
	public InfoMail(
	    String SMTPHost,
	    String from,
	    String[] to,
	    String[] CC,
	    String subject,
	    String text) {
		
	    this.SMTPHost = SMTPHost;
	    this.from = from;
	    this.to = to.clone();
	    this.CC = CC.clone();
	    this.subject = subject;
	    this.text = text;
	}
	
	public InfoMail(
	    String SMTPHost,
	    String from,
	    ArrayList<String> to,
	    ArrayList<String> CC,
	    String subject,
	    String text,
	    ArrayList<String> files) {
		
	    this(SMTPHost, from, to, CC, new ArrayList<>(),  new ArrayList<>(), subject, text, files);
	}
	
	public InfoMail(
	    String SMTPHost,
	    String from,
	    ArrayList<String> to,
	    ArrayList<String> CC,
	    ArrayList<String> CCO,
	    ArrayList<String> replyTo,
	    String subject,
	    String text,
	    ArrayList<String> files) {
		
	    this.SMTPHost = SMTPHost;
	    this.from = from;
	    setTo(to);
	    setCC(CC);
	    setCCO(CCO);
	    setReplyTo(replyTo);
	    this.subject = subject;
	    this.text = text;
	    setFiles(files);
	}
	
	public InfoMail(
	    String SMTPHost,
	    String from,
	    ArrayList<String> to,
	    ArrayList<String> CC,
	    String subject,
	    String text) {
		
	    this.SMTPHost = SMTPHost;
	    this.from = from;
	    setTo(to);
	    setCC(CC);
	    this.subject = subject;
	    this.text = text;
	}
	
	public String getSMTPHost() {
	    return this.SMTPHost;
	}
	public void setSMTPHost(String host) {
	    this.SMTPHost = host;
	}
	public String getFrom() {
	    return this.from;
	}
	public void setFrom(String from) {
	    this.from = from;
	}
	public String[] getTo() {
	    return this.to;
	}
	public void setTo(String[] to) {
	    this.to = to.clone();
	}
	public String[] getCC() {
	    return this.CC;
	}
	public void setCC(String[] CC) {
	    this.CC = CC.clone();
	}
	public String[] getCCO() {
	    return this.CCO;
	}
	public void setCCO(String[] CCO) {
	    this.CCO = CCO.clone();
	}
	public String[] getReplyTo() {
	    return this.replyTo;
	}
	public void setReplyTo(String[] replyTo) {
	    this.replyTo = replyTo.clone();
	}
	public String getSubject() {
	    return this.subject;
	}
	public void setSubject(String subject) {
	    this.subject = subject;
	}
	public String getText() {
	    return this.text;
	}
	public void setText(String text) {
	    this.text = text;
	}
	public String[] getFiles() {
	    return this.files;
	}
	public void setFiles(String[] files) {
	    this.files = files.clone();
	}
	
	public void setTo(ArrayList<String> to) {
	    this.to = getArray(to);
	}
	public void setCC(ArrayList<String> CC) {
	    this.CC = getArray(CC);
	}
	public void setCCO(ArrayList<String> CCO) {
	    this.CCO = getArray(CCO);
	}
	public void setReplyTo(ArrayList<String> replyTo) {
	    this.replyTo = getArray(replyTo);
	}
	public void setFiles(ArrayList<String> files) {
	    this.files = getArray(files);
	}
	
	private String[] getArray(List<String> list) {
	    String[] array = new String[list.size()];
	    for (int i = 0; i < list.size(); i++)
	        array[i] = list.get(i);
	    return array;
	}
	
	public static String concatArrString(String []arrString){
	    int lengthArr;
	    String strReturn="";
	    if(arrString!=null){
	        lengthArr=arrString.length;
	        if(lengthArr>=1){
	            strReturn+=arrString[0];
	        }
	        for(int i=1;i<lengthArr;i++){
	            strReturn+=", "+arrString[i];
	        }
	    }
	    return strReturn;
	}
	
	@Override
	public String toString(){
	    StringBuffer strBuffer=new StringBuffer();
	    if(this.SMTPHost!=null)
	        strBuffer.append("SMTPHost :'"+this.SMTPHost+"'\t");
	    
	    if(this.from!=null)
	        strBuffer.append("from   :'"+this.from+"'\t");
	    
	    if(this.to!=null)
	        strBuffer.append("to     :'"+concatArrString(this.to)+"'\t");
	    
	    if(this.CC!=null)
	        strBuffer.append("CC     :'"+concatArrString(this.CC)+"'\t");
	    
	    if(this.CCO!=null)
	        strBuffer.append("CCO    :'"+concatArrString(this.CCO)+"'\t");
	    
	    if(this.replyTo!=null)
	        strBuffer.append("replyTo:'"+concatArrString(this.replyTo)+"'\t");
	    
	    if(this.subject!=null)
	        strBuffer.append("subject:'"+this.subject+"'\t");
	    
	    if(this.text!=null)
	        strBuffer.append("text:   '"+this.text+"'\t");
	    
	    if(this.files!=null)
	        strBuffer.append("files:  '"+concatArrString(this.files)+"'\t");
	    
	    return strBuffer.toString();
	}
}
