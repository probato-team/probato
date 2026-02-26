package org.probato.test.datamodel;

import org.probato.model.Datamodel;

public class UserModel extends Datamodel {

	private String name;
	private String other;
	private int age;
	private long value;
	private double current;
	private boolean active;
	private CredentialsModel credentials;

	public String getName() {
		return name;
	}

	public String getOther() {
		return other;
	}

	public int getAge() {
		return age;
	}

	public Long getValue() {
		return value;
	}

	public double getCurrent() {
		return current;
	}

	public Boolean getActive() {
		return active;
	}

	public CredentialsModel getCredentials() {
		return credentials;
	}

}