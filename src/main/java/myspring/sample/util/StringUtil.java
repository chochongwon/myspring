package myspring.sample.util;

public class StringUtil {
	
	   /**
		 * Method crop. 문자열 자리수만큼 끊어주기
		 * @param str 문자열
		 * @param i 자리수
		 * @return String
		 */
		public static String crop(String str, int i) {
	    	if (str==null) return "";
	    	return (str.length()>i)
	    			? str.substring(0,i)+"..."
	    			: str;
	    }

	    /**
		 * Method cropByte. 문자열 바이트수만큼 끊어주고, 생략표시하기
		 * @param str 문자열
		 * @param i 바이트수
		 * @param trail 생략 문자열. 예) "..."
		 * @return String
		 */
		public static String cropByte(String str, int i, String trail) {
	    	if (str==null) return "";
	    	String tmp = str;
	    	int slen = 0, blen = 0;
	    	char c;
	    	try {
	        	if(tmp.getBytes("MS949").length>i) {
	        		while (blen+1 < i) {
	        			c = tmp.charAt(slen);
	        			blen++;
	        			slen++;
	        			if ( c  > 127 ) blen++;  //2-byte character..
	        		}
	        		tmp=tmp.substring(0,slen)+trail;
	        	}
	        } catch(java.io.UnsupportedEncodingException e) {
	        	System.out.println("Unsupported Encoding:"+str);
	        }
	    	return tmp;
	    }

		/**
		 * 바이트로 문자열 substring
		 * 
		 * String string = "123가나다라마";
		 * assertEquals("가나", substr(string, 3, 6));
		 * assertEquals("가나다", substr(string, 3, 8));
		 * assertEquals("라마", substr(string, 9, 12));
		 * @param string
		 * @param begin
		 * @param end
		 * @return
		 */
		public static String cropByte(String string, int begin, int end) {
	    	int beginPos = 0, endPos = 0, bytelen = 0;
	    	char c;
	    	try {
	        	if(string.getBytes("MS949").length>end) {
	        		while (bytelen < end) {
	        			c = string.charAt(endPos);
	        			if ( bytelen < begin ) beginPos++;
	        			bytelen++;
	        			endPos++;
	        			if ( c  > 127 ) bytelen++;  //2-byte character..
	        		}
	        		string=string.substring(beginPos,endPos);
	        	}
	        } catch(java.io.UnsupportedEncodingException e) {
	        	System.out.println("Unsupported Encoding:"+string);
	        }
	    	return string;
		}
}
