package com.microware.intrahealth.object;

public class MstVHND_DueListItems {
	private int ID;
	private String Items;
	private int LanguageId;
	private int Status;
	public int getLanguageId() {
		return LanguageId;
	}

	public void setLanguageId(int languageId) {
		LanguageId = languageId;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getItems() {
		return Items;
	}

	public void setItems(String items) {
		Items = items;
	}

}
