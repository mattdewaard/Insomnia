package accounts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import model.Inventory;
import model.ItemManager;
import model.Toaster;
import Items.Equipment;

import android.content.Context;
import android.util.Log;

/*
 [0]		Username
 [1]		Password
 [2]		Class
 [3]		Level
 [4]		Gold
 [5]		remainingExp
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

public class AccountManager {
    static BufferedReader br;

    public static boolean registerAccount(Context context, String uName,
                                          String password, String charClass) {
        if (uName.length() == 0) {          // Character name needs to have length
            Toaster.makeToast(context, "Enter your name");
            return false;
        } else {
            File characterNames = new File(context.getFilesDir(),
                    "character_names");
            try {
                br = new BufferedReader(new FileReader(characterNames));
                while (br.ready()) {
                    if (uName.equals(br.readLine())) {       // Character name already exists in the file
                        Toaster.makeToast(context, "This save name already exists.");
                        br.close();
                        return false;
                    }
                }
                return generateFile(context, uName, password,
                        charClass);             // Return if file generation was successful or not
            } catch (Exception e) {
                Log.d("Character Names File Error", e.toString());
                return false;           // Return false if file error
            }
        }
    }

    public static Account getAccount(Context context, String uName, String password) {
        Account acc;        // Empty account to store data
        File characterFile = new File(context.getFilesDir(), uName);    // file containing the data for a character
        ArrayList<String> details = new ArrayList<String>();

        // READ IN FILE DATA
        try {
            br = new BufferedReader(new FileReader(characterFile));
            while (br.ready()) {
                details.add(br.readLine());         // Add each piece of data into a string list
                Log.d("Data", details.get(details.size() - 1) + "  " + Integer.toString(details.size() - 1)); // Tag data index
            }
            br.close();
        } catch (Exception e) {
            Log.d("Character file error", e.toString());
        }

        // PROCESS FILE DATA
        if (details.size() < 12) {              // number of details must be 12
            Log.d("Saved data not valid", Integer.toString(details.size()));
            return null;
        } else {
            if (details.get(1).equals(password)) {      // Compare two passwords

                int i = 7;
                try {
                    CharacterClass cc = new CharacterClass(details.get(2),
                            details.get(6), Integer.parseInt(details.get(3)),
                            Integer.parseInt(details.get(5)));          // Parameters: Type, stats, level, exp

                    ArrayList<Skill> skills = new ArrayList<Skill>();
                    Skill skill;
                    String[] array;
                    while (i < 11) {
                        array = details.get(i).split("-");          // Gets the name and current level;
                        skill = SkillManager.getSkill(array[0]);        // Get skill by name
                        skill.setLevel(Integer.parseInt(array[1]));     // Set skill level
                        skills.add(skill);
                        i++;

                    }

                    ArrayList<Equipment> equipment = new ArrayList<Equipment>();
                    Equipment equip;
                    while (i < 16 && details.get(i).equals("Inv") == false) {
                        array = details.get(i).split(" ");                          // Get data for equipment
                        equip = (Equipment) ItemManager.getItem(array[0]);
                        equip.setLevel(Integer.parseInt(array[1]));
                        if (array[2].equals("null") == false) {                      // Cannot add imbue if imbue is null
                            equip.setImbue(ItemManager.getImbue(array[2]),
                                    Integer.parseInt(array[3]));
                        }
                        equipment.add(equip);
                        i++;
                    }
                    i++;

                    ArrayList<String> invent = new ArrayList<String>();
                    while (i < details.size()) {
                        invent.add(details.get(i));
                        Log.d("Inventory item " + i, details.get(i));
                        i++;
                    }
                    Inventory inventory = new Inventory(invent,
                            Integer.parseInt(details.get(4)));      // Parameters: Array of items details, gold
                    acc = new Account(details.get(0), details.get(1), cc,
                            skills, equipment, inventory);          // Parameters: Username, Password, CharacterClass, Skills, equipment, inventory
                    return acc;
                } catch (Exception e) {
                    Log.d("Error", e.toString() + " " + i);
                }
            }
        }
        return null;
    }

    public static void deleteAccount(Context context, String accountName) {
        try {
            File characterNames = new File(context.getFilesDir(),
                    "character_names");
            File account = new File(context.getFilesDir(), accountName);

            br = new BufferedReader(new FileReader(
                    characterNames));
            ArrayList<String> names = new ArrayList<String>();
            String temp;
            while (br.ready()) {
                temp = br.readLine();
                if (temp.equals(accountName) == false) {        // Write all files that have a different name
                    names.add(temp);
                }
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    characterNames, false));
            for (int i = 0; i < names.size(); i++) {
                bw.write(names.get(i));
                bw.newLine();
            }
            bw.close();
            account.delete();
        } catch (Exception e) {
            Log.d("Error in load/delete", e.toString());
        }
    }

    private static boolean generateFile(Context context, String name,
                                        String pass, String cClass) {
        File characterNames = new File(context.getFilesDir(), "character_names");
        File characterFile;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    characterNames, true));
            bw.write(name + '\n');
            bw.flush();
            bw.close();
            characterFile = new File(context.getFilesDir(), name);
            bw = new BufferedWriter(new FileWriter(characterFile, false));

            String stats = "";
            if (cClass.equals("warrior"))
                stats = "50 50 12  5 12  5  5  1";
            else if (cClass.equals("wizard"))
                stats = "50 50  5 12  5 12  5  1";
            else if (cClass.equals("hunter"))
                stats = "50 50  8  6  6  6 10  4";
            bw.write(name + '\n' + pass + '\n' + cClass + "\n1\n0\n50\n");
            bw.write(stats + '\n');
            bw.write("Headbutt-1\n");
            bw.write("Drop Kick-1\n");
            bw.write("Wind Gust-1\n");
            bw.write("Flame Burst-1\n");
            bw.write("Inv\n");
            bw.flush();
            bw.close();
        } catch (Exception e) {
            Log.d("Generating file error", e.toString());
            return false;
        }
        return true;
    }
}