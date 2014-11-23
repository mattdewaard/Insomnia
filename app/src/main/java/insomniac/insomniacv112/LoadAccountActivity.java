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
import model.TransitionActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
    public void onResume() {
        super.onResume();
        updateSavedAccounts();
    }

    private void updateSavedAccounts() {
        LinearLayout savedAccounts = (LinearLayout) findViewById(R.id.save_box);
        savedAccounts.removeAllViews();
        File accountNames = new File(this.getFilesDir(), "character_names");
        TextView textViewAccount;
        try {
            BufferedReader br = new BufferedReader(new FileReader(accountNames));
            int i = 0;
            while (br.ready()) {
                textViewAccount = (TextView) LayoutInflater.from(this).inflate(R.layout.text_view_account_name, null);
                textViewAccount.setText(br.readLine());
                textViewAccount.setId(i);
                savedAccounts.addView(textViewAccount);
                textViewAccount.setGravity(Gravity.CENTER_VERTICAL);
                ViewGroup.LayoutParams params = textViewAccount.getLayoutParams();
                params.height = 120;
                textViewAccount.setLayoutParams(params);
                textViewAccount.setPadding(20, 0, 0, 0);
                textViewAccount.setAlpha(.75f);
                i++;
            }
            if (i == 0) { // no accounts
                accountNames.delete();
            }
        } catch (Exception e) {
            Log.d("Reading accounts error", e.toString());
        }
        if (accountNames.length() == 0) {
            textViewAccount = (TextView) LayoutInflater.from(this).inflate(
                    R.layout.text_view_account_name, null);
            textViewAccount.setText(getString(R.string.text_no_saves));
            textViewAccount.setAlpha(0.75f);
            textViewAccount.setClickable(false);
            savedAccounts.addView(textViewAccount);
        }
    }

    @Override
    public void onClick(View v) {
        EditText pinBox = (EditText) findViewById(R.id.editText_load_pin);
        String pin = pinBox.getText().toString();
        if (accountName == null || accountName == "") {
            Toaster.makeToast(this, getString(R.string.text_select_account));
        } else {
            File account = new File(this.getFilesDir(), accountName);
            String actualPin = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(account));
                br.readLine();
                actualPin = br.readLine();      // Second line is PIN
            } catch (Exception e) {
                Log.d("Load/delete error", e.toString());
            }
            if (actualPin.equals(pin) == false) {
                Toaster.makeToast(this, getString(R.string.text_invalid_pin));
            } else {
                pinBox.setText("");
                switch (v.getId()) {
                    case (R.id.btn_delete):
                        AccountManager.deleteAccount(this, accountName);
                        updateSavedAccounts();
                        break;
                    case (R.id.btn_signin):
                        if (AccountManager.getAccount(this, accountName, pin) != null) {
                            oldSelectedAccount = null;
                            Intent intent = new Intent(this, TownActivity.class);
                            startActivity(intent);
                        } else Toaster.makeToast(this, getString(R.string.text_unable_sign_in));
                        break;
                }
            }
        }
    }

    public void accountSelected(View v) {
        int newSelectedAccount;
        try {
            TextView tv = (TextView) v;
            accountName = tv.getText().toString();
            tv.setBackgroundColor(Color.parseColor("#cccccc"));
            tv.setTextColor(Color.parseColor("#000000"));
            newSelectedAccount = tv.getId();
            if (oldSelectedAccount != null
                    && oldSelectedAccount != newSelectedAccount) {
                tv = (TextView) findViewById(oldSelectedAccount);
                tv.setBackgroundResource(0);
                tv.setTextColor(Color.parseColor("#ffffff"));
            }
            oldSelectedAccount = newSelectedAccount;
        } catch (Exception e) {
            Log.d("Account selected error", e.toString());
        }
    }
}
