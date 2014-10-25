package Items;

import model.Upgradable;
import java.util.ArrayList;

import model.Attributes;


public class Imbue extends Item implements Upgradable{

	int level;
	Attributes attributes;

	public Imbue(String id, String n, String t, int p, int l, Attributes att) {
		super(id, n, t, p);
		level = l;
		attributes = att;
	}
	
	public void setLevel(int lvl){
		level = lvl;
		ArrayList<Integer> att = attributes.get();
		attributes = new Attributes(att.get(0) * lvl, att.get(1) * lvl,
				att.get(2) * lvl, att.get(3) * lvl, att.get(4) * lvl,
				att.get(5) * lvl, att.get(6) * lvl, att.get(7) * lvl);
	}
	public int getLevel(){ return level; }
	
	public Attributes getAttributes(){return attributes;}
	
	public void upgrade(){
		ArrayList<Integer> att = attributes.get();
		attributes = new Attributes((att.get(0) / level) * (level + 1), (att.get(1) / level) * (level + 1),
				(att.get(2) / level) * (level + 1), (att.get(3) / level) * (level + 1), (att.get(4) / level) * (level + 1),
				(att.get(5) / level) * (level + 1), (att.get(6) / level) * (level + 1), (att.get(7) / level) * (level + 1));
		level++;
	}

}