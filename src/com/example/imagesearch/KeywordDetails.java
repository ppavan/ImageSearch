package com.example.imagesearch;

import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;

public class KeywordDetails implements Serializable, Comparable<KeywordDetails> {

	@DatabaseField(id = true)
	String keyword;

	@DatabaseField
	Date date;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public KeywordDetails() {

	}

	public KeywordDetails(String keyword, Date date) {
		super();
		this.keyword = keyword;
		this.date = date;
	}

	@Override
	public String toString() {
		return "KeywordDetails [keyword=" + keyword + ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		KeywordDetails other = (KeywordDetails) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	@Override
	public int compareTo(KeywordDetails another) {
		return another.getDate().compareTo(this.getDate());
	}

}
