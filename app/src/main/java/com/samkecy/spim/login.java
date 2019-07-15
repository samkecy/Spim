package com.samkecy.spim;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Google  authentications
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button loginBtn;
    Button forgetPass;
    EditText pass, email;
    String email1, pass1;
    InternetConnection internetConnection = new InternetConnection();
    FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private ProgressDialog pDialog;
  //  private GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton ;


    // method to create new account
    public void createAccount() {

        forgetPass = (Button) findViewById(R.id.btnforgotPass);
        pass = (EditText) findViewById(R.id.edtPass);
        email = (EditText) findViewById(R.id.edtEmail);
        signInButton = (SignInButton)findViewById(R.id.sign_in_button);


        Button btnCreate;
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signUp.class);
                startActivity(intent);
            }
        });

    }

    // method for signin
    public void mainSignIn() {

        loginBtn = (Button) findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Log.d(TAG,"Login");

                String Email = email.getText().toString();
                String Pass = pass.getText().toString();

                if (!validate()) {

                    onLoginFailed();

                    return;
                }

                //loginBtn.setEnabled(false);

                final ProgressDialog progressDialog = new ProgressDialog(login.this,

                        R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                firebaseAuth = FirebaseAuth.getInstance();
                //authenticate user
                firebaseAuth.signInWithEmailAndPassword(Email, Pass)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error

                                    Toast.makeText(login.this, "User does not Exist", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {

                                    new android.os.Handler().postDelayed(

                                            new Runnable() {
                                                @Override
                                                public void run() {


                                                    onLoginSuccess();
                                                    pass.setText("");
                                                    progressDialog.dismiss();
                                                    Intent intent = new Intent(login.this, MainActivity.class);
                                                    startActivity(intent);


                                                }
                                            }, 3000);

                                }


                            }
                        });


            }
        });
    }

    public void onLoginFailed() {

        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginBtn.setEnabled(true);
    }


    // method to validate login detail
    public boolean validate() {

        boolean valid = true;


        email1 = email.getText().toString();
        pass1 = pass.getText().toString();

        if (email1.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }


        if (pass1.isEmpty()) {
            pass.setError("Please enter your password");

            valid = false;

        } else if (pass1.length() < 4 || pass1.length() > 20) {
            pass.setError("password must be between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }

    public void onLoginSuccess() {

        Toast.makeText(getBaseContext(), "Login Successfull", Toast.LENGTH_LONG).show();
        loginBtn.setEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkInternet();

    }


    public void checkInternet() {

        //Checks Internet Connection
        internetConnection.setContext(getApplicationContext());

        if (!internetConnection.checkConnection
                (this)) {
            AlertDialog alertDialog = new AlertDialog.Builder(login.this).create();
            alertDialog.setTitle("Your Data Is Off");
            alertDialog.setMessage(" \t \t \tPlease Turn On Your Data And Try Again");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
//
//            alertDialog.show();
            firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null) {
                // User is logged in
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            forgotPasss();
        mainSignIn();
            createAccount();

        }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public  void forgotPasss()
    {
        forgetPass = (Button) findViewById(R.id.btnforgotPass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), resetPassWord.class));
            }
        });
    }



}




