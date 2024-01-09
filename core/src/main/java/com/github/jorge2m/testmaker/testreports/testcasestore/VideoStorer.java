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

public class VideoStorer extends TestCaseEvidenceStorerBase {

	public VideoStorer(TestCaseTM testcase) {
		super(TestCaseEvidence.VIDEO, testcase);
	}
	
	@Override
	protected void store() {
		if (testcase.getInputParamsSuite().isAvailableRecord()) {
			if (isRemote()) {
				VideoRecorder.make(testcase.getDriver()).stop();
				testcase.setVideo(getVideoBase64FromFile());
				deleteFileVideo();
			} else {
				if (testcase.getDriver()!=null) {
					VideoRecorder.make(testcase.getDriver()).stop();
				}
			}
		}
	}
	
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
	
	private String getVideoBase64FromFile() {
		String filePath = TestCaseEvidence.VIDEO.getPathFile(testcase);
        File file = new File(filePath);
        try {
        	byte[] videoBytes = Files.readAllBytes(file.toPath());
        	return Base64.getEncoder().encodeToString(videoBytes);
	    } catch (IOException e) {
	    	Log4jTM.getLogger().warn("Problem recovering file video {}", filePath, e);
	        return "";
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
	
	private boolean isRecordNeeded() {
		return 
			testcase.getInputParamsSuite().isAvailableRecord() &&
			testcase.getDriver()!=null;
	}
    
}
