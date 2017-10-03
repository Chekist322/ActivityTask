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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.batrakov.activitytask.CustomView;

import java.util.ArrayList;

/**
 * Main app activity.
 * Allows to browsing cat list and provide access to  AddActivity.
 */
public class MainActivity extends AppCompatActivity {

    private static final String CAT_ARRAY = "cat array";
    private static final String CAT_INDEX = "cat index";
    private static final String CUSTOM_ACTION = "com.example.batrakov.activitytaskgrid.ACTION";

    private static final int GRID_ACT = 0;
    private static final int ADD_ACT = 1;

    private RecyclerView mListView;
    private View mListHeader;
    private CatAdapter mListAdapter;
    private ProgressBar mProgressBar;
    private ArrayList<Cat> mListData;

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_main);
        final CustomView customView = (CustomView) this.findViewById(R.id.customView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mListView = (RecyclerView) findViewById(R.id.list);
        mListHeader = findViewById(R.id.listHeader);

        if (aSavedInstanceState != null) {
            if (aSavedInstanceState.getSerializable(CAT_ARRAY) != null) {
                if (aSavedInstanceState.getSerializable(CAT_ARRAY) instanceof ArrayList) {
                    mListData = (ArrayList<Cat>) aSavedInstanceState.getSerializable(CAT_ARRAY);
                }
            }
        } else {
            mListData = new ArrayList<>();
        }
        mListAdapter = new CatAdapter(mListData);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setAdapter(mListAdapter);
        mListAdapter.replaceData(mListData);


        System.out.println(customView);

//        customView.setFirstButtonOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View aView) {
//                ArrayList<String> stringArrayList = new ArrayList<>();
//                Intent intent = new Intent();
//                intent.setAction(CUSTOM_ACTION);
//                for (int i = 0; i < mListData.size(); i++) {
//                    stringArrayList.add(mListData.get(i).getName());
//                    stringArrayList.add(mListData.get(i).getBreed());
//                    stringArrayList.add(mListData.get(i).getAge());
//                }
//                intent.putStringArrayListExtra(CAT_ARRAY, stringArrayList);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, GRID_ACT);
//
//                }
//            }
//        });
//
//        customView.setSecondButtonOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View aView) {
//                ArrayList<String> stringArrayList = new ArrayList<>();
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                for (int i = 0; i < mListData.size(); i++) {
//                    stringArrayList.add(mListData.get(i).getName());
//                    stringArrayList.add(mListData.get(i).getBreed());
//                    stringArrayList.add(mListData.get(i).getAge());
//                }
//                intent.putStringArrayListExtra(CAT_ARRAY, stringArrayList);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, GRID_ACT);
//
//                }
//            }
//        });
//
//        customView.setThirdButtonOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View aView) {
//                mProgressBar.setVisibility(View.VISIBLE);
//                mListView.setVisibility(View.INVISIBLE);
//                createAddActivity();
//            }
//        });
    }

    /**
     * Intent for creating AddActivity.
     */
    private void createAddActivity() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_ACT);
    }

    @Override
    protected void onSaveInstanceState(Bundle aOutState) {
        super.onSaveInstanceState(aOutState);
        aOutState.putSerializable(CAT_ARRAY, mListData);
    }

    @Override
    protected void onResume() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mListView.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aData) {
        if (aRequestCode == GRID_ACT) {
            if (aResultCode == RESULT_OK) {
                int index = aData.getIntExtra(CAT_INDEX, 0);
                Snackbar snackbar = Snackbar.make(mListHeader, getResources().getText(R.string.catPressed)
                        + String.valueOf(index), Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                view.setBackgroundColor(getColor(R.color.colorPrimary));
                snackbar.show();
            }
        } else {
            if (aResultCode == RESULT_OK) {
                Cat cat = new Cat(aData.getStringExtra(AddActivity.NAME_KEY),
                        aData.getStringExtra(AddActivity.BREED_KEY),
                        aData.getStringExtra(AddActivity.AGE_KEY));
                mListData.add(cat);
                mListAdapter.replaceData(mListData);
            }
        }
    }

    /**
     * Holder for RecyclerView Adapter.
     */
    private final class CatHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mBreed;
        private TextView mAge;

        /**
         * Constructor.
         * @param aItemView item view
         */
        private CatHolder(View aItemView) {
            super(aItemView);
            mName = itemView.findViewById(R.id.name);
            mBreed = itemView.findViewById(R.id.breed);
            mAge = itemView.findViewById(R.id.age);
        }

        /**
         * View filling.
         * @param aCat cat from list
         */
        void bindView(Cat aCat) {
            mName.setText(aCat.getName());
            mBreed.setText(aCat.getBreed());
            mAge.setText(aCat.getAge());
        }
    }

    /**
     * Adapter for RecyclerView.
     */
    private class CatAdapter extends RecyclerView.Adapter<CatHolder> {

        private ArrayList<Cat> mList;

        /**
         * Constructor.
         * @param aList target list for fill.
         */
        CatAdapter(ArrayList<Cat> aList) {
            mList = aList;
        }

        /**
         * List updating.
         * @param aList new target list.
         */
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
        public CatHolder onCreateViewHolder(ViewGroup aParent, int aViewType) {
            View rowView = LayoutInflater.from(aParent.getContext()).inflate(R.layout.list_item, aParent, false);
            return new CatHolder(rowView);
        }

        @Override
        public void onBindViewHolder(CatHolder aHolder, int aPosition) {
            Cat cat = mList.get(aPosition);
            aHolder.bindView(cat);
        }

        @Override
        public long getItemId(int aIndex) {
            return aIndex;
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }


}
