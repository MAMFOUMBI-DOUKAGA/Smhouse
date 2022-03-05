package fr.furtif.smhouse.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mysql.cj.util.StringUtils;

import java.util.Locale;

import fr.furtif.smhouse.R;
import fr.furtif.smhouse.model.User;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mWelcomeUser;
    private Button mBoutonCommencer;

    private User mNameUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mWelcomeUser = findViewById(R.id.start_activity_textview_welcome_user);
        mBoutonCommencer = findViewById(R.id.start_button_commencer);
        mBoutonCommencer.setOnClickListener(this);
        mWelcomeUser.setText(mNameUser.getName().toUpperCase(Locale.ROOT) +" bienvenue Sur notre application!");

    }

    @Override
    public void onClick(View view) {

    }
}
