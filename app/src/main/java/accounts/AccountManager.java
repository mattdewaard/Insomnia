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

public class AccountManager {
	static BufferedReader br;

	public static boolean registerAccount(Context context, String uName,
			String password, String charClass) {
		if (uName.length() != 0) {
			File characterNames = new File(context.getFilesDir(),
					"character_names");
			boolean bool = true;
			try {
				String registeredName;
				br = new BufferedReader(new FileReader(characterNames));
				while (br.ready() && bool == true) {
					registeredName = br.readLine();
					if (uName.equals(registeredName))
						bool = false;
				}
			} catch (Exception e) {
				Log.d("Reading Existing Name Error", e.toString());
			}
			if (bool == false) {
				Toaster.makeToast(context, "This save name already exists.");
				return false;
			} else {
				if (generateFile(context, uName, password,
						charClass) == true)
					return true;
			}
		} else
			Toaster.makeToast(context, "Enter your name");
		return false;
	}

	public static Account getAccount(Context context, String uName, String password) {
		Account acc;
		File characterFile = new File(context.getFilesDir(), uName);
		ArrayList<String> details = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(characterFile));
			while (br.ready()) {
				details.add(br.readLine());
				Log.d("Data", details.get(details.size() - 1));
			}
			br.close();
		} catch (Exception e) {
			Log.d("Reading character file error", e.toString());
		}
		if (details.get(1).equals(password)) {
			if (details.size() < 10) {
				Log.d("Too small", Integer.toString(details.size()));
				return null;
			} else {
				try {
					CharacterClass cc = new CharacterClass(details.get(2),
							details.get(6), Integer.parseInt(details.get(3)),
							Integer.parseInt(details.get(5))); // Type,
					// stats,
					// level
					ArrayList<Skill> skills = new ArrayList<Skill>();
					Skill skill;
					String[] array;
					for (int i = 0; i < 4; i++) {
						array = details.get(7 + i).split("-");
						skill = SkillManager.getSkill(array[0]);
						skill.setLevel(Integer.parseInt(array[1]));
						skills.add(skill);
					}
					ArrayList<Equipment> equipment = new ArrayList<Equipment>();
					Equipment equip;
					int i = 11;
					while (i < 16 && details.get(i).equals("Inv") == false) {
						array = details.get(i).split(" "); // get data for
															// equipment
						equip = (Equipment) ItemManager.getItem(array[0]);
						equip.setLevel(Integer.parseInt(array[1]));
						equip.setImbue(ItemManager.getImbue(array[2]),
								Integer.parseInt(array[3]));
						equipment.add(equip);
						i++;
					}
					i++;
					ArrayList<String> invent = new ArrayList<String>();
					while (i < details.size()) {
						invent.add(details.get(i));
                        Log.d("Invent = " + i, details.get(i));
						i++;
					}
					Inventory inventory = new Inventory(invent,
							Integer.parseInt(details.get(4)));
					acc = new Account(details.get(0), details.get(1), cc,
							skills, equipment, inventory);
					return acc;
				} catch (Exception e) {
					Log.d("Error", e.toString());
				}
			}
		}
		return null;
	}

	public static void delete(Context context) {
		File characterNames = new File(context.getFilesDir(), "character_names");
		try {
			br = new BufferedReader(new FileReader(characterNames));
			File f;
			while (br.ready()) {
				f = new File(context.getFilesDir(), br.readLine());
				f.delete();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(
					characterNames, false));
			bw.write("");
			bw.flush();
			bw.close();
			br.close();
		} catch (Exception e) {
			Log.d("Deletion Error", e.toString());
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