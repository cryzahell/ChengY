package com.ox.chengystudio.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by admin on 2016/12/26.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected final Activity mActivity;
    protected final LayoutInflater mInflater;
    private List<T> mList = new ArrayList<>();

    public BaseListAdapter(Activity activity) {
        mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mGetView(position, convertView, parent);
    }

    public abstract View mGetView(int position, View convertView, ViewGroup parent);

    public void add(T entity) {
        add(entity, -1);
    }

    public void add(T entity, int index) {
        if (index > 0) {
            mList.add(index, entity);
        } else {
            mList.add(entity);
        }
    }

    public void remove(T entity) {
        mList.remove(entity);
        notifyDataSetChanged();
    }

    public void refresh(Collection<? extends T> collection) {
        mList.clear();
        addAll(collection);
    }

    public void addAll(Collection<? extends T> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mList;
    }
}
