package org.probato.test.datamodel;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.probato.entity.model.Datamodel;

public class Data extends Datamodel {
	
	private String name;
	private ZonedDateTime dateTime;
	
	public Data() {
		super();
	}

	public Data(String name, ZonedDateTime dateTime) {
		this.name = name;
		this.dateTime = dateTime;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dateTime, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		return Objects.equals(dateTime, other.dateTime) && Objects.equals(name, other.name);
	}
	
}