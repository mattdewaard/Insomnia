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
import model.Toaster;
import model.TransitionActivity;


public class InventoryActivity extends TransitionActivity implements View.OnClickListener {
    Account account;
    Item selectedItem;
    Integer oldSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context c = this;
        final ActionBar tabBar = getActionBar();
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_show_inventory);
        tabBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                RelativeLayout container = (RelativeLayout) findViewById(R.id.fragment_container);
                LinearLayout ll;
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
        Button btnEquip = (Button) findViewById(R.id.btn_equip);
        btnEquip.setOnClickListener(this);
    }

    public void onClick(View v) {
        Button btnEquip = (Button) findViewById(R.id.btn_equip);
        if (v.getId() == btnEquip.getId()) {
            if (account.equipItem(selectedItem) == true) {
                account.getInventory().removeItem(selectedItem);
                selectedItem = null;
                btnEquip.setEnabled(false);
                TextView tv = (TextView) findViewById(R.id.textView_item_name);
                tv.setText(getString(R.string.text_no_item_selected));
                tv = (TextView) findViewById(R.id.textView_stat_names);
                tv.setText("");
                tv = (TextView) findViewById(R.id.textView_item_stats);
                tv.setText("");
                ImageView iv = (ImageView) findViewById(R.id.imageView_item_pic);
                iv.setBackgroundResource(0);
                oldSelectedItem = null;
                updateInventory();
            } else
                Toaster.makeToast(this, getString(R.string.text_cannot_equip_item));
        } else {
            newItemSelected(v);
            btnEquip.setEnabled(true);
            selectedItem = account.getInventory().getItem(v.getId() - 0xff000);
            displayStats(v.getId() - 0xff000);
        }
    }

    public void updateInventory() {
        account = Account.get();
        ScrollView sv = (ScrollView) findViewById(R.id.inventory_scroll_view);
        sv.setFadingEdgeLength(100);
        LinearLayout itemLayout = (LinearLayout) findViewById(R.id.linear_layout_items);
        ArrayList<Item> items = account.getInventory().getItems();
        itemLayout.removeAllViews();
        LinearLayout layout;
        TextView textName;
        TextView textType;
        Item item;
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i);
            String colour = getRarityColour(item);
            layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_item, null);  // Create a new layout "item"
            layout.setId(0xff000 + i);      // Set a unique ID
            layout.setOnClickListener(this);
            itemLayout.addView(layout);     // Must be added to container before params can change
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.height = 200;
            layout.setLayoutParams(params);
            textName = (TextView) findViewById(R.id.textView_inv_item_name);
            textName.setText(item.getName());
            textName.setTextColor(Color.parseColor(colour));
            textName.setId(0xf000 + i);
            textType = (TextView) findViewById(R.id.textView_inv_item_type);
            textType.setText(item.getType());
            textType.setTextColor(Color.parseColor(colour));
            textType.setId(0xf00 + i);

        }
    }

    public void updateEquipment() {
        int headIndex, bodyIndex, legIndex, wpn1Index, wpn2Index;
        Account account = Account.get();
        if (account != null) {
            ArrayList<Equipment> equipment = account.getEquipped();
            if (equipment != null) {

            }
            ArrayList<Integer> temp = account.getTotalAttributes().get();
            ArrayList<String> att = new ArrayList<String>();
            for (int i = 0; i < temp.size(); i++) {
                att.add(Integer.toString(temp.get(i)));
            }
            TextView tv = (TextView) findViewById(R.id.item_stats);
            tv.setText(att.get(0) + '\n' + att.get(1) + '\n' + att.get(2) + '\n' + att.get(3) + '\n' + att.get(4) + '\n' + att.get(5) + '\n' + att.get(6) + '\n' + att.get(7));
        }
    }

    public void displayStats(int i) {
        ArrayList<Item> items = account.getInventory().getItems();

        if (i < items.size()) {
            Item item = items.get(i);
            ArrayList<Integer> att;
            String names = "";
            String data = "";
            if (item != null) {
                ImageView iv = (ImageView) findViewById(R.id.imageView_item_pic);
                iv.setBackgroundResource(R.drawable.double_lined);
                if (item.getClass() == Equipment.class) {
                    att = ((Equipment) item).getAttributes().get();
                    names = "Health Points:\nAction Points:\nPhysical Damage:\nMagic Damage:\nPhysical Defence:\nMagic Defence:\nCritical Damage:\nSpeed:\nValue:";
                    data = att.get(0) + "\n" + att.get(1) + "\n" + att.get(2) + " \n" + att.get(3) + "\n" + att.get(4) + "\n" + att.get(5) + "\n" + att.get(6) + "\n" + att.get(7) + "\n" + item.getPrice() + "g";
                } else if (item.getClass() == Potion.class) {
                    names = "Health Points:\nAction Points:\nCount:\nValue:";
                    if (((Potion) item).getType().equals("HP")) {
                        data = ((Potion) item).getAmount() + "\n0\n" + ((Potion) item).getCount() + "\n" + item.getPrice();
                    } else {
                        data = "0\n" + ((Potion) item).getAmount() + "\n" + ((Potion) item).getCount() + "\n" + item.getPrice();
                    }
                } else if (item.getClass() == Imbue.class) {
                    att = ((Imbue) item).getAttributes().get();
                    names = "Type:\nPhysical Damage:\nMagic Damage:\nPhysical Defence:\nMagic Defence:\nCritical Damage:\nSpeed:\nValue:";
                    data = item.getType() + "\n" + att.get(2) + " \n" + att.get(3) + "\n" + att.get(4) + "\n" + att.get(5) + "\n" + att.get(6) + "\n" + att.get(7) + "\n" + item.getPrice() + "g";
                } else if (item.getClass() == UpgradeStone.class) {
                    names = "Type:\nRarity:\nCount:\nValue:";
                    data = item.getType() + "\n" + ((UpgradeStone) item).getRarity() + "\n" + ((UpgradeStone) item).getCount() + "\n" + item.getPrice();
                }

                TextView title = (TextView) findViewById(R.id.textView_item_name);
                title.setText(item.getName());
                TextView textNames = (TextView) findViewById(R.id.textView_stat_names);
                textNames.setText(names);
                TextView textData = (TextView) findViewById(R.id.textView_item_stats);
                textData.setText(data);
            }
        }
    }

    private void newItemSelected(View v) {
        int newSelectedItem;
        int i = v.getId() - 0xff000;
        try {
            LinearLayout ll = (LinearLayout) v;
            TextView tv1 = (TextView) findViewById(0xf000 + i); //R.id.textView_inv_item_name
            TextView tv2 = (TextView) findViewById(0xf00 + i); //R.id.textView_inv_item_type
            ll.setBackgroundColor(Color.parseColor("#cccccc"));
            tv1.setTextColor(Color.parseColor("#000000"));
            tv2.setTextColor(Color.parseColor("#000000"));
            newSelectedItem = ll.getId();
            if (oldSelectedItem != null
                    && oldSelectedItem != newSelectedItem) {
                ll = (LinearLayout) findViewById(oldSelectedItem);
                ll.setBackgroundResource(0);
                i = oldSelectedItem - 0xff000;
                tv1 = (TextView) findViewById(0xf000 + i);
                tv2 = (TextView) findViewById(0xf00 + i);
                tv1.setTextColor(Color.parseColor(getRarityColour(selectedItem)));
                tv2.setTextColor(Color.parseColor(getRarityColour(selectedItem)));

            }
            oldSelectedItem = newSelectedItem;
        } catch (Exception e) {
            Log.d("Item selected error", e.toString());
        }
    }

    private String getRarityColour(Item item){
        switch (item.getId().charAt(0)) {
            case ('u'):
                return "#B2FF66";
            case ('r'):
                return "#3399FF";
            case ('l'):
                return "#CC0066";
            case ('a'):
                return "#99FFFF";
            default:
                return "#ffffff";
        }
    }

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        return display.getWidth(); // gets actual usable height
    }
}
