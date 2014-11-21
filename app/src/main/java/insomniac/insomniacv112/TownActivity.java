package insomniac.insomniacv112;

import accounts.Account;
import model.NavigationActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TownActivity extends NavigationActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String feedback = getString(R.string.text_what_do);
        String save = getString(R.string.text_go_inn);
        String blacksmith = getString(R.string.text_go_blacksmith);
        String wild = getString(R.string.text_go_wild);
        String inventory = getString(R.string.text_open_inventory);
        super.onCreate(savedInstanceState);
        super.setText(feedback, save, blacksmith, wild, inventory);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case (R.id.btn_1):
                String s = "";
                if(Account.get() != null) {
                    if (Account.get().save(this) == true) s = getString(R.string.text_game_saved);
                } else s = getString(R.string.text_save_failed);
                Toast.makeText(this, s,Toast.LENGTH_LONG).show();
                break;
            case (R.id.btn_2):
                intent = new Intent(this, BlacksmithActivity.class);
                startActivity(intent);
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
