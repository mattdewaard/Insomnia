package insomniac.insomniacv112;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import Items.Equipment;
import Items.Imbue;
import Items.Item;
import Items.Potion;
import Items.UpgradeStone;
import accounts.Account;
import model.Inventory;
import model.ItemManager;
import model.TabActivity;
import model.Toaster;
import model.TransitionActivity;


public class TradeActivity extends TabActivity implements View.OnClickListener {
    Account account;
    Inventory shopInventory;
    boolean state;      // True for buy, false for sell

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ActionBar tabBar = getActionBar();
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_show_inventory);
        tabBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        state = true;
        setContext(this);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                selectedItem = null;
                oldSelectedItem = null;
                if (tab.getPosition() == 0) {
                    setContentView(R.layout.layout_inventory);
                    ((Button) findViewById(R.id.btn_equip)).setText(R.string.text_buy);
                    state = true;
                    displayShop();
                } else {
                    setContentView(R.layout.layout_inventory);
                    ((Button) findViewById(R.id.btn_equip)).setText(R.string.text_sell);
                    state = false;
                    displayInventory();
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                setContentView(R.layout.activity_show_inventory);
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }
        };
        tabBar.addTab(tabBar.newTab().setText(getString(R.string.text_buy)).setTabListener(tabListener));
        tabBar.addTab(tabBar.newTab().setText(getString(R.string.text_sell)).setTabListener(tabListener));
        (findViewById(R.id.inventory_scroll_view)).setFadingEdgeLength(100);

    }

    @Override
    public void onClick(View v) {
        account = Account.get();
        String text = "";
        if (v.getId() == R.id.btn_equip) {
            if (state == true) {            // Buy item
                int result = account.getInventory().buyItem(selectedItem);
                if (result == 0) {
                    text = getString(R.string.text_reassure_buy);
                } else if (result == 2) {
                    Toaster.makeToast(this, getString(R.string.text_cannot_afford));
                } else Toaster.makeToast(this, getString(R.string.text_no_space));
            }
            if (text.equals(getString(R.string.text_reassure_buy)) || state == false) {
                if (state == false) text = getString(R.string.text_reassure_sell);
                AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
                messageBox.setMessage(text);
                messageBox.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (state == true) deselectItem();
                        else buyItem();
                    }
                });
                messageBox.setNegativeButton(R.string.text_cancel, null);
                messageBox.show();
            }
        } else {
            if (state == true) {
                selectItem(shopInventory.getItems().get(v.getId() - 0xff000), v);
            } else {
                selectItem(account.getInventory().getItem(v.getId() - 0xff000), v);
            }
        }
    }

    private void buyItem() {
        int gold = selectedItem.getPrice() / 2;
        account.removeItem(selectedItem);
        account.getInventory().addGold(gold);
        deselectItem();
        displayInventory();
    }

    private void deselectItem() {
        findViewById(selectedItemId).setBackgroundResource(0);
        setTextViewColour(0xf00000 + selectedItemId - 0xff000, ItemManager.getRarityColour(selectedItem));
        setTextViewColour(0xf0000 + selectedItemId - 0xff000, ItemManager.getRarityColour(selectedItem));
        setTextViewColour(0xf000 + selectedItemId - 0xff000, ItemManager.getRarityColour(selectedItem));   // Restore rarity colours
        setTextViewColour(0xf00 + selectedItemId - 0xff000, ItemManager.getRarityColour(selectedItem));
        disableButton();
    }

    private void selectItem(Item item, View v) {
        newItemSelected(v);
        selectedItem = item;
        selectedItemId = v.getId();
        displayStats(selectedItem);
        (findViewById(R.id.btn_equip)).setEnabled(true);
    }

    private void displayShop() {
        shopInventory = new Inventory(new ArrayList<String>(), 0);
        String id;
        for (int x = 0; x < 2; x++) {
            if (x == 0) id = "c0";
            else id = "u0";
            for (int i = 0; i < 18; i++) {
                if (i < 10) shopInventory.addItem(ItemManager.getItem(id + "0" + (char) (i + 48)));
                else shopInventory.addItem(ItemManager.getItem(id + "1" + (char) (i - 10 + 48)));
            }
        }
        ArrayList<Item> shopItems = shopInventory.getItems();
        displayItems(shopItems, 4);
    }

    private void displayInventory() {
        account = Account.get();
        displayItems(account.getInventory().getItems(), 0.5);
    }
}
