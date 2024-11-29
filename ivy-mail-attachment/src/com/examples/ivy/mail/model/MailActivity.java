package com.examples.ivy.mail.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import ch.ivyteam.ivy.scripting.objects.File;

public class MailActivity implements Serializable {
	private static final long serialVersionUID = 6955086598101637881L;

	private String from;
	private String to;
	private String cc;
	private String bcc;

	private String subject;
	private String body;

	private List<File> attachments;

	public MailActivity() {
		this.attachments = Collections.emptyList();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}
}
