package apextechies.theferiwala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.firebase.digitsmigrationhelpers.AuthMigrator;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import apextechies.theferiwala.R;


/**
 * Created by Shankar on 4/1/2018.
 */

public class LoginActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 111;
   private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initWidgit();


    }

    private void initWidgit() {
     //   checkSession();
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(new Intent(LoginActivity.this, EmailLogin.class));
                    finish();
                }
            }
        };
        timer.start();
    }

    private void checkSession() {
            AuthMigrator.getInstance().migrate(true).addOnSuccessListener(this,
                    new OnSuccessListener() {

                        @Override
                        public void onSuccess(Object o) {
                            FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                            if (u != null) {
                                uid = u.getUid();
                                callApi(u.getPhoneNumber());

                            } else {
                                startActivityForResult(
                                        AuthUI.getInstance()
                                                .createSignInIntentBuilder()
                                                .setProviders(
                                                        //  Arrays.asList(
                                                        Collections.singletonList(
                                                                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                                        )
                                                )
                                                .setTheme(R.style.LoginTheme)
                                                .setLogo(R.mipmap.ic_launcher)
                                                .build(),
                                        RC_SIGN_IN);
                            }
                        }
                    }).addOnFailureListener(this,
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
        }

    private void callApi(String phoneNumber) {
       /* String mobile = phoneNumber.replace("+91","");
        JsonObject jsonObject = new JsonObject();
        retrofitDataProvider.login(mobile, new DownlodableCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel result) {
                ClsGeneral.setPreferences(LoginActivity.this, ConstantValue.ID, result.getData().get(0).getId());
                ClsGeneral.setPreferences(LoginActivity.this, ConstantValue.MOBILE, result.getData().get(0).getPhone());
                if (result.getData().get(0).getStatus().equals("0")){
                    finish();
                }else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(String error) {

            }

            @Override
            public void onUnauthorized(int errorNumber) {

            }
        });*/

       startActivity(new Intent(LoginActivity.this, MainActivity.class));
       finish();
    }



   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == RC_SIGN_IN) {
           IdpResponse response = IdpResponse.fromResultIntent(data);
           if (resultCode == ResultCodes.OK) {
               FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
               uid= u.getUid();
               callApi(response.getPhoneNumber());

           } else {
               // Sign in failed
               if (response == null) {
                   // User pressed back button
                   Log.e("Login", "Login canceled by User");
               }
               if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
               }
               if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                   Log.e("Login", "Unknown Error");
               }
           }
           Log.e("Login", "Unknown sign in response");

       }
   }

}
