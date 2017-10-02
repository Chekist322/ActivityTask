package com.example.batrakov.activitytask;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String CAT_ARRAY = "cat array";
    private static final String CAT_INDEX = "cat index";

    private static final int GRID_ACT = 0;
    private static final int ADD_ACT = 1;

    private ImageButton mFirstIntentButton;
    private ImageButton mSecondIntentButton;
    private ImageButton mAddButton;
    private RecyclerView mListView;
    private View mListHeader;
    private CatAdapter mListAdapter;
    private ProgressBar mProgressBar;
    private ArrayList<Cat> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mFirstIntentButton = (ImageButton) findViewById(R.id.firstButton);
        mSecondIntentButton = (ImageButton) findViewById(R.id.secondButton);
        mAddButton = (ImageButton) findViewById(R.id.add);
        mListView = (RecyclerView) findViewById(R.id.list);
        mListHeader = findViewById(R.id.listHeader);

        if (savedInstanceState != null) {
            mListData = (ArrayList<Cat>) savedInstanceState.getSerializable(CAT_ARRAY);
        } else {
            mListData = new ArrayList<>();
        }

        mListAdapter = new CatAdapter(mListData);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setAdapter(mListAdapter);
        mListAdapter.replaceData(mListData);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.INVISIBLE);
                createAddActivity();
            }
        });

        mFirstIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> stringArrayList = new ArrayList<>();
                Intent intent = new Intent();
                intent.setAction(getResources().getString(R.string.customAction));
                for (int i = 0; i < mListData.size(); i++) {
                    stringArrayList.add(mListData.get(i).getName());
                    stringArrayList.add(mListData.get(i).getBreed());
                    stringArrayList.add(mListData.get(i).getAge());
                }
                intent.putStringArrayListExtra(CAT_ARRAY, stringArrayList);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, GRID_ACT);

                }
            }
        });

        mSecondIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> stringArrayList = new ArrayList<>();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                for (int i = 0; i < mListData.size(); i++) {
                    stringArrayList.add(mListData.get(i).getName());
                    stringArrayList.add(mListData.get(i).getBreed());
                    stringArrayList.add(mListData.get(i).getAge());
                }
                intent.putStringArrayListExtra(CAT_ARRAY, stringArrayList);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, GRID_ACT);

                }
            }
        });
    }

    private void createAddActivity() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_ACT);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CAT_ARRAY, mListData);
    }

    @Override
    protected void onResume() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mListView.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GRID_ACT) {
            if (resultCode == RESULT_OK) {
                int index = data.getIntExtra(CAT_INDEX, 0);
                Snackbar snackbar = Snackbar.make(mListHeader, getResources().getText(R.string.catPressed) + String.valueOf(index), Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                view.setBackgroundColor(getColor(R.color.colorPrimary));
                snackbar.show();
            }
        } else {
            if (resultCode == RESULT_OK) {
                Cat cat = new Cat(data.getStringExtra(AddActivity.NAME_KEY),
                        data.getStringExtra(AddActivity.BREED_KEY),
                        data.getStringExtra(AddActivity.AGE_KEY));
                mListData.add(cat);
                mListAdapter.replaceData(mListData);
            }
        }
    }

    private class CatHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mBreed;
        private TextView mAge;

        private CatHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mBreed = itemView.findViewById(R.id.breed);
            mAge = itemView.findViewById(R.id.age);
        }

        void bindView(Cat aCat) {
            mName.setText(aCat.getName());
            mBreed.setText(aCat.getBreed());
            mAge.setText(aCat.getAge());
        }
    }

    private class CatAdapter extends RecyclerView.Adapter<CatHolder> {

        private ArrayList<Cat> mList;

        CatAdapter(ArrayList<Cat> aList) {
            mList = aList;
        }

        void replaceData(ArrayList<Cat> aList) {
            mList = aList;
            if (!mList.isEmpty()) {
                mListHeader.setVisibility(View.VISIBLE);
            } else {
                mListHeader.setVisibility(View.INVISIBLE);
            }
            notifyDataSetChanged();
        }

        @Override
        public CatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new CatHolder(rowView);
        }

        @Override
        public void onBindViewHolder(CatHolder holder, int position) {
            Cat cat = mList.get(position);
            holder.bindView(cat);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }


}
