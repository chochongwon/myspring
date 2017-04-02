package myspring.batch.reader;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import myspring.batch.model.PlayerBean;

public class PlayerFieldSetMapper implements FieldSetMapper<PlayerBean> {

	@Override
	public PlayerBean mapFieldSet(FieldSet fs) throws BindException {
		if (fs == null) {
			return null;
		}
		
		PlayerBean player = new PlayerBean();
		player.setId(fs.readString("id"));
		player.setLastName(fs.readString("lastName"));
		player.setFirstName(fs.readString("firstName"));
		player.setPosition(fs.readString("position"));
		player.setBirthYear(fs.readInt("birthYear"));
		player.setDebutYear(fs.readInt("debutYear"));
		
		return player;
	}

}
