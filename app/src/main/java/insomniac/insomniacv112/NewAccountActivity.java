package insomniac.insomniacv112;

import Items.Equipment;
import Items.Potion;
import model.DataSimpleton;
import model.ItemManager;
import model.Toaster;
import accounts.Account;
import accounts.AccountManager;
import model.TransitionActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class NewAccountActivity extends TransitionActivity implements
        OnClickListener {
    String classType;
    EditText textBoxAccount;
    EditText textBoxPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        textBoxAccount = (EditText) findViewById(R.id.edit_text_account_name);
        textBoxPin = (EditText) findViewById(R.id.edit_text_pin);
        Button create = (Button) findViewById(R.id.btn_create_account);
        create.setOnClickListener(this);
        RadioButton radioWarrior = (RadioButton) findViewById(R.id.radio_warrior);
        RadioButton radioHunter = (RadioButton) findViewById(R.id.radio_hunter);
        RadioButton radioWizard = (RadioButton) findViewById(R.id.radio_wizard);
        radioWarrior.setOnClickListener(this);
        radioHunter.setOnClickListener(this);
        radioWizard.setOnClickListener(this);
        classType = "warrior";
    }

    @Override
    public void onPause() {
        super.onPause();
        DataSimpleton.clear();
        DataSimpleton sim = DataSimpleton.get();
        sim.addData(textBoxAccount.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        DataSimpleton sim = DataSimpleton.get();
        textBoxAccount.setText(sim.getData());
        DataSimpleton.clear();
    }

    @Override
    public void onClick(View view) {
        EditText textBoxName = (EditText) findViewById(R.id.edit_text_account_name);
        EditText textBoxPin = (EditText) findViewById(R.id.edit_text_pin);
        switch (view.getId()) {
            case (R.id.btn_create_account):
                String name = textBoxName.getText().toString();
                String pin = textBoxPin.getText().toString();
                if (name.length() == 0)
                    Toaster.makeToast(this, getString(R.string.text_enter_name));
                else {
                    if (pin.length() != 4) {
                        Toaster.makeToast(this, getString(R.string.text_pin_length));
                    } else {
                        if (classType != null || classType != "") {
                            if (AccountManager.registerAccount(this, name, pin,
                                    classType) == true) {
                                if (AccountManager.getAccount(this, name, pin) != null){
                                    // TEST DATA
                                    Account account = AccountManager.getAccount(this, name, pin);
                                    Potion p = (Potion) ItemManager.getItem("c001");
                                    p.increaseCount();
                                    p.increaseCount();
                                    account.addItem(p);
                                    account.addItem(ItemManager.getItem("u002"));
                                    account.addItem(ItemManager.getItem("r003"));
                                    account.addItem(ItemManager.getItem("l004"));
                                    Equipment equip = (Equipment) ItemManager.getItem("a005");
                                    equip.upgrade();
                                    equip.upgrade();
                                    account.addItem(equip);
                                    account.addItem(ItemManager.getItem("c006"));
                                    account.addItem(ItemManager.getItem("u007"));
                                    account.addItem(ItemManager.getItem("r008"));
                                    account.addItem(ItemManager.getItem("l009"));
                                    account.addItem(ItemManager.getItem("a010"));
                                    account.addItem(ItemManager.getItem("l017"));
                                    account.addItem(ItemManager.getItem("u017"));
                                    ////////////////

                                    textBoxName.setText("");
                                    textBoxPin.setText("");
                                    Intent intent = new Intent(this, TownActivity.class);
                                    startActivity(intent);
                                } else Log.d("New account failed","");
                            }
                        } else {
                            Toaster.makeToast(this, "Please select a class");
                        }
                    }
                }
                break;
            case (R.id.radio_warrior):
                classType = "warrior";
                break;
            case (R.id.radio_hunter):
                classType = "hunter";
                break;
            case (R.id.radio_wizard):
                classType = "wizard";
                break;
        }
    }
}
