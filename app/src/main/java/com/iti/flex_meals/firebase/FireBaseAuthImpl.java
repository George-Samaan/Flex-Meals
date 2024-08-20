package com.iti.flex_meals.firebase;

import android.util.Log;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;

public class FireBaseAuthImpl implements IFirebaseAuth {
    private final FirebaseAuth mAuth;

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
                        } else {
                            callback.onFailure("Failed to get user");
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        callback.onFailure(task.getException().getMessage());
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                    }
                });
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password, AuthResultCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            callback.onSuccess(user.getUid());
                        } else {
                            callback.onFailure("Failed to get user");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void signUnWithEmailAndPassword(String email, String password, AuthResultCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            callback.onSuccess(user.getUid());
                        } else {
                            callback.onFailure("Failed to get user");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }


    @Override
    public void getAuthToken(AuthTokenCallback callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            GetTokenResult tokenResult = task.getResult();
                            if (tokenResult != null) {
                                callback.onSuccess(tokenResult.getToken());
                            } else {
                                callback.onFailure("Failed to get token.");
                            }
                        } else {
                            callback.onFailure(task.getException().getMessage());
                        }
                    });
        } else {
            callback.onFailure("User is not logged in.");
        }
    }

    @Override
    public void getUserUid(AuthUserUidCallBack callBack) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            callBack.onSuccess(user.getUid());
        } else {
            callBack.onFailure("User is not logged in.");
        }
    }

}

