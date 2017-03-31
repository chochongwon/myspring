package myspring.batch.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class ChunkCustomItemReader implements ItemReader<String> {
	private int index = 0;
	 
	private List<String> itemList;
	 
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {	  
		if(index < itemList.size()) {
			String str = itemList.get(index++);
			System.out.println("Read[ " + index + " ] = " + str);
			return str;
		} else {
			return null;
		}  
	 }
	 
	 public List<String> getItemList() {
		 return itemList;
	 }
	 
	 public void setItemList(List<String> itemList) {
		 this.itemList = itemList;
	 }
}
