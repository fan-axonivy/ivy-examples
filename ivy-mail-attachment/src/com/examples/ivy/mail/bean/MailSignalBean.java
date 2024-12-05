package com.examples.ivy.mail.bean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.examples.ivy.mail.model.MailActivity;

import ch.ivyteam.ivy.cm.ContentObject;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.model.value.SignalCode;
import ch.ivyteam.ivy.scripting.objects.Binary;
import ch.ivyteam.ivy.scripting.objects.File;

public class MailSignalBean {

	public void onSendEmail() {
		List<File> attachments = getAttachments();

		MailActivity mail = new MailActivity();
		mail.setFrom("sender@test.com");
		mail.setTo("receiver@test.com");
		mail.setSubject("Send from signal");
		mail.setBody("<p> Body html</p>");
		mail.setAttachments(attachments);

		SignalCode signalCode = new SignalCode("send:mail");
		Ivy.wf().signals().send(signalCode, mail);
	}

	private List<File> getAttachments() {
		Optional<ContentObject> object = Ivy.cm().findObject("/sample");
		byte[] data = object.get().values().get(0).read().bytes();
		File file = storeFile(data);		return List.of(file);
	
	}
	
	private File storeFile(byte[] data) {
		try {
			File ivyFile = new File("sample.pdf");
			ivyFile.writeBinary(new Binary(data));
			return ivyFile;
		} catch (IOException e) {
			Ivy.log().error("Error when storing file ", e);
		}
		return null;

	}
}
