package myspring.sample.filter;

import java.io.CharArrayWriter; 
import java.io.IOException; 
import java.io.PrintWriter; 

import javax.servlet.Filter; 
import javax.servlet.FilterChain; 
import javax.servlet.FilterConfig; 
import javax.servlet.ServletException; 
import javax.servlet.ServletRequest; 
import javax.servlet.ServletResponse; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletRequestWrapper; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpServletResponseWrapper; 

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

public class TestFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(TestFilter.class);
	private String encoding;
	
	/*
	 * init 함수는 컨텍스트 로드시 호출된다.
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("TestFilter init call");
		
		// web.xml 에서 필터 등록시 정의했던 파라미터를 가져온다.
		this.encoding = config.getInitParameter("encoding");
	}
	
	/*
	 * 필터 실행 부분
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
		throws IOException, ServletException {
		
		// Controller 처리 전에 처리를 수행 하는 곳이다.
		// 파라미터 값으로 POST 데이터의 인코딩을 지정한다.
		request.setCharacterEncoding(this.encoding);
		
		// 요청과 응답에 필요한 처리를 수행할 Wrapper를 생성한다.
		ServletRequest requestWrapper = new TestRequestWrapper((HttpServletRequest) request);
		ServletResponse responseWrapper = new TestResponseWrapper((HttpServletResponse) response);
		
		logger.info("before doFilter");
		
		// 다음 필터의 호출, 실제 자원을 처리한다.
		chain.doFilter(requestWrapper, responseWrapper);
		
		// 응답에 대한 처리를 하는 곳이다.
		logger.info("after doFilter");
		
		// 응답 래퍼를 이용하여 출력을 모두 대문자로 변형한다.
		if(response.isCommitted() == false) {
			// response.getWriter().write(responseWrapper.toString().toUpperCase()); // 모두 대문자로 바꾼다.
			response.getWriter().write(responseWrapper.toString());
		}
	}
	
	/*
	 * destroy 는 컨텍스트가 종료될 때 호출된다.
	 */
	@Override
	public void destroy() {
		logger.info("destroy call");
	}
	
	/*
	 * 요청 래퍼
	 */
	class TestRequestWrapper extends HttpServletRequestWrapper {
		
		public TestRequestWrapper(HttpServletRequest request) {
			super(request);
		}
		
		/*
		 * 입력 파라미터에서 <, > 를 제거한다.
		 */
		@Override
		public String getParameter(String name) {
			String value = super.getParameter(name);
			if(value==null) value = "";
			logger.info("Testfilter replace <> character in parameter");
			return value.replaceAll("[<>]", "");
		}
	}
	
	/*
	 * 응답 래퍼를 이용하여 출력되는 응답정보를 변형한다.
	 */
	class TestResponseWrapper extends HttpServletResponseWrapper {
		protected CharArrayWriter charWriter;
		
		public TestResponseWrapper(HttpServletResponse response) {
			super(response);
			charWriter = new CharArrayWriter();
		}
		
		/*
		 * 출력을 나중에 수정할 수 있도록 모아둔다.
		 */
		@Override
		public PrintWriter getWriter() throws IOException {
			return new PrintWriter(charWriter);
		}
		
		@Override
		public String toString() {
			return charWriter.toString();
		}
	}

}
