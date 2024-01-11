package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.VideoRecorder;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class VideoStorer extends TestCaseEvidenceStorerBase {

	public VideoStorer(TestCaseTM testcase) {
		super(TestCaseEvidence.VIDEO, testcase);
	}
	
	@Override
	protected void store() {
		if (testcase.isStopRecordNeeded()) {
			if (isRemote()) {
				VideoRecorder.make(testcase).stop();
				String video = getVideoBase64FromFile(5);
				testcase.setVideo(video);
				deleteFileVideo();
			} else {
				if (testcase.getDriver()!=null) {
					VideoRecorder.make(testcase).stop();
				}
			}
		}
	}
	
	@Override
	public void storeInFile(String video) {
		String pathFile = TestCaseEvidence.VIDEO.getPathFile(testcase);
		byte[] bytesMp4 = Base64.getMimeDecoder().decode(video);
		File file = new File(pathFile);
		
		try {
	        Path parentDir = file.toPath().getParent();
	        if (!Files.exists(parentDir)) {
	            Files.createDirectories(parentDir);
	        }
	        Files.write(file.toPath(), bytesMp4, StandardOpenOption.CREATE);
		}
		catch (IOException e) {
			Log4jTM.getLogger().warn("Problem saving File " + pathFile, e);
		}
	}
	
	private String getVideoBase64FromFile(int seconds) {
		for (int i=0; i<seconds; i++) {
			try {
				return getVideoBase64FromFile();
			} catch (IOException e) {
				PageObjTM.waitMillis(1000);
			}
		}
		Log4jTM.getLogger().error("Not recovered file video");
		return "";
	}
	
	private String getVideoBase64FromFile() throws IOException {
		String filePath = TestCaseEvidence.VIDEO.getPathFile(testcase);
        File file = new File(filePath);
        try {
        	byte[] videoBytes = Files.readAllBytes(file.toPath());
        	return Base64.getEncoder().encodeToString(videoBytes);
	    } catch (IOException e) {
	    	Log4jTM.getLogger().info("Problem recovering file video {} {}", filePath, e.toString());
	    	throw e;
	    }
	}
	
	private void deleteFileVideo() {
        Path path = Paths.get(TestCaseEvidence.VIDEO.getPathFile(testcase));
        try {
        	Files.delete(path);
        } catch (IOException e) {
        	Log4jTM.getLogger().warn("Problem deleting file video {}", path, e);
        }
	}
	
}
