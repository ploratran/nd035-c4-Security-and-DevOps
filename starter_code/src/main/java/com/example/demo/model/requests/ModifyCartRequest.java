package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyCartRequest {
	
	@JsonProperty // tells Jackson to include this fields
	private String username;
	
	@JsonProperty // tells Jackson to include this fields
	private long itemId;
	
	@JsonProperty // tells Jackson to include this fields
	private int quantity;

	public ModifyCartRequest(long itemId, String username, int quantity) {
		this.username = username;
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
