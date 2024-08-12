package com.iti.flex_meals.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseAuthImpl implements IFirebaseAuth {
    private final FirebaseAuth mAuth;;
    public FireBaseAuthImpl() {
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void signInWithGoogle(String idToken, AuthResultCallback callback) {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("TAG", "signInWithCredential:success");
                        if (user != null) {
                            callback.onSuccess(user.getUid());
                        }else {
                            callback.onFailure("Failed to get user");
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        callback.onFailure(task.getException().getMessage());
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                    }
                });
    }
}
