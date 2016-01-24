package com.example.imagesearch;

import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;

public class ParsedDetail implements Serializable {

	@DatabaseField(id = true)
	String photoUrl;

	@DatabaseField
	String photoName;

	@DatabaseField
	String searchterm;

	@DatabaseField
	String ownerName;

	@DatabaseField
	String ownerUrl;

	@DatabaseField
	String UserNote;

	@Override
	public String toString() {
		return "ParsedDetail [photoUrl=" + photoUrl + ", photoName="
				+ photoName + ", searchterm=" + searchterm + ", ownerName="
				+ ownerName + ", ownerUrl=" + ownerUrl + ", UserNote="
				+ UserNote + "]";
	}

	public ParsedDetail(String photoUrl, String photoName, String searchterm,
			String ownerName, String ownerUrl, String userNote) {
		super();
		this.photoUrl = photoUrl;
		this.photoName = photoName;
		this.searchterm = searchterm;
		this.ownerName = ownerName;
		this.ownerUrl = ownerUrl;
		UserNote = userNote;
	}

	public ParsedDetail() {

	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getSearchterm() {
		return searchterm;
	}

	public void setSearchterm(String searchterm) {
		this.searchterm = searchterm;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerUrl() {
		return ownerUrl;
	}

	public void setOwnerUrl(String ownerUrl) {
		this.ownerUrl = ownerUrl;
	}

	public String getUserNote() {
		return UserNote;
	}

	public void setUserNote(String userNote) {
		UserNote = userNote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((photoUrl == null) ? 0 : photoUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParsedDetail other = (ParsedDetail) obj;
		if (photoUrl == null) {
			if (other.photoUrl != null)
				return false;
		} else if (!photoUrl.equals(other.photoUrl))
			return false;
		return true;
	}

}
