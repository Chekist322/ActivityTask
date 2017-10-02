package com.example.batrakov.activitytask;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String CAT_ARRAY = "cat array";
    private static final String RESTORE = "restore";
    private static final int GRID_ACT = 0;

    ImageButton mFirstIntentButton;
    ImageButton mSecondIntentButton;
    ImageButton mAddButton;
    ListView mListView;
    View mListHeader;
    CatAdapter mListAdapter;
    private ArrayList<Cat> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("stageMain", "onCreate");
        super.onCreate(savedInstanceState);
        setThings();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("stageMain", "ConfChanged");
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("stageMain", String.valueOf(mListData.isEmpty()));
            Intent intent = new Intent(this, MainActivity.class);
            intent.setAction(RESTORE);
            intent.putExtra(CAT_ARRAY, mListData);
            startActivity(intent);
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("stageMain", String.valueOf(mListData.isEmpty()));
            Intent intent = new Intent(this, MainActivity.class);
            intent.setAction(RESTORE);
            intent.putExtra(CAT_ARRAY, mListData);
            startActivity(intent);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("stageMain","onNewIntent");
        switch (intent.getAction()){
            case AddActivity.ADD:
                Log.i("stageMain","add");
                Cat cat = new Cat(intent.getStringExtra(AddActivity.NAME_KEY),
                        intent.getStringExtra(AddActivity.BREED_KEY),
                        intent.getStringExtra(AddActivity.AGE_KEY));
                if (mListData == null){
                    mListData = new ArrayList<>(0);
                }
                mListData.add(cat);
                mListAdapter.replaceData(mListData);
                break;
            case RESTORE:
                setThings();
                mListData = (ArrayList<Cat>) intent.getSerializableExtra(CAT_ARRAY);
                mListAdapter.replaceData(mListData);
                break;
            default:
                super.onNewIntent(intent);
                break;
        }
    }

    private void setThings(){
        setContentView(R.layout.activity_main);
        mFirstIntentButton = (ImageButton) findViewById(R.id.firstButton);
        mSecondIntentButton = (ImageButton) findViewById(R.id.secondButton);
        mAddButton = (ImageButton) findViewById(R.id.add);
        mListView = (ListView) findViewById(R.id.list);
        mListHeader = (LinearLayout) findViewById(R.id.listHeader);
        if (mListData == null){
            mListData = new ArrayList<>(0);
        }
        Log.i("stageMain", String.valueOf(mListData.isEmpty()));
        if (mListAdapter == null){
            mListAdapter = new CatAdapter(mListData);
        }
        mListView.setAdapter(mListAdapter);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddActivity();
            }
        });

        mFirstIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("stageMain", "pressed");

                ArrayList<String> stringArrayList = new ArrayList<String>();
                Intent intent = new Intent();
                intent.setAction("com.example.batrakov.activitytaskgrid.ACTION");
                for (int i = 0; i < mListData.size(); i++) {
                    stringArrayList.add(mListData.get(i).getName());
                    stringArrayList.add(mListData.get(i).getBreed());
                    stringArrayList.add(mListData.get(i).getAge());
                }
                intent.putStringArrayListExtra(CAT_ARRAY, stringArrayList);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    Log.i("stageMain", "started");
                    startActivityForResult(intent, GRID_ACT);
                }
            }
        });
    }

    private void createAddActivity(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    private class CatAdapter extends BaseAdapter{

        private ArrayList<Cat> mList;

        CatAdapter(ArrayList<Cat> aList){
            mList = aList;
        }

        public void replaceData(ArrayList<Cat> aList){
            mList = aList;
            if (!mList.isEmpty()){
                mListHeader.setVisibility(View.VISIBLE);
            } else {
                mListHeader.setVisibility(View.INVISIBLE);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.list_item, viewGroup, false);
            }
            final Cat cat = (Cat) getItem(i);

            TextView name = rowView.findViewById(R.id.name);
            name.setText(cat.getName());
            TextView breed = rowView.findViewById(R.id.breed);
            breed.setText(cat.getBreed());
            TextView age = rowView.findViewById(R.id.age);
            age.setText(cat.getAge());
            return rowView;
        }
    }
}
