package com.example.emergencyphone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emergencyphone.R;
import com.example.emergencyphone.model.PhoneItem;

import java.util.List;

public class PhoneListAdapter extends ArrayAdapter<PhoneItem> {

    private Context mContext;
    private int mResource;
    private List<PhoneItem> mPhoneItemList;

    public PhoneListAdapter(@NonNull Context context,
                            int resource,
                            @NonNull List<PhoneItem> phoneItemList) {
        super(context, resource, phoneItemList);
        this.mContext = context;
        this.mResource = resource;
        this.mPhoneItemList = phoneItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, parent, false);

        TextView titleTextView = view.findViewById(R.id.title_text_view);
        TextView numberTextView = view.findViewById(R.id.number_text_view);
        ImageView imageView = view.findViewById(R.id.image_view);

        PhoneItem phoneItem = mPhoneItemList.get(position);
        String title = phoneItem.title;
        String number = phoneItem.number;

        titleTextView.setText(title);
        numberTextView.setText(number);
        //imageView.setImageResource(R.drawable.xxx);

        return view;
    }
}
