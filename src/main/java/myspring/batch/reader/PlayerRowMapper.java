package myspring.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import myspring.batch.model.PlayerBean;

public class PlayerRowMapper implements RowMapper<PlayerBean> {

	@Override
	public PlayerBean mapRow(ResultSet rs, int rowNum) throws SQLException {

		PlayerBean player = new PlayerBean();
		
		player.setId(rs.getString("player_id"));
		player.setFirstName(rs.getString("first_name"));
		player.setLastName(rs.getString("last_name"));
		player.setPosition(rs.getString("position"));
		player.setBirthYear(rs.getInt("birth_year"));
		player.setDebutYear(rs.getInt("debut_year"));
		
		return player;
	}
}
