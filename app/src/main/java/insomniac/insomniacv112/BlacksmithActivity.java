package insomniac.insomniacv112;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import accounts.Account;
import model.NavigationActivity;


public class BlacksmithActivity extends NavigationActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String feedback = getString(R.string.text_welcome_blacksmith);
        String trade = getString(R.string.text_trade_items);
        String upgrade = getString(R.string.text_upgrade_equipment);
        String imbue = getString(R.string.text_imbue_equipment);
        super.onCreate(savedInstanceState);
        super.setText(feedback, trade, upgrade, imbue, "");
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case (R.id.btn_1):
                startActivity(new Intent(this, TradeActivity.class));
                break;
            case (R.id.btn_2):

                break;
            case (R.id.btn_3):
                intent = new Intent(this, WildernessActivity.class);
                startActivity(intent);
                break;
            default:    //R.id.btn_4
                intent = new Intent(this, InventoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
