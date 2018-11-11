package com.example.emergencyphone.model;

import java.util.Locale;

public class PhoneItem {

    public final long _id;
    public final String title;
    public final String number;
    public final String image;

    public PhoneItem(long _id, String title, String number, String image) {
        this._id = _id;
        this.title = title;
        this.number = number;
        this.image = image;
    }

    @Override
    public String toString() {
        String msg = String.format(
                Locale.getDefault(),
                "%s (%s)",
                this.title,
                this.number
        );
        return msg;
    }
}
