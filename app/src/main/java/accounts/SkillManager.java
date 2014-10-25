package accounts;

import java.util.ArrayList;

import android.util.Log;

public class SkillManager {
	private static ArrayList<Skill> skills;

	public static Skill getSkill(String skillId) {
		if(skills == null) getSkills();
		for(int i = 0; i < skills.size(); i++){
			if(skills.get(i).getName().equals(skillId)) return skills.get(i);
		}
		return null;
	}

	private static void getSkills() {
		skills = new ArrayList<Skill>();
		try {
			skills.add(new Skill("Headbutt", "physical", "none", 1, 10, 10));
			skills.add(new Skill("Drop Kick", "physical", "none", 1, 10, 10));
			skills.add(new Skill("Wind Gust", "magical", "none", 1, 10, 10));
			skills.add(new Skill("Flame Burst", "magical", "none", 1, 10, 10));

			skills.add(new Skill("Crude Swipe", "physical", "sword", 5, 10, 20));
			skills.add(new Skill("Thrust", "physical", "sword", 5, 10, 20));
			skills.add(new Skill("Razor Wind", "magical", "staff", 5, 10, 20));
			skills.add(new Skill("Flare Bolt", "magical", "staff", 5, 10, 20));

			skills.add(new Skill("Fatal Blow", "physical", "sword", 10, 25, 35));
			skills.add(new Skill("Critical Swipe", "physical", "sword", 10, 20,
					30));
			skills.add(new Skill("Thunder Storm", "magical", "staff", 10, 25,
					35));
			skills.add(new Skill("Icy Blast", "magical", "staff", 10, 20, 30));

			skills.add(new Skill("Storm Flurry", "physical", "sword", 20, 50,
					55));
			skills.add(new Skill("Meditate", "physical", "sword", 20, -50, 0));
			skills.add(new Skill("Lightning Storm", "magical", "staff", 20, 50,
					55));
			skills.add(new Skill("Levitate", "magical", "staff", 20, -50, 0));

			skills.add(new Skill("Outrage", "physical", "sword", 30, 60, 70));
			skills.add(new Skill("Powered Stance", "physical", "sword", 30, 40,
					60));
			skills.add(new Skill("Fire Storm", "magical", "staff", 30, 60, 70));
			skills.add(new Skill("Light Force", "magical", "staff", 30, 40, 60));

			skills.add(new Skill("Lunar Slash", "physical", "sword", 45, 80,
					100));
			skills.add(new Skill("Silent Blow", "physical", "sword", 45, 65, 90));
			skills.add(new Skill("Icy Storm", "magical", "staff", 45, 80, 100));
			skills.add(new Skill("Heavy Force", "magical", "staff", 45, 65, 90));

			skills.add(new Skill("Solar Slash", "physical", "sword", 60, 150,
					200));
			skills.add(new Skill("Assassin's Wrath", "physical", "sword", 60,
					100, 150));
			skills.add(new Skill("Unrelenting Force", "magical", "staff", 60,
					150, 200));
			skills.add(new Skill("Elemental Storm", "magical", "staff", 60,
					100, 150));
		} catch (Exception e) {
			Log.d("Skill Error", e.getStackTrace().toString());
		}

	}

}