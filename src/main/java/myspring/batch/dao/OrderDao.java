package myspring.batch.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import myspring.batch.model.OrderBean;

public class OrderDao {
	private JdbcTemplate jdbcTemplate;
	
	public OrderDao() { }
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(final OrderBean order){		
		String sql = "insert into orders(isbn, quantity, price, customer) values(?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			
				public void setValues(PreparedStatement stmt) throws SQLException{
					stmt.setString(1,  order.getIsbn());
					stmt.setInt(2,  order.getQuantity());
					stmt.setDouble(3,  order.getPrice());
					stmt.setString(4, order.getCustomer());
				}			
		});
	}
}
