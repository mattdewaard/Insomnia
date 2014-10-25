package accounts;

import java.util.ArrayList;

import android.util.Log;
import model.Attributes;
import model.Upgradable;

public class Skill implements Upgradable {
	private Attributes attributes;
	private int reqLevel;
	private int level;
	private String type;
	private String reqWeapon;
	private String name;

	public Skill(String name, String type, String rWpn, int rLvl, int AP,
			int dmg) throws Exception {
		this.name = name;
		this.type = type;
		this.reqWeapon = rWpn;
		this.reqLevel = rLvl;
		if (type.equals("magical"))
			attributes = new Attributes(0, AP, 0, dmg, 0, 0, 0, 0);
		else if (type.equals("physical"))
			attributes = new Attributes(0, AP, dmg, 0, 0, 0, 0, 0);
		else{
			throw new Exception();
		}
	}

	@Override
	public void setLevel(int lvl) {
		level = lvl;
		ArrayList<Integer> array = attributes.get();
		attributes = new Attributes(array.get(0) * lvl, array.get(1) * lvl,
				array.get(2) * lvl, array.get(3) * lvl, array.get(4) * lvl,
				array.get(5) * lvl, array.get(6) * lvl, array.get(7) * lvl);
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public Attributes getAttributes() {
		return attributes;
	}

	@Override
	public void upgrade() {
		ArrayList<Integer> att = attributes.get();
		attributes = new Attributes((att.get(0) / level) * (level + 1),
				(att.get(1) / level) * (level + 1), (att.get(2) / level)
						* (level + 1), (att.get(3) / level) * (level + 1),
				(att.get(4) / level) * (level + 1), (att.get(5) / level)
						* (level + 1), (att.get(6) / level) * (level + 1),
				(att.get(7) / level) * (level + 1));
		level++;
	}

	public String getName() {
		return name;
	}
}