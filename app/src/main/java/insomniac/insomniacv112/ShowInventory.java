package insomniac.insomniacv112;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Items.Equipment;
import Items.Imbue;
import Items.Item;
import Items.Potion;
import Items.UpgradeStone;
import accounts.Account;
import model.Attributes;
import model.Toaster;


public class ShowInventory extends TransitionActivity implements View.OnClickListener {
    Item item;

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
                    setContentView(R.layout.layout_show_inv);
                    generateInventory();
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
        Button b = (Button) findViewById(R.id.btn_equip);
        b.setOnClickListener(this);
    }

    public void onClick(View v) {
        System.out.println("Hello");
        Account account = Account.get();
        boolean b;
        b = account.equipItem(item);
        System.out.println(b);
        if (b == true) {
            account.getInventory().removeItem(item);
            item = null;
            Button btnEquip = (Button) findViewById(R.id.btn_equip);
            btnEquip.setEnabled(false);
            TextView tv = (TextView) findViewById(R.id.textView_item_name);
            tv.setText(getString(R.string.text_no_items));
            tv = (TextView) findViewById(R.id.textView_stat_names);
            tv.setText("");
            tv = (TextView) findViewById(R.id.textView_item_stats);
            tv.setText("");
            ImageView iv = (ImageView) findViewById(R.id.imageView_item_pic);
            iv.setBackgroundResource(0);
            generateInventory();
        } else
            Toaster.makeToast(this, getString(R.string.text_cannot_equip_item));
    }

    public void showStats(int i) {
        Account account = Account.get();
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

    public void generateInventory() {

        GridView gv = (GridView) findViewById(R.id.gridView_items);

        Account account = Account.get();
        ArrayList<Item> items = account.getInventory().getItems();
        gv.setFadingEdgeLength(100);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Button btnEquip = (Button) findViewById(R.id.btn_equip);
                showStats(position);
                btnEquip.setEnabled(true);
                item = Account.get().getInventory().getItems().get(position);
            }
        });
        while (items.size() < 32) items.add(null);
        gv.setAdapter(new GridAdapter(items, getScreenWidth() / 5, this));
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

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        return display.getWidth(); // gets actual usable height
    }
}
