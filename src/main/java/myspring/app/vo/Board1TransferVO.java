package myspring.app.vo;

import java.util.List;

public class Board1TransferVO {

    private Integer idx;
    private String crea_id;
    private List<Board1VO> resultList;

    public Board1TransferVO() { }

    public void setResultList(List<Board1VO> list) {
        this.resultList = list;
    }

    public List<Board1VO> getResultList() {
        return this.resultList;
    }

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public String getCrea_id() {
		return crea_id;
	}

	public void setCrea_id(String crea_id) {
		this.crea_id = crea_id;
	}

	@Override
	public String toString() {
		return "Board1TransferVO [idx=" + idx + ", crea_id=" + crea_id + ", resultList=" + resultList + "]";
	}

}