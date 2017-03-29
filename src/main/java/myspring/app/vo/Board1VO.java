package myspring.app.vo;

import java.sql.Date;

public class Board1VO {
	private Integer idx; 
	private Integer parent_idx; 
	private String title; 
	private String contents; 
	private Integer hit_cnt;
	private String del_gb;
	private Date crea_dtm;
	private String crea_id;
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public Integer getParent_idx() {
		return parent_idx;
	}
	public void setParent_idx(Integer parent_idx) {
		this.parent_idx = parent_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Integer getHit_cnt() {
		return hit_cnt;
	}
	public void setHit_cnt(Integer hit_cnt) {
		this.hit_cnt = hit_cnt;
	}
	public String getDel_gb() {
		return del_gb;
	}
	public void setDel_gb(String del_gb) {
		this.del_gb = del_gb;
	}
	public Date getCrea_dtm() {
		return crea_dtm;
	}
	public void setCrea_dtm(Date crea_dtm) {
		this.crea_dtm = crea_dtm;
	}
	public String getCrea_id() {
		return crea_id;
	}
	public void setCrea_id(String crea_id) {
		this.crea_id = crea_id;
	}
	
	@Override
	public String toString() {
		return "Board1VO [idx=" + idx + ", parent_idx=" + parent_idx + ", title=" + title + ", contents=" + contents
				+ ", hit_cnt=" + hit_cnt + ", del_gb=" + del_gb + ", crea_dtm=" + crea_dtm + ", crea_id=" + crea_id
				+ "]";
	}
}
