package model;

import java.util.ArrayList;

public class Attributes {
	int HP;
	int AP;
	int pDmg;
	int mDmg;
	int pDef;
	int mDef;
	int speed;
	int crit;
	public Attributes(int hp, int ap, int pa, int ma, int pd, int md, int spd, int crt){
		this.HP = hp;
		this.AP = ap;
		this.pDmg = pa;
		this.mDmg = ma;
		this.pDef = pd;
		this.mDef = md;
		this.speed = spd;
		this.crit = crt;
	}
	
	public ArrayList<Integer> get(){
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(HP);
		array.add(AP);
		array.add(pDmg);
		array.add(mDmg);
		array.add(pDef);
		array.add(mDef);
		array.add(speed);
		array.add(crit);
		return array;
	}
	
	public void increaseAttribute(String attribute){
		if(attribute.equals("HP"))this.HP++;
		else if(attribute.equals("AP"))this.AP++;
		else if(attribute.equals("pDmg"))this.pDmg++;
		else if(attribute.equals("mDmg"))this.mDmg++;
		else if(attribute.equals("pDef"))this.pDef++;
		else if(attribute.equals("mDef"))this.mDef++;
		else if(attribute.equals("speed"))this.speed++;
		else if(attribute.equals("crit"))this.crit++;
	}
	
}