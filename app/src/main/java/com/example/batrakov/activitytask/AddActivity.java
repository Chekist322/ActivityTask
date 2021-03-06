package com.example.batrakov.activitytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 *
 * Created by batrakov on 27.09.17.
 */

public class AddActivity extends AppCompatActivity {

    /**
     * Intent key for name.
     */
    public static final String NAME_KEY = "name key";

    /**
     * Intent key for breed.
     */
    public static final String BREED_KEY = "breed key";

    /**
     * Intent key for age.
     */
    public static final String AGE_KEY = "age key";

    private EditText mName;
    private EditText mBreed;
    private EditText mAge;
    private View mView;

    @Override
    protected void onCreate(@Nullable Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.add_layout);
        mView = findViewById(R.id.layoutForSnackbar);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        mName = (EditText) findViewById(R.id.nameField);
        mBreed = (EditText) findViewById(R.id.breedField);
        mAge = (EditText) findViewById(R.id.ageField);
        Button register = (Button) findViewById(R.id.registerButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                updateMainActivity();
            }
        });
    }

    /**
     * Sending new item to MainActivity.
     */
    private void updateMainActivity() {
        String name = mName.getText().toString();
        String breed = mBreed.getText().toString();
        String age = mAge.getText().toString();
        if (checkName(name) && checkBreed(breed) && checkAge(age)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(NAME_KEY, name);
            intent.putExtra(BREED_KEY, breed);
            intent.putExtra(AGE_KEY, age);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * Check name field.
     * @param aName target string
     * @return check result
     */
    private boolean checkName(String aName) {
        if (!aName.matches("([a-zA-Zа-яА-Я]+\\s?)+")) {
            Snackbar snackbar = Snackbar.make(mView, getResources().getText(R.string.nameError), Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            view.setBackgroundColor(getColor(R.color.colorPrimary));
            snackbar.show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check breed field.
     * @param aBreed target string
     * @return check result
     */
    private boolean checkBreed(String aBreed) {
        if (!aBreed.matches("([a-zA-Zа-яА-Я]+\\s?)+")) {
            Snackbar snackbar = Snackbar.make(mView, getResources().getText(R.string.breedError), Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            view.setBackgroundColor(getColor(R.color.colorPrimary));
            snackbar.show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check age field.
     * @param aAge target string
     * @return check result
     */
    private boolean checkAge(String aAge) {
        if (!aAge.matches("\\d+")) {
            Snackbar snackbar = Snackbar.make(mView, getResources().getText(R.string.ageError), Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            view.setBackgroundColor(getColor(R.color.colorPrimary));
            snackbar.show();
            return false;
        } else {
            return true;
        }
    }
}
