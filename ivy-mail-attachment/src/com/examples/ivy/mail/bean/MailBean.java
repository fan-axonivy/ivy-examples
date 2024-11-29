package com.examples.ivy.mail.bean;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import com.examples.ivy.mail.model.Compose;
import com.examples.ivy.mail.model.MailActivity;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.scripting.objects.Binary;
import ch.ivyteam.ivy.scripting.objects.File;

public class MailBean {

	private MailActivity mail;
	private Compose compose;

	public void init() {
		this.compose = new Compose();
	}

	public MailActivity getMail() {
		return mail;
	}

	public Compose getCompose() {
		return compose;
	}

	public void setCompose(Compose compose) {
		this.compose = compose;
	}

	public void onSendEmail() {
		List<File> attachments = createAttachments(this.compose.getFiles());
		
		String to = toMailAddress(this.compose.getTos());
		String cc = toMailAddress(this.compose.getCcs());
		String bcc = toMailAddress(this.compose.getBccs());
		
		this.mail = new MailActivity();
		this.mail.setTo(to);
		this.mail.setCc(cc);
		this.mail.setBcc(bcc);
		this.mail.setFrom("from@test.com");
		this.mail.setSubject(this.compose.getSubject());
		this.mail.setBody(this.compose.getBody());
		this.mail.setAttachments(attachments);
	}

	private String toMailAddress(List<String> mailAddresses) {
		return ListUtils.emptyIfNull(mailAddresses).stream().collect(Collectors.joining(";"));
	}

	private List<File> createAttachments(UploadedFiles files) {
		if (files == null) {
			return Collections.emptyList();
		}
		List<File> ivyFiles = ListUtils.emptyIfNull(files.getFiles()).stream()
				.map(it -> storeFile(it))
				.filter(Objects::nonNull).toList();

		return ivyFiles;
	}

	private File storeFile(UploadedFile file) {
		try {
			File ivyFile = new File(file.getFileName());
			ivyFile.writeBinary(new Binary(file.getContent()));
			return ivyFile;
		} catch (IOException e) {
			Ivy.log().error("Error when storing file {0}", file.getFileName());
		}
		return null;

	}

}
