package myspring.app.vo;

public class Board2VO {
    private int num;
    private String title;
    private String description;
    
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Board2VO [num=" + num + ", title=" + title + ", description=" + description + "]";
	}
}
