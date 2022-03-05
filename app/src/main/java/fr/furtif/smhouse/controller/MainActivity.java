package fr.furtif.smhouse.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;

import fr.furtif.smhouse.R;
import fr.furtif.smhouse.model.JSONParser;
import fr.furtif.smhouse.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNomUser;
    private EditText mMotDePasse;
    private Button mBoutonConnexion;
    private Button mBoutonEnregistrement;
    private Button mBoutonMotDePasseOublié;
    private User mNameUser = new User();

    ProgressDialog dialog;
    JSONParser parser=new JSONParser();

    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNomUser = (EditText) findViewById(R.id.main_edittext_nom);
        mMotDePasse = (EditText) findViewById(R.id.main_edittext_mot_de_passe);
        mBoutonConnexion = (Button) findViewById(R.id.main_button_connexion);
        mBoutonEnregistrement = (Button) findViewById(R.id.main_button_créer_compte);
        mBoutonMotDePasseOublié = (Button) findViewById(R.id.main_button_mot_de_passe_oublié);

        mBoutonConnexion.setOnClickListener(this);
        mBoutonEnregistrement.setOnClickListener(this);
        mBoutonMotDePasseOublié.setOnClickListener(this);
        mBoutonConnexion.setEnabled(false);
        mBoutonMotDePasseOublié.setEnabled(false);

        mMotDePasse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { // c'est ici que nous allons verifier l'entré de l'utilisateur
                String verif = mNomUser.getText().toString();
                if(verif.matches("")){mBoutonConnexion.setEnabled(false); }

                 else  mBoutonConnexion.setEnabled(!editable.toString().isEmpty()); /*À chaque fois que l'utilisateur saisira une lettre,
                                                                     la méthode afterTextChanged sera appelée. Cela nous permettra de
                                                                     déterminer si l'utilisateur a commencé à saisir son prénom, et
                                                                     ainsi activer le bouton de démarrage de jeu.
                                                                     En un mot activé le bouton quand on commence à écrire*/
            }
        });

        mNomUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { // c'est ici que nous allons verifier l'entré de l'utilisateur
                String verif = mMotDePasse.getText().toString();
                if(verif.matches("")){mBoutonConnexion.setEnabled(false); }

                else  mBoutonConnexion.setEnabled(!editable.toString().isEmpty()); /*À chaque fois que l'utilisateur saisira une lettre,
                                                                     la méthode afterTextChanged sera appelée. Cela nous permettra de
                                                                     déterminer si l'utilisateur a commencé à saisir son prénom, et
                                                                     ainsi activer le bouton de démarrage de jeu.
                                                                     En un mot activé le bouton quand on commence à écrire*/
            }
        });

        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    @Override
    public void onClick(View v) {

       if(v==mBoutonEnregistrement){
            Intent enregistrementIntent = new Intent(MainActivity.this, EnregistrementActivity.class );
            startActivity(enregistrementIntent);
        }
       if(v==mBoutonConnexion){
           new Log().execute();
        }


    }



    class Log extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<>();

            map.put("nom",mNomUser.getText().toString());
            map.put("pass",mMotDePasse.getText().toString());

            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/login/login.php","POST",map);

            try {
                success = object.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();

            if(success==1)
            {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Login done successfully");
                alert.setNeutralButton("ok",null);
                alert.show();
                Intent startactivityIntent = new Intent(MainActivity.this, StartActivity.class );
                startActivity(startactivityIntent);
                mNameUser.setName(mNomUser.getText().toString());
                finish();
            }
            else
            {

                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("No user with this name or password");
                alert.setNeutralButton("ok",null);
                alert.show();
            }

        }
    }

}