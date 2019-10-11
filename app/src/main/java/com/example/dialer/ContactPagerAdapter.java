package com.example.dialer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ContactPagerAdapter extends FragmentStatePagerAdapter {
    private int numOfTabs;
    private Contact contact;

    public ContactPagerAdapter(@NonNull FragmentManager fm, int numOfTabs, Contact contact) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.contact = contact;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new NumbersFragment(contact.getNumbers());
            case 1: return new RecentsFragment(contact);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
