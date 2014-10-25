package model;

import java.util.ArrayList;

import Items.Equipment;
import Items.Imbue;
import Items.Item;
import Items.Potion;
import Items.UpgradeStone;

import android.util.Log;

public class ItemManager {
    private static ArrayList<Item> commons = new ArrayList<Item>();
    private static ArrayList<Item> uncommons = new ArrayList<Item>();
    private static ArrayList<Item> rares = new ArrayList<Item>();
    private static ArrayList<Item> legendaries = new ArrayList<Item>();
    private static ArrayList<Item> ancients = new ArrayList<Item>();

    public static Item getItem(String ID) {
        char rarity;
        int index = 0;
        if (ID.length() == 4) {// rxxx
            rarity = ID.charAt(0);
            index = Integer.parseInt(ID.substring(1, 4));
        } else {
            Log.d("Error", "Invalid ID. Required \"rXXX\". Got " + ID);
            return null;
        }
        init(rarity);
        switch (rarity) {
            case ('u'): {
                if (uncommons.size() <= index)
                    return null;
                return uncommons.get(index);
            }
            case ('r'): {
                if (rares.size() <= index)
                    return null;
                return rares.get(index);

            }
            case ('l'): {
                if (legendaries.size() <= index)
                    return null;
                return legendaries.get(index);
            }
            case ('a'): {
                if (ancients.size() <= index)
                    return null;
                return ancients.get(index);
            }
            default: {
                if (commons.size() <= index)
                    return null;
                return commons.get(index);
            }
        }
    }

    private static void init(char type) {
        switch (type) {
            case ('c'):
                commons = new ArrayList<Item>();
                getCommons();
                break;
            case ('u'):
                uncommons = new ArrayList<Item>();
                getUncommons();
                break;
            case ('r'):
                rares = new ArrayList<Item>();
                getRares();
                break;
            case ('l'):
                legendaries = new ArrayList<Item>();
                getLegendaries();
                break;
            case ('a'):
                ancients = new ArrayList<Item>();
                getAncients();
                break;
        }
    }

    public static Imbue getImbue(String type) {
        init('c');
        init('u');
        init('r');
        init('l');
        init('a');
        Imbue imbue = null;
        if (commons.get(3).getType().equals(type))
            imbue = (Imbue) commons.get(3);
        else if (uncommons.get(3).getType().equals(type))
            imbue = (Imbue) uncommons.get(3);
        else if (rares.get(3).getType().equals(type))
            imbue = (Imbue) rares.get(3);
        else if (legendaries.get(3).getType().equals(type))
            imbue = (Imbue) legendaries.get(3);
        else if (ancients.get(3).getType().equals(type))
            imbue = (Imbue) ancients.get(3);
        return imbue;
    }

    public static ArrayList<Item> getLootPool(char type) {
        init(type);
        switch (type) {
            case ('c'):
                return commons;
            case ('u'):
                return uncommons;
            case ('r'):
                return rares;
            case ('l'):
                return legendaries;
            case ('a'):
                return ancients;
        }
        return null;
    }

    private static void getCommons() {
        commons.add(new Potion("c000", "Health Potion", "HP", 50, 5, 0));
        commons.add(new Potion("c001", "Action Potion", "AP", 50, 0, 5));
        commons.add(new Imbue("c002", "Fire Stone", "Fire", 50, 1,
                new Attributes(0, 0, 5, 0, 0, 0, 0, 0)));

        commons.add(new Equipment("c003", "Broken Knight's Sword", "Sword", 50,
                1, new Attributes(0, 0, 2, 0, 0, 0, 0, 0)));
        commons.add(new Equipment("c004", "Blunt Sword", "Sword", 50, 1,
                new Attributes(0, 0, 3, 0, 0, 0, 0, 2)));
        commons.add(new Equipment("c005", "Old Pirate's Blade", "Scimitar", 50,
                1, new Attributes(0, 0, 3, 0, 0, 0, 2, 0)));
        commons.add(new Equipment("c006", "Wooden Staff", "Staff", 50, 1,
                new Attributes(0, 0, 0, 3, 0, 0, 0, 0)));
        commons.add(new Equipment("c007", "Wooden Wand", "Wand", 50, 1,
                new Attributes(0, 0, 0, 3, 0, 0, 2, 0)));

        commons.add(new Equipment("c008", "Rusty Helmet", "Helm", 50, 1,
                new Attributes(10, 10, 0, 0, 2, 0, 0, 0)));
        commons.add(new Equipment("c009", "Rusty Armour", "Armour", 50, 1,
                new Attributes(25, 25, 0, 0, 2, 2, 0, 0)));
        commons.add(new Equipment("c010", "Rusty Leggings", "Leggings", 50, 1,
                new Attributes(10, 10, 0, 0, 2, 0, 0, 0)));
        commons.add(new Equipment("c011", "Dirty Hood", "Helm", 50, 1,
                new Attributes(8, 12, 0, 0, 0, 2, 1, 0)));
        commons.add(new Equipment("c012", "Dirty Robes", "Armour", 50, 1,
                new Attributes(22, 28, 0, 0, 2, 2, 1, 0)));
        commons.add(new Equipment("c013", "Dirty Pants", "Leggings", 50, 1,
                new Attributes(8, 12, 0, 0, 0, 2, 1, 0)));
        commons.add(new Equipment("c014", "Blood-stained Hood", "Helm", 50, 1,
                new Attributes(10, 10, 0, 0, 1, 1, 1, 1)));
        commons.add(new Equipment("c015", "Blood-stained Cloak", "Armour", 50,
                1, new Attributes(25, 25, 0, 0, 3, 1, 1, 1)));
        commons.add(new Equipment("c016", "Blood-stained Tights", "Leggings",
                50, 1, new Attributes(10, 10, 0, 0, 1, 1, 1, 1)));
        commons.add(new UpgradeStone("c017", "Damaged Moon Stone", "Upgrade", 50));
    }

    private static void getUncommons() {
        uncommons.add(new Potion("u000", "Health Potion", "HP", 100, 20, 0));
        uncommons.add(new Potion("u001", "Action Potion", "AP", 100, 0, 20));
        uncommons.add(new Imbue("u002", "Lightning Stone", "Lightning", 100, 1,
                new Attributes(0, 0, 5, 5, 0, 0, 0, 0)));

        uncommons.add(new Equipment("u003", "Silver Sword", "Sword", 100, 1,
                new Attributes(0, 0, 6, 0, 0, 0, 0, 0)));
        uncommons.add(new Equipment("u004", "Katana", "Katana", 100, 1,
                new Attributes(0, 0, 4, 0, 0, 0, 1, 1)));
        uncommons.add(new Equipment("u005", "Captain's Cutlass", "Scimitar",
                100, 1, new Attributes(0, 0, 4, 0, 0, 0, 2, 0)));
        uncommons.add(new Equipment("u006", "Wizard's Staff", "Staff", 100, 1,
                new Attributes(0, 0, 0, 6, 0, 0, 0, 0)));
        uncommons.add(new Equipment("u007", "Wizard's Wand", "Wand", 100, 1,
                new Attributes(0, 0, 0, 4, 0, 0, 2, 0)));

        uncommons.add(new Equipment("u008", "Silver Helmet", "Helm", 100, 1,
                new Attributes(15, 15, 0, 0, 4, 0, 0, 0)));
        uncommons.add(new Equipment("u009", "Silver Armour", "Armour", 100, 1,
                new Attributes(30, 30, 0, 0, 4, 4, 0, 0)));
        uncommons.add(new Equipment("u010", "Silver Leggings", "Leggings", 100,
                1, new Attributes(15, 15, 0, 0, 4, 0, 0, 0)));
        uncommons.add(new Equipment("u011", "Wizard's Hood", "Helm", 100, 1,
                new Attributes(12, 16, 0, 0, 0, 4, 4, 0)));
        uncommons.add(new Equipment("u012", "Wizard's Robes", "Armour", 100, 1,
                new Attributes(26, 32, 0, 0, 4, 4, 4, 0)));
        uncommons.add(new Equipment("u013", "Wizard's Pants", "Leggings", 100,
                1, new Attributes(12, 16, 0, 0, 0, 4, 4, 0)));
        uncommons.add(new Equipment("u014", "Apprentice Hood", "Helm", 100, 1,
                new Attributes(15, 15, 0, 0, 2, 2, 2, 2)));
        uncommons.add(new Equipment("u015", "Apprentice Cloak", "Armour", 100,
                1, new Attributes(30, 30, 0, 0, 4, 2, 2, 2)));
        uncommons.add(new Equipment("u016", "Apprentice Tights", "Leggings",
                100, 1, new Attributes(15, 15, 0, 0, 2, 2, 2, 2)));
        uncommons.add(new UpgradeStone("u017", "Fractured Moon Stone", "Upgrade", 100));
    }

    private static void getRares() {
        rares.add(new Potion("r000", "Health Potion", "HP", 250, 300, 0));
        rares.add(new Potion("r001", "Action Potion", "AP", 250, 0, 300));
        rares.add(new Imbue("r002", "Light Stone", "Light", 250, 1,
                new Attributes(0, 0, 10, 10, 0, 0, 5, 0)));

        rares.add(new Equipment("r003", "Blue Light", "Sword", 250, 1,
                new Attributes(0, 0, 10, 5, 0, 0, 2, 2)));
        rares.add(new Equipment("r004", "Debt of the Samurai", "Katana", 250,
                1, new Attributes(0, 0, 12, 0, 0, 0, 5, 5)));
        rares.add(new Equipment("r005", "Sea Bane", "Scimitar", 250, 1,
                new Attributes(0, 0, 14, 0, 0, 0, 5, 0)));
        rares.add(new Equipment("r006", "Storm Caster", "Staff", 250, 1,
                new Attributes(0, 0, 0, 15, 0, 0, 0, 0)));
        rares.add(new Equipment("r007", "Wind Maker", "Wand", 250, 1,
                new Attributes(0, 0, 0, 12, 0, 0, 5, 5)));

        rares.add(new Equipment("r008", "Knight's Helmet", "Helm", 250, 1,
                new Attributes(20, 20, 0, 0, 8, 0, 0, 0)));
        rares.add(new Equipment("r009", "Knight's Armour", "Armour", 250, 1,
                new Attributes(35, 35, 0, 0, 8, 8, 0, 0)));
        rares.add(new Equipment("r010", "Knight's Leggings", "Leggings", 250,
                1, new Attributes(20, 20, 0, 0, 8, 0, 0, 0)));
        rares.add(new Equipment("r011", "Storm Hood", "Helm", 250, 1,
                new Attributes(16, 24, 0, 0, 0, 8, 8, 0)));
        rares.add(new Equipment("r012", "Storm Robes", "Armour", 250, 1,
                new Attributes(28, 40, 0, 0, 8, 8, 8, 0)));
        rares.add(new Equipment("r013", "Storm Pants", "Leggings", 250, 1,
                new Attributes(16, 24, 0, 0, 0, 8, 8, 0)));
        rares.add(new Equipment("r014", "Assassin's Hood", "Helm", 250, 1,
                new Attributes(20, 20, 0, 0, 4, 4, 4, 4)));
        rares.add(new Equipment("r015", "Assassin's Cloak", "Armour", 250, 1,
                new Attributes(35, 35, 0, 0, 8, 4, 4, 4)));
        rares.add(new Equipment("r016", "Assassin's Tights", "Leggings", 250,
                1, new Attributes(20, 20, 0, 0, 4, 4, 4, 4)));
        rares.add(new UpgradeStone("r017", "Perfect Moon Stone", "Upgrade", 250));
    }

    private static void getLegendaries() {
        legendaries
                .add(new Potion("l000", "Health Potion", "HP", 500, 1000, 0));
        legendaries
                .add(new Potion("l001", "Action Potion", "AP", 500, 0, 1000));
        legendaries.add(new Imbue("l002", "Darkness Stone", "Dark", 500, 1,
                new Attributes(0, 0, 10, 10, 0, 5, 5, 5)));

        legendaries.add(new Equipment("l003", "The Founding Sword", "Sword",
                500, 1, new Attributes(0, 0, 30, 5, 0, 0, 10, 0)));
        legendaries.add(new Equipment("l004", "Bloodbane", "Katana", 500, 1,
                new Attributes(0, 0, 25, 0, 0, 0, 15, 20)));
        legendaries.add(new Equipment("l005", "Wrath of the Sea", "Scimitar",
                500, 1, new Attributes(0, 0, 20, 10, 0, 0, 20, 0)));
        legendaries.add(new Equipment("l006", "Lord's Darkness", "Staff", 500,
                1, new Attributes(0, 0, 0, 30, 0, 0, 5, 5)));
        legendaries.add(new Equipment("l007", "Chaos Wand", "Wand", 500, 1,
                new Attributes(0, 0, 0, 25, 0, 0, 15, 15)));

        legendaries.add(new Equipment("l008", "Sunlight Helmet", "Helm", 500,
                1, new Attributes(60, 40, 10, 0, 20, 5, 0, 0)));
        legendaries.add(new Equipment("l009", "Sunlight Armour", "Armour", 500,
                1, new Attributes(100, 60, 10, 0, 20, 20, 0, 0)));
        legendaries.add(new Equipment("l010", "Sunlight Leggings", "Leggings",
                500, 1, new Attributes(60, 40, 10, 0, 20, 5, 0, 0)));
        legendaries.add(new Equipment("l011", "Chaos Hood", "Helm", 500, 1,
                new Attributes(50, 40, 0, 10, 5, 20, 15, 0)));
        legendaries.add(new Equipment("l012", "Chaos Robes", "Armour", 500, 1,
                new Attributes(70, 70, 0, 10, 25, 20, 15, 0)));
        legendaries.add(new Equipment("l013", "Chaos Pants", "Leggings", 500,
                1, new Attributes(50, 40, 0, 10, 5, 20, 15, 0)));
        legendaries.add(new Equipment("l014", "Darkness Hood", "Helm", 500, 1,
                new Attributes(50, 50, 5, 0, 15, 15, 15, 10)));
        legendaries.add(new Equipment("l015", "Darkness Cloak", "Armour", 500,
                1, new Attributes(100, 70, 5, 0, 20, 15, 15, 10)));
        legendaries.add(new Equipment("l016", "Darkness Tights", "Leggings",
                500, 1, new Attributes(50, 50, 5, 0, 15, 15, 15, 10)));
        legendaries.add(new UpgradeStone("l017", "Flawless Moon Stone", "Upgrade", 500));
    }

    private static void getAncients() {
        ancients.add(new Equipment("a000", "Moon Dragon Blade", "Sword",
                1000, 1, new Attributes(0, 0, 50, 15, 0, 0, 15, 15)));
        ancients.add(new Equipment("a001", "Moon Dragon Staff", "Staff", 1000,
                1, new Attributes(0, 0, 0, 50, 0, 0, 20, 20)));

        ancients.add(new Equipment("a002", "Moon Dragon Helmet", "Helm", 1000,
                1, new Attributes(100, 60, 20, 0, 30, 10, 0, 0)));
        ancients.add(new Equipment("a003", "Moon Dragon Armour", "Armour", 1000,
                1, new Attributes(150, 80, 20, 0, 40, 30, 0, 0)));
        ancients.add(new Equipment("a004", "Moon Dragon Leggings", "Leggings",
                1000, 1, new Attributes(100, 60, 20, 0, 30, 10, 0, 0)));
        ancients.add(new Equipment("a005", "Moon Dragon Hood", "Helm", 1000, 1,
                new Attributes(80, 60, 0, 20, 10, 30, 30, 0)));
        ancients.add(new Equipment("a006", "Moon Dragon Robes", "Armour", 1000, 1,
                new Attributes(110, 90, 0, 20, 30, 40, 30, 0)));
        ancients.add(new Equipment("a007", "Moon Dragon Pants", "Leggings", 1000,
                1, new Attributes(80, 60, 0, 20, 10, 30, 30, 0)));
        ancients.add(new Equipment("a008", "Moon Dragon Light Hood", "Helm", 1000, 1,
                new Attributes(80, 70,10, 0, 20, 20, 20, 20)));
        ancients.add(new Equipment("a009", "Moon Dragon Cloak", "Armour", 1000,
                1, new Attributes(140, 90, 10, 0, 40, 30, 30, 10)));
        ancients.add(new Equipment("a010", "Moon Dragon Tights", "Leggings",
                1000, 1, new Attributes(80, 70, 10, 0, 20, 20, 20, 20)));
        ancients.add(new UpgradeStone("a011", "Flawless Moon Stone", "Upgrade", 1000));
    }
}