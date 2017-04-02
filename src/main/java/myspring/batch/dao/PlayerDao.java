package myspring.batch.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import myspring.batch.model.PlayerBean;

public class PlayerDao {
	private JdbcTemplate jdbcTemplate;
	
	public PlayerDao() { }
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(final PlayerBean player) {
		String sql = "insert into player(player_id, first_name, last_name, position, birth_year, debut_year) values(?,?,?,?,?,?)";

		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			
				public void setValues(PreparedStatement stmt) throws SQLException{
					stmt.setString(1,  player.getId());
					stmt.setString(2,  player.getFirstName());
					stmt.setString(3,  player.getLastName());
					stmt.setString(4, player.getPosition());
					stmt.setInt(5,  player.getBirthYear());
					stmt.setInt(6,  player.getDebutYear());
				}			
		});
	}
}
