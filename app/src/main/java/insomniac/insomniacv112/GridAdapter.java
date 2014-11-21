package insomniac.insomniacv112;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import Items.Equipment;
import Items.Imbue;
import Items.Item;
import Items.Potion;
import Items.UpgradeStone;

public final class GridAdapter extends BaseAdapter {

    ArrayList<Item> mItems;
    int mCount;
    Context mContext;
    int vSize;

    public GridAdapter(ArrayList<Item> item, int size, Context c) {
        mItems = item;
        mCount = item.size();
        vSize = size;
        mContext = c;
    }

    public void setViewSize(int i) {
        vSize = i;
    }

    public void setItems(ArrayList<Item> items) {
        mItems = items;
        mCount = items.size();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(final int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView,
                        final ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_item, null);
            textView.setLayoutParams(new GridView.LayoutParams(vSize, vSize));
        } else {
            textView = (TextView) convertView;
        }
        String symbol = "";
        if (mItems.get(position) == null) {
            textView.setBackgroundColor(Color.BLACK);
        } else {
            switch (mItems.get(position).getId().charAt(0)) {
                case ('c'):
                    textView.setBackgroundColor(Color.WHITE);
                    break;
                case ('u'):
                    textView.setBackgroundColor(Color.parseColor("#B2FF66"));
                    break;
                case ('r'):
                    textView.setBackgroundColor(Color.parseColor("#3399FF"));
                    break;
                case ('l'):
                    textView.setBackgroundColor(Color.parseColor("#CC0066"));
                    break;
                case ('a'):
                    textView.setBackgroundColor(Color.parseColor("#99FFFF"));
                    break;
            }
            if (mItems.get(position).getClass() == Equipment.class) {
                symbol = "E";
            } else if (mItems.get(position).getClass() == Potion.class) {
                symbol = "P";
            } else if (mItems.get(position).getClass() == Imbue.class) {
                symbol = "I";
            } else if (mItems.get(position).getClass() == UpgradeStone.class) {
                symbol = "U";
            }
        }
        textView.setText(symbol);
        return textView;
    }
}