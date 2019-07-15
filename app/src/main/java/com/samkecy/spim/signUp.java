package com.samkecy.spim;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class signUp extends AppCompatActivity {


// Create varaible

    EditText edtName, edtEmail, edtPhone, edtPassword;
    String name, email , phone , pass ;
    TextView tvTextView;
    Button btnCreate ;
    FirebaseAuth firebaseAuth;


    // form validation

public boolean validate() {
// assign variables

    Boolean valid = true;


    edtName = (EditText) findViewById(R.id.edtName);
    edtEmail = (EditText) findViewById(R.id.edtEmailA);
    edtPhone = (EditText) findViewById(R.id.edtPhone);
    edtPassword = (EditText) findViewById(R.id.edtPass);


      name = edtName.getText().toString();
      email = edtEmail.getText().toString();
      phone = edtPhone.getText().toString();
      pass = edtPassword.getText().toString();


    if (name.isEmpty()) {

        edtName.setError("Please enter your name");
        valid = false;
    }

    else
    {
        edtName.setError(null);
    }
    if ( email.isEmpty()|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        edtEmail.setError("Enter a valid email address");

        valid = false;

    }

        else
        {
            edtEmail.setError(null);
        }


        if (pass.isEmpty() || pass.length() <6  || pass.length() >10 )
        {

            edtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        else

        {
            edtPassword.setError(null);
        }

        if (phone.isEmpty())
        {
            edtPhone.setError("phone number required");

            valid = false;

        }

        else
        {
            edtPhone.setError(null);
        }

        return valid;

}


public void log()

{


    tvTextView = (TextView)findViewById(R.id.tvLoginLink);
    tvTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(signUp.this, login.class);
            startActivity(intent);
        }
    });
}

public void create()

{


    btnCreate = (Button) findViewById(R.id.btnLog);
    btnCreate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            if(!validate()){

                signUpFailed();
                return;

            }
            btnCreate.setEnabled(false);
            firebaseAuth = FirebaseAuth.getInstance();
            final ProgressDialog progressDialog = new ProgressDialog(signUp.this,

                    R.style.AppTheme);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account.......");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,pass)

                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){



                                // logic here .......


                                new android.os.Handler().postDelayed(

                                        new Runnable() {
                                            @Override
                                            public void run() {


                                                signUpSuccessfull();

                                                progressDialog.dismiss();
                                            }
                                        }, 3000);
                                signUpSuccessfull();
                                startActivity(new Intent(getApplicationContext(),login.class));
                                finish();
                            }
                            else{
                                 signUpFailed();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }





    });

}

public void signUpSuccessfull()

{

     setResult(RESULT_OK, null);
     finish();
}

public  void signUpFailed()


{

    Toast.makeText(getBaseContext(), "Registration Failed", Toast.LENGTH_LONG).show();
    btnCreate.setEnabled(true);

}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



      log();
      create();


        
    }
}
