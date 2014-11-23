package insomniac.insomniacv112;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import Items.Equipment;
import Items.Imbue;
import Items.Item;
import Items.Potion;
import Items.UpgradeStone;
import accounts.Account;
import model.ItemManager;
import model.TabActivity;
import model.Toaster;
import model.TransitionActivity;


public class InventoryActivity extends TabActivity implements View.OnClickListener {
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ActionBar tabBar = getActionBar();
        super.onCreate(savedInstanceState);
        setContext(this);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_show_inventory);
        tabBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                selectedItem = null;
                oldSelectedItem = null;
                if (tab.getPosition() == 0) {
                    setContentView(R.layout.layout_inventory);
                    updateInventory();
                } else {
                    setContentView(R.layout.layout_equipment);
                    updateEquipment();
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                setContentView(R.layout.activity_show_inventory);
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }
        };
        tabBar.addTab(tabBar.newTab().setText(getString(R.string.text_inventory)).setTabListener(tabListener));
        tabBar.addTab(tabBar.newTab().setText(getString(R.string.text_equipped)).setTabListener(tabListener));
    }

    @Override
    public void onClick(View v) {
        Button btnEquip = (Button) findViewById(R.id.btn_equip);
        if (v.getId() == btnEquip.getId()) {
            if (account.equipItem(selectedItem) == true) {
                account.getInventory().removeItem(selectedItem);
                selectedItem = null;
                oldSelectedItem = null;
                btnEquip.setEnabled(false);
                TextView tv = (TextView) findViewById(R.id.textView_item_name);
                tv.setText(getString(R.string.text_no_item_selected));
                ((TextView) findViewById(R.id.textView_stat_names)).setText("");
                ((TextView) findViewById(R.id.textView_item_stats)).setText("");

                ImageView iv = (ImageView) findViewById(R.id.imageView_item_pic);
                iv.setBackgroundResource(0);
                updateInventory();
            } else
                Toaster.makeToast(this, getString(R.string.text_cannot_equip_item));
        } else {
            newItemSelected(v);
            selectedItem = account.getInventory().getItem(v.getId() - 0xff000);
            btnEquip.setEnabled(true);
            displayStats(v.getId() - 0xff000, false);
        }
    }

    public void updateInventory() {
        (findViewById(R.id.inventory_scroll_view)).setFadingEdgeLength(100);
        account = Account.get();
        displayItems(account.getInventory().getItems(), 1);
    }

    public void updateEquipment() {
        account = Account.get();
        if (account != null) {
            String type;
            int id = 0;
            ArrayList<Equipment> equipment = account.getEquipped();
            ArrayList<Integer> totalAttributes = account.getTotalAttributes().get();
            String att = "";
            for (int i = 0; i < totalAttributes.size(); i++) {
                att += Integer.toString(totalAttributes.get(i)) + "\n";     // Put all stats into a string
            }
            ((TextView) findViewById(R.id.item_stats)).setText(att);

            for (int i = 0; i < equipment.size(); i++) {
                type = equipment.get(i).getType();
                if (type.equals("Helm")) {
                    id = R.id.pic_head;
                } else if (type.equals("Armour")) {
                    id = R.id.pic_chest;
                } else if (type.equals("Leggings")) {
                    id = R.id.pic_legs;
                } else {
                    if (id != R.id.pic_wpn2) {
                        id = R.id.pic_wpn1;
                    }
                }
                setTextViewColour(id, ItemManager.getRarityColour(equipment.get(i)));
                ((TextView) findViewById(id)).setText("Level\n" + equipment.get(i).getLevel());
                if (id == R.id.pic_wpn1) id = R.id.pic_wpn2;
            }
        }
    }

    public void displayStats(int i, boolean equip) {
        ArrayList<Item> items;
        if (equip == true) {
            items = new ArrayList<Item>();
            ArrayList<Equipment> equips = account.getEquipped();
            for (int index = 0; index < equips.size(); index++) items.add(equips.get(index));
        } else items = account.getInventory().getItems();

        if (i < items.size()) {
            Item item = items.get(i);
            ArrayList<Integer> att;
            String statNames = getString(R.string.text_stats);
            String statData = "";
            if (item != null) {
                ImageView iv = (ImageView) findViewById(R.id.imageView_item_pic);
                iv.setBackgroundResource(R.drawable.double_lined);
                if (item.getClass() == Equipment.class || item.getClass() == Imbue.class) {
                    if (item.getClass() == Equipment.class)
                        att = ((Equipment) item).getAttributes().get();
                    else att = ((Imbue) item).getAttributes().get();
                    for (int x = 0; x < att.size(); x++)
                        statData += Integer.toString(att.get(x)) + "\n";      // Put all statistics into a String
                } else if (item.getClass() == Potion.class) {
                    statNames = "Health Points:\nAction Points:\nCount:";
                    if (item.getType().equals("HP")) {
                        statData = ((Potion) item).getAmount() + "\n0\n" + ((Potion) item).getCount(); // 0 AP
                    } else
                        statData = "0\n" + ((Potion) item).getAmount() + "\n" + ((Potion) item).getCount(); // 0 HP

                } else if (item.getClass() == UpgradeStone.class) {
                    statNames = "Count:";
                    statData = Integer.toString(((UpgradeStone) item).getCount());
                }

                ((TextView) findViewById(R.id.textView_item_name)).setText(item.getName());     // Set name
                ((TextView) findViewById(R.id.textView_stat_names)).setText(statNames);         // Set stats
                ((TextView) findViewById(R.id.textView_item_stats)).setText(statData);          // Set stat data
            }
        }
    }

    public void onEquipSelected(View v) {
        ArrayList<Equipment> equipment = account.getEquipped();
        selectedItemId = v.getId();
        int index = 0;
        int weapon = 0;
        String type = "Weapon";
        switch (v.getId()) {
            case (R.id.pic_head):
                type = "Helm";
                break;
            case (R.id.pic_chest):
                type = "Armour";
                break;
            case (R.id.pic_legs):
                type = "Leggings";
                break;
            case (R.id.pic_wpn1):
                weapon = R.id.pic_wpn1 - R.id.pic_wpn2;
                break;
            case (R.id.pic_wpn2):
                weapon = R.id.pic_wpn2 - R.id.pic_wpn1;
                break;
        }
        while (index < equipment.size()) {
            if (equipment.get(index).getType().equals(type)) {
                if (weapon > 0) {     // Second weapon selected
                    weapon = -1;    // Skips the first weapon in the list
                } else {
                    newEquipSelected(v);
                    selectedItem = equipment.get(index);
                    displayStats(index, true);
                    (findViewById(R.id.btn_unequip)).setEnabled(true);
                    return;
                }
            }
            index++;
        }
        // Only reach this point if no such item is equipped (Blank box was pressed)
        if (oldSelectedItem != null) {      // If an item was previous selected, restore its correct colours
            setTextViewColour(oldSelectedItem, ItemManager.getRarityColour(selectedItem));
            (findViewById(oldSelectedItem)).setBackgroundResource(R.drawable.single_lined_black);
            oldSelectedItem = null;
        }
        disableUnequipButton();
    }


    public void newEquipSelected(View v) {
        int newSelectedItem = v.getId();
        v.setBackgroundColor(Color.parseColor("#cccccc"));          // Select item
        setTextViewColour(newSelectedItem, "#000000");
        if (oldSelectedItem != null && oldSelectedItem != newSelectedItem) {    // New item has been selected
            v = findViewById(oldSelectedItem);
            v.setBackgroundResource(R.drawable.single_lined_black);             // Restore colours of the old selected item
            setTextViewColour(oldSelectedItem, ItemManager.getRarityColour(selectedItem));
        }
        oldSelectedItem = newSelectedItem;
    }

    public void unequipSelected(View v) {
        if (selectedItem != null) {         // Item must be selected to unequip it
            account.unequipItem(selectedItem);      // Unequip and add to inventory
            account.getInventory().addItem(selectedItem);
            // Dunno what this does?...
            // setTextViewColour(v.getId(), ItemManager.getRarityColour(selectedItem));
            selectedItem = null;
            oldSelectedItem = null;
            setContentView(R.layout.layout_equipment);
            updateEquipment();
        }
    }

    private void disableUnequipButton() {
        (findViewById(R.id.btn_unequip)).setEnabled(false);
        selectedItem = null;
        (findViewById(R.id.imageView_item_pic)).setBackgroundResource(0);
        TextView tv = (TextView) findViewById(R.id.textView_item_name);
        tv.setText(getString(R.string.text_no_item_selected));
        ((TextView) findViewById(R.id.textView_stat_names)).setText("");
        ((TextView) findViewById(R.id.textView_item_stats)).setText("");
    }
}
