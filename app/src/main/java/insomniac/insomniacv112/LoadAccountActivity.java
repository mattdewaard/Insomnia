package insomniac.insomniacv112;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import model.Toaster;
import accounts.Account;
import accounts.AccountManager;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Build;

public class LoadAccountActivity extends TransitionActivity implements
        OnClickListener {
    Integer oldSelectedAccount;
    String accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_account);
        ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
        Button button1 = (Button) findViewById(R.id.btn_delete);
        button1.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.btn_signin);
        button2.setOnClickListener(this);
        sv.setFadingEdgeLength(100);
        updateSavedAccounts();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateSavedAccounts();
    }

    private void updateSavedAccounts() {
        LinearLayout savedAccounts = (LinearLayout) findViewById(R.id.save_box);
        savedAccounts.removeAllViews();
        File accountNames = new File(this.getFilesDir(), "character_names");
        try {
            BufferedReader br = new BufferedReader(new FileReader(accountNames));
            TextView textViewAccount;
            TextView spacer;
            int i = 0;
            while (br.ready()) {
                textViewAccount = (TextView) LayoutInflater.from(this).inflate(
                        R.layout.text_view_account_name, null);
                spacer = (TextView) LayoutInflater.from(this).inflate(
                        R.layout.spacer, null);
                textViewAccount.setText(br.readLine());
                textViewAccount.setId(i);
                savedAccounts.addView(textViewAccount);
                savedAccounts.addView(spacer);
                i++;
            }
            if (i == 0) { // no accounts
                textViewAccount = (TextView) LayoutInflater.from(this).inflate(
                        R.layout.text_view_account_name, null);
                textViewAccount.setText("No saved accounts");
                textViewAccount.setTextColor(Color.parseColor("#ffffff"));
                textViewAccount.setBackgroundDrawable(null);
                savedAccounts.addView(textViewAccount);
            }
        } catch (Exception e) {
            Log.d("Reading accounts error", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        EditText et = (EditText) findViewById(R.id.editText_load_pin);
        String pin = et.getText().toString();
        if (accountName == null || accountName == "") {
            Toaster.makeToast(this, getString(R.string.text_select_account));
        } else {
            File account = new File(this.getFilesDir(), accountName);
            String actualPin = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(account));
                br.readLine();
                actualPin = br.readLine();
            } catch (Exception e) {
                Log.d("Error in load/delete", e.toString());
            }
            Log.d(actualPin, pin);
            if (actualPin.equals(pin)) {
                et.setText("");
                switch (v.getId()) {
                    case (R.id.btn_delete):
                        try {
                            File characterNames = new File(this.getFilesDir(),
                                    "character_names");
                            BufferedReader br = new BufferedReader(new FileReader(
                                    characterNames));
                            ArrayList<String> names = new ArrayList<String>();
                            String temp;
                            while (br.ready()) {
                                temp = br.readLine();
                                if (temp.equals(accountName) == false) {
                                    names.add(temp);
                                }
                            }
                            br.close();
                            BufferedWriter bw = new BufferedWriter(new FileWriter(
                                    characterNames, false));
                            for (int i = 0; i < names.size(); i++) {
                                bw.write(names.get(i));
                                bw.newLine();
                            }
                            bw.close();
                            account.delete();
                        } catch (Exception e) {
                            Log.d("Error in load/delete", e.toString());
                        }
                        updateSavedAccounts();
                        break;
                    case (R.id.btn_signin):
                        oldSelectedAccount = null;
                        Account user = AccountManager.getAccount(this, accountName,
                                pin);
                        Intent intent = new Intent(this, TownActivity.class);
                        startActivity(intent);
                        break;
                }
            } else {
                Toaster.makeToast(this, getString(R.string.text_invalid_pin));
            }
        }
    }

    public void accountSelected(View v) {
        int newSelectedAccount;
        try {
            TextView tv = (TextView) v;
            accountName = tv.getText().toString();
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setBackgroundResource(R.drawable.single_lined_black);
            newSelectedAccount = tv.getId();
            if (oldSelectedAccount != null
                    && oldSelectedAccount != newSelectedAccount) {
                // This is now null
                tv = (TextView) findViewById(oldSelectedAccount);
                //
                Log.d(Integer.toString(oldSelectedAccount), Integer.toString(newSelectedAccount));
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setBackgroundResource(R.drawable.single_lined);
            }
            System.out.println("Test7");
            oldSelectedAccount = newSelectedAccount;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }
}
