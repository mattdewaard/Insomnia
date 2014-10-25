package model;

import android.util.Log;

import java.util.ArrayList;
// Item Structure
// Name type price lvl imbueType imbueLvl  HP AP pdmg mdmg def mdef speed crit


import Items.Equipment;
import Items.Imbue;
import Items.Item;
import Items.Potion;
import Items.UpgradeStone;

public class Inventory {
    ArrayList<Item> items = new ArrayList<Item>();
    int gold;

    public Inventory(ArrayList<String> itemList, int g) throws Exception {
        gold = g;
        String[] details;    // [ID] [lvl] [imbueType] [imbueLevel]
        Item item;
        for (int i = 0; i < itemList.size(); i++) {
            details = itemList.get(i).split("\\s+");
            item = ItemManager.getItem(details[0]);
            Log.d("Test index: " + i, "0");
            if (item.getClass() == Equipment.class) {

                Log.d("Test index: " + i, "1");
                Equipment equip = (Equipment) item;
                equip.setLevel(Integer.parseInt(details[1]));

                Log.d("Test index: " + i, "2");
                if (details[2].equals("null") == false)
                    equip.setImbue(ItemManager.getImbue(details[2]), Integer.parseInt(details[3]));
                items.add(equip);
            } else {
                if (item.getClass() == Potion.class) {
                    Potion potion = (Potion) item;
                    potion.setCount(Integer.parseInt(details[1]));
                    items.add(potion);
                } else if (item.getClass() == UpgradeStone.class) {
                    UpgradeStone upgradeStone = (UpgradeStone) item;
                    upgradeStone.setCount(Integer.parseInt(details[1]));
                    items.add(upgradeStone);
                } else{ items.add(item);}
            }
        }
    }

    public ArrayList<Item> getItems(){return items;}

    public int getSize() {
        return items.size();
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public Item getItem(String itemId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(itemId))
                return items.get(i);
        }
        return null;
    }

    public int getGold() {
        return gold;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) { items.remove(item);}

}