package myspring.sample.bean;

public class ProfileTestBean {
	private String field1;
	private String field2;

	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	
	public String toString(){
		return String.format("{field1 : %s, field2 : %s}", field1, field2);
	}
}
