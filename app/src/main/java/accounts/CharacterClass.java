package accounts;

import java.util.ArrayList;

import android.util.Log;
import model.*;

public class CharacterClass {
	private String type;
	private Attributes defaultAttributes;
	private Attributes currentAttributes;
	private int level;
	private int skillPoints;
	private int remainingExp;
	
	public CharacterClass(String type, String stats, int lvl, int exp)
			throws Exception {
		this.type = type;
		this.remainingExp = exp;
		if (type.equals("warrior"))
			this.defaultAttributes = new Attributes(50, 50, 12, 5, 12, 5, 5, 1);
		else if (type.equals("wizard"))
			this.defaultAttributes = new Attributes(50, 50, 5, 12, 5, 12, 5, 1);
		else if (type.equals("hunter"))
			this.defaultAttributes = new Attributes(50, 50, 8, 6, 6, 6, 10, 4);
		String[] temp = stats.split("\\s+");
		int[] i = new int[temp.length];
		for (int x = 0; x < temp.length; x++)
			i[x] = Integer.parseInt(temp[x]);
		this.currentAttributes = new Attributes(i[0], i[1], i[2], i[3], i[4],
				i[5], i[6], i[7]);
		this.level = lvl;
		this.skillPoints = getSkillPoints();
	}

	public String getType() {
		return type;
	}

	public int getLevel() {
		return level;
	}

	private int getSkillPoints() {
		ArrayList<Integer> original = defaultAttributes.get();
		ArrayList<Integer> current = currentAttributes.get();
		int difference = 0;
		for (int i = 0; i < original.size(); i++) {
			difference += (current.get(i) - original.get(i));
		}
		return (((level - 1) * 3) - (difference));
	}

	public void resetSkillPoints() {
		skillPoints = (level - 1) * 3;
		currentAttributes = defaultAttributes;
	}

	public Attributes getAttributes() {
		return currentAttributes;
	}

	public void upgrade(String attr) {
		if (skillPoints > 0) {
			currentAttributes.increaseAttribute(attr);
			skillPoints--;
		}
	}
	
	public int getExp(){ return remainingExp;}
	
	public boolean addExp(int exp){
		remainingExp -= exp;
		while(remainingExp <= 0){
			level++;
			getSkillPoints();
			remainingExp = (level * (level * 50)) + remainingExp;
			if(remainingExp >0) return true;
		}
		return false;
	}
}