package model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Items.Equipment;
import Items.Imbue;
import Items.Item;
import Items.Potion;
import Items.UpgradeStone;
import insomniac.insomniacv112.R;


public class TabActivity extends TransitionActivity implements View.OnClickListener {
    public Item selectedItem;
    public Integer selectedItemId;
    public Integer oldSelectedItem;
    Context context;

    public void setContext(Context c){
        context = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void disableButton() {
        (findViewById(R.id.btn_equip)).setEnabled(false);
        selectedItem = null;
        oldSelectedItem = null;
        (findViewById(R.id.imageView_item_pic)).setBackgroundResource(0);
        TextView tv = (TextView) findViewById(R.id.textView_item_name);
        tv.setText(getString(R.string.text_no_item_selected));
        ((TextView) findViewById(R.id.textView_stat_names)).setText("");
        ((TextView) findViewById(R.id.textView_item_stats)).setText("");
    }

    // Set the text colour of a textView
    public void setTextViewColour(int id, String colour) {
        TextView tv = (TextView) findViewById(id);
        tv.setTextColor(Color.parseColor(colour));
    }

    // Set text, colour, and id for inflated TextViews
    public void setupTextView(int tvId, String text, Item item, int id) {
        TextView tv = (TextView) findViewById(tvId);
        tv.setText(text);
        tv.setTextColor(Color.parseColor(ItemManager.getRarityColour(item)));
        tv.setId(id);
    }

    public void displayItems(ArrayList<Item> items, double multiplier) {
        LinearLayout scrollBox = (LinearLayout) findViewById(R.id.linear_layout_items);
        scrollBox.removeAllViews();
        LinearLayout itemLayout;        // Layout of each item
        String level;
        Item item;
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i);
            if (item.getClass() == Equipment.class)
                level = Integer.toString(((Equipment) item).getLevel());
            else level = "1";       // Non equipment items have a level of 1
            itemLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_item, null);  // Create a new layout "item"
            itemLayout.setId(0xff000 + i);      // Set a unique ID
            itemLayout.setOnClickListener(this);
            scrollBox.addView(itemLayout);     // Must be added to container before params can change
            ViewGroup.LayoutParams params = itemLayout.getLayoutParams();
            params.height = 200;
            itemLayout.setLayoutParams(params);
            setupTextView(R.id.textView_inv_item_name, item.getName(), item, 0xf000 + i);
            setupTextView(R.id.textView_inv_item_type, item.getType(), item, 0xf00 + i);
            setupTextView(R.id.textView_inv_item_level, "Level: " + level, item, 0xf0000 + i);
            setupTextView(R.id.textView_inv_item_price, "Price: " + (int)(item.getPrice() * multiplier) + "g", item, 0xf00000 + i);
        }
    }

    public void onClick(View v){}


    public void newItemSelected(View v) {
        int newSelectedItem = v.getId();

        v.setBackgroundColor(Color.parseColor("#cccccc"));
        setTextViewColour(0xf00000 + newSelectedItem - 0xff000, "#000000");
        setTextViewColour(0xf0000 + newSelectedItem - 0xff000, "#000000");
        setTextViewColour(0xf000 + newSelectedItem - 0xff000, "#000000");       // Id is textView id + index
        setTextViewColour(0xf00 + newSelectedItem - 0xff000, "#000000");
        if (oldSelectedItem != null && oldSelectedItem != newSelectedItem) {    // New item has been selected
            v = findViewById(oldSelectedItem);
            v.setBackgroundResource(0);                                         // "Unselect item
            setTextViewColour(0xf00000 + oldSelectedItem - 0xff000, ItemManager.getRarityColour(selectedItem));
            setTextViewColour(0xf0000 + oldSelectedItem - 0xff000, ItemManager.getRarityColour(selectedItem));
            setTextViewColour(0xf000 + oldSelectedItem - 0xff000, ItemManager.getRarityColour(selectedItem));   // Restore rarity colours
            setTextViewColour(0xf00 + oldSelectedItem - 0xff000, ItemManager.getRarityColour(selectedItem));
        }
        oldSelectedItem = newSelectedItem;
    }

    public void displayStats(Item item) {
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
