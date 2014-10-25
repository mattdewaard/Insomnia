package Items;
import model.Upgradable;
import java.util.ArrayList;

import model.Attributes;

public class Equipment extends Item implements Upgradable{
	// Name type price lvl imbueType imbueLvl HP AP pdmg mdmg def mdef speed
	// crit
	int level;
	Imbue imbue;
	Attributes attributes;

	public Equipment(String id, String n, String t, int p, int l, Attributes att) {
		super(id, n, t, p);
		level = l;
		attributes = att;
	}

	public int getLevel() {
		return level;
	}

	public void setImbue(Imbue type, int lvl) {
		imbue = type;
		imbue.setLevel(lvl);
	}

	public Imbue getImbue() {
		return imbue;
	}

	public void setLevel(int lvl) {
		level = lvl;
		ArrayList<Integer> att = attributes.get();
		attributes = new Attributes(att.get(0) * lvl, att.get(1) * lvl,
				att.get(2) * lvl, att.get(3) * lvl, att.get(4) * lvl,
				att.get(5) * lvl, att.get(6) * lvl, att.get(7) * lvl);
	}

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

	public Attributes getAttributes() {
		// combined att of imbue + attributes
		ArrayList<Integer> imbueData = new ArrayList<Integer>();
		if(imbue != null)  imbueData = imbue.getAttributes().get();
		else while(imbueData.size() < 8) imbueData.add(0);
		ArrayList<Integer> attData = attributes.get();
		for(int i = 0; i < attData.size();i++){ attData.set(i, attData.get(i) + imbueData.get(i)); }
		Attributes att = new Attributes(attData.get(0),attData.get(1),attData.get(2),attData.get(3),attData.get(4),attData.get(5),attData.get(6), attData.get(7));
		return att;
	}
}