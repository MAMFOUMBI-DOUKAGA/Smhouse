package fr.furtif.smhouse.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import fr.furtif.smhouse.R;
import fr.furtif.smhouse.model.JSONParser;

public class EnregistrementActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextNomUser;
    private EditText mEditTextMail;
    private EditText mEditTextMotDePasse;
    private Button mBoutonEnregistrement;
    ProgressDialog dialog;
    JSONParser parser=new JSONParser();
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrement);

        mEditTextNomUser = findViewById(R.id.enreg_edittext_nom_page_enreg);
        mEditTextMail = findViewById(R.id.enreg_edittext_mail_page_enreg);
        mEditTextMotDePasse = findViewById(R.id.enreg_edittext_mot_de_passe_page_enreg);
        mBoutonEnregistrement = findViewById(R.id.enreg_button_enregistrement);

        mBoutonEnregistrement.setOnClickListener(this);
        mBoutonEnregistrement.setEnabled(false);

        mEditTextNomUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { // c'est ici que nous allons verifier l'entré de l'utilisateur
                String verif = mEditTextMail.getText().toString();
                String veriff = mEditTextMotDePasse.getText().toString();
                if(verif.matches("")){mBoutonEnregistrement.setEnabled(false); }
                else if (veriff.matches("")){mBoutonEnregistrement.setEnabled(false);}

                else  mBoutonEnregistrement.setEnabled(!editable.toString().isEmpty()); /*À chaque fois que l'utilisateur saisira une lettre,
                                                                     la méthode afterTextChanged sera appelée. Cela nous permettra de
                                                                     déterminer si l'utilisateur a commencé à saisir son prénom, et
                                                                     ainsi activer le bouton de démarrage de jeu.
                                                                     En un mot activé le bouton quand on commence à écrire*/
            }
        });

        mEditTextMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { // c'est ici que nous allons verifier l'entré de l'utilisateur
                String verif = mEditTextNomUser.getText().toString();
                String veriff = mEditTextMotDePasse.getText().toString();
                if(verif.matches("")){mBoutonEnregistrement.setEnabled(false); }
                else if (veriff.matches("")){mBoutonEnregistrement.setEnabled(false);}

                else  mBoutonEnregistrement.setEnabled(!editable.toString().isEmpty()); /*À chaque fois que l'utilisateur saisira une lettre,
                                                                     la méthode afterTextChanged sera appelée. Cela nous permettra de
                                                                     déterminer si l'utilisateur a commencé à saisir son prénom, et
                                                                     ainsi activer le bouton de démarrage de jeu.
                                                                     En un mot activé le bouton quand on commence à écrire*/
            }
        });

        mEditTextMotDePasse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { // c'est ici que nous allons verifier l'entré de l'utilisateur
                String verif = mEditTextMail.getText().toString();
                String veriff = mEditTextNomUser.getText().toString();
                if(verif.matches("")){mBoutonEnregistrement.setEnabled(false); }
                else if (veriff.matches("")){mBoutonEnregistrement.setEnabled(false);}

                else  mBoutonEnregistrement.setEnabled(!editable.toString().isEmpty()); /*À chaque fois que l'utilisateur saisira une lettre,
                                                                     la méthode afterTextChanged sera appelée. Cela nous permettra de
                                                                     déterminer si l'utilisateur a commencé à saisir son prénom, et
                                                                     ainsi activer le bouton de démarrage de jeu.
                                                                     En un mot activé le bouton quand on commence à écrire*/
            }
        });


    }


    @Override
    public void onClick(View view) {
        new Enregistrement().execute();
    }

    class Enregistrement extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog=new ProgressDialog(EnregistrementActivity.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<>();

            map.put("nom",mEditTextNomUser.getText().toString());
            map.put("mail",mEditTextMail.getText().toString());
            map.put("pass",mEditTextMotDePasse.getText().toString());

            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/login/enregistrement.php","GET",map);

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
                AlertDialog.Builder alert=new AlertDialog.Builder(EnregistrementActivity.this);
                alert.setMessage("Add done successfully");
                alert.setNeutralButton("ok",null);
                alert.show();
                Intent mainactivityIntent = new Intent(EnregistrementActivity.this, MainActivity.class );
                startActivity(mainactivityIntent);
                finish();
            }
            else
            {

                AlertDialog.Builder alert=new AlertDialog.Builder(EnregistrementActivity.this);
                alert.setMessage("Echec!!!");
                alert.setNeutralButton("ok",null);
                alert.show();
            }

        }
    }
}
