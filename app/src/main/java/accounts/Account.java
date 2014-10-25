package accounts;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import Items.Equipment;
import Items.Imbue;
import Items.Item;
import Items.Potion;
import Items.UpgradeStone;
import model.Attributes;
import model.Inventory;
import model.ItemManager;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

/*	
 [0]		Username
 [1]		Password
 [2]		Class
 [3]		Level
 [4]		remainingExp
 [5]		Gold
 [6]		HP AP pdmg mdmg def mdef speed critical
 [7]		SkillName1 lvl1 
 [8]		SkillName2 lvl2 
 [9]		SkillName3 lvl3
 [10]		SkillName4 lvl4 
 [11]		[ID] [lvl] [imbueType] [imbueLevel]
 [12]		[ID] [lvl] [imbueType] [imbueLevel]
 [13]		[ID] [lvl] [imbueType] [imbueLevel]
 [14]		[ID] [lvl] [imbueType] [imbueLevel]
 [15]		[ID] [lvl] [imbueType] [imbueLevel]
 [16]		Inv
 [17]		[ID] [lvl] [imbueType] [imbueLevel]
 ....



 Character Traits
 [Warrior]  50 50 12  5 12  5  5  1 (40)
 [Magician] 50 50  5 12  5 12  5  1 (40)
 [Hunter]   50 50  8  6  6  6 10  4 (40)
 */
public class Account {
    private static Account account;

    private String userName;
    private String password;
    private CharacterClass charClass;
    private ArrayList<Equipment> equipment = new ArrayList<Equipment>();
    private Inventory inventory;
    private ArrayList<Skill> skills;

    public Account(String user, String pass, CharacterClass chrClss,
                   ArrayList<Skill> sklls, ArrayList<Equipment> eqpmnt, Inventory inv) {
        this.userName = user;
        this.password = pass;
        this.charClass = chrClss;
        this.skills = sklls;
        this.equipment = eqpmnt;
        this.inventory = inv;
        account = this;
    }

    public static Account get() {
        return account;
    }

    public String getName() {
        return userName;
    }

    public String getCharClass() {
        return charClass.getType();
    }

    public int getLevel() {
        return charClass.getLevel();
    }

    public ArrayList<Equipment> getEquipped() {
        return equipment;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addItem(Item i) {
        inventory.addItem(i);
    }

    public void removeItem(Item i) {
        inventory.removeItem(i);
    }

    public boolean equipItem(Item item) {
        if (item.getClass() == Equipment.class) {
            int numWpns = 0;
            Equipment equip = (Equipment) item;
            for (int i = 0; i < equipment.size(); i++) {
                Equipment tempEquip = equipment.get(i);
                if (tempEquip.getType().equals("Weapon") && equip.getType().equals("Weapon")) {
                    if (numWpns++ >= 2)
                        return false;
                }
                if (tempEquip.getType().equals(equip.getType())) {
                    return false;
                }
            }
            equipment.add(equip);
            return true;
        }
        return false;
    }

    public boolean unequipItem(Item item) {
        if (item.getClass() == Equipment.class) {
            Equipment equip = (Equipment) item;
            ;
            for (int i = 0; i < equipment.size(); i++) {
                if (equipment.get(i).getId() == equip.getId()) {
                    equipment.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public Attributes getAttributes() {
        return charClass.getAttributes();
    }

    public Attributes getTotalAttributes() {
        int hp = 0, ap = 0, pdmg = 0, mdmg = 0, pdef = 0, mdef = 0, spd = 0, crit = 0;
        ArrayList<Integer> temp;
        for (int i = 0; i <= equipment.size(); i++) {
            if (i != equipment.size()) temp = equipment.get(i).getAttributes().get();
            else
                temp = charClass.getAttributes().get();
            hp += temp.get(0);
            ap += temp.get(1);
            pdmg += temp.get(2);
            mdmg += temp.get(3);
            pdef += temp.get(4);
            mdef += temp.get(5);
            spd += temp.get(6);
            crit += temp.get(7);
        }
        System.out.println(hp + " " + ap + " " + pdmg + " " + mdmg + " " + pdef + " " + mdef + " " + spd + " " + crit);
        return new Attributes(hp, ap, pdmg, mdmg, pdef, mdef, spd, crit);
    }

    public boolean gainExp(int exp) {
        return charClass.addExp(exp);
    }

    public void levelUp(String attribute) {
        charClass.upgrade(attribute);
    }

    public boolean resetLevel() {
        charClass.resetSkillPoints();
        return true;
    }

    public boolean save(Context context) {
        ArrayList<String> data = getSaveFile();
        File file = new File(context.getFilesDir(), getName());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            for (int i = 0; i < data.size(); i++) {
                bw.write(data.get(i));
                bw.newLine();
            }
            bw.flush();
            bw.close();
            return true;
        } catch (Exception e) {
            Log.d("Error in saving", e.toString());
            return false;
        }

    }

    private ArrayList<String> getSaveFile() {
        ArrayList<String> details = new ArrayList<String>();
        details.add(userName);
        details.add(password);
        details.add(charClass.getType()); // Write type
        details.add(Integer.toString(getLevel())); // Write Level
        details.add(Integer.toString(charClass.getExp()));
        details.add(Integer.toString(inventory.getGold())); // Write gold
        String string = "";
        ArrayList<Integer> array = charClass.getAttributes().get();
        for (int i = 0; i < array.size(); i++)
            string += (array.get(i) + " ");
        details.add(string); // write stats
        Skill skill;
        for (int i = 0; i < skills.size(); i++) {
            skill = skills.get(i);
            details.add(skill.getName() + "-" + skill.getLevel());
        }
        Equipment equip;
        Imbue imbue;
        for (int i = 0; i < equipment.size(); i++) {
            equip = equipment.get(i);
            imbue = equip.getImbue();
            String imbueType = "null";
            String imbueLevel = "0";
            if (imbue != null) {
                imbueType = imbue.getType();
                imbueLevel = Integer.toString(imbue.getLevel());
            }
            details.add(equip.getId() + " " + equip.getLevel() + " "
                    + imbueType + " " + imbueLevel);
        }
        details.add("Inv");
        Item item;
        for (int i = 0; i < inventory.getSize(); i++) {
            item = inventory.getItem(i);
            if (item != null) {
                if (item.getClass() == Equipment.class) {
                    equip = (Equipment) item;
                    imbue = equip.getImbue();
                    String imbueType = "null";
                    String imbueLevel = "0";
                    if (imbue != null) {
                        imbueType = imbue.getType();
                        imbueLevel = Integer.toString(imbue.getLevel());
                    }

                    details.add(equip.getId() + " " + equip.getLevel() + " "
                            + imbueType + " " + imbueLevel);
                } else {
                    String s = "";
                    if (item.getClass() == Potion.class) {
                        s = Integer.toString(((Potion) item).getCount());
                    } else if (item.getClass() == UpgradeStone.class) {
                        s = Integer.toString(((UpgradeStone) item).getCount());
                    } else {
                        s = "1";
                    }
                    details.add(item.getId() + " " + s);
                }
            }
        }
        return details;
    }
}