package com.sinanrassam.covid19tracker;

import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinanrassam.covid19tracker.Tasks.FetchLocationsTask;
import com.sinanrassam.covid19tracker.Utils.PreferencesUtility;

public class LocationsFragment extends ListFragment {

    private SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        refreshLayout = view.findViewById(R.id.pullToRefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refreshLayout.setRefreshing(false);
            }
        });

        refresh();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        new FetchLocationsTask(this).execute(PreferencesUtility.getUser().getUsername());
    }
}