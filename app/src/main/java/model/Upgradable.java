package model;
public interface Upgradable{
	public void setLevel(int lvl);
	public int getLevel();
	public Attributes getAttributes();
	public void upgrade();
	
}