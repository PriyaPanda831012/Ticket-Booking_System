package org.airline.Entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SeatAdd implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String item;
	private boolean selected;
	
	
	public SeatAdd(int id, String item, boolean selected) {
		this.id = id;
		this.item = item;
		this.selected = selected;
		
	}
	
	public SeatAdd() {
		
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	@Override
	public String toString() {
		return "SeatAdd [item=" + item + ", selected=" + selected + "]";
	}
	
	
	
}
