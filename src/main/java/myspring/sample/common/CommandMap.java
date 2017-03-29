package myspring.sample.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/*  
 * HandlerMethodArgumentResolver는 컨트롤러의 파라미터가 Map형식이면 동작하지 않는다.
 * 즉, HandlerMethodArgumentResolver를 만들더라도 파라미터가 Map 형식이면 설정한 클래스가 아닌, 
 * 스프링에서 기본설정된 ArgumentResolver를 거치게 된다. 
 * 항상 그런것은 아니고 <mvc:annotation-driven/>을 선언하게 되면 그렇게 된다.
 * 하지만, 프로젝트가 <mvc:annotation-driven/>가 필요하므로 Map의 기본기능을 대신할 CommandMap 을 만든다.
 * 여기서 중요한 점은 절대로 Map을 상속받으면 안된다. 상속하면 ArgumentResolver를 거치게 된다. 
 */
public class CommandMap {
    Map<String,Object> map = new HashMap<String,Object>();
    
    public Object get(String key){
        return map.get(key);
    }
     
    public void put(String key, Object value){
        map.put(key, value);
    }
     
    public Object remove(String key){
        return map.remove(key);
    }
     
    public boolean containsKey(String key){
        return map.containsKey(key);
    }
     
    public boolean containsValue(Object value){
        return map.containsValue(value);
    }
     
    public void clear(){
        map.clear();
    }
     
    public Set<Entry<String, Object>> entrySet(){
        return map.entrySet();
    }
     
    public Set<String> keySet(){
        return map.keySet();
    }
     
    public boolean isEmpty(){
        return map.isEmpty();
    }
     
    public void putAll(Map<? extends String, ?extends Object> m){
        map.putAll(m);
    }
     
    public Map<String,Object> getMap(){
        return map;
    }

	@Override
	public String toString() {
		return "CommandMap [map=" + map + "]";
	}
}
