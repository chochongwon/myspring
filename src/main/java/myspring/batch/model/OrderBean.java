package myspring.batch.model;

import java.io.Serializable;

public class OrderBean implements Serializable{

	private static final long serialVersionUID = -8382772480142873117L;
	
	private String isbn;
	
	private int quantity;
	
	private double price;
	
	private String customer;
	
	public OrderBean(){
		
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "OrderBean [isbn=" + isbn + ", quantity=" + quantity
				+ ", price=" + price + ", customer=" + customer + "]";
	}
	
}
