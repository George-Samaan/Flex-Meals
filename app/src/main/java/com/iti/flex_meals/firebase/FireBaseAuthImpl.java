package com.iti.flex_meals.firebase;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireBaseAuthImpl implements IFirebaseAuth {
    private final FirebaseAuth mAuth;
    private static final String FAV_MEAL_COLLECTION = "FavMealCollection";
    private static final String MEAL_PLAN_COLLECTION = "MealPlanCollection";
    private static final String TAG = "TAG";
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

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

    @Override
    public void uploadFavouriteItems(List<MealsItem> mealsItemList, OnCompleteListener<Void> onCompleteListener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        List<Task<Void>> tasks = new ArrayList<>();

        if (currentUser != null) {
            for (MealsItem favMealItem : mealsItemList) {
                DocumentReference docRef = firestore.collection(FAV_MEAL_COLLECTION)
                        .document(favMealItem.getIdMeal() + favMealItem.getUID());
                Task<Void> task = docRef.set(favMealItem.toMap());
                tasks.add(task);
            }
            Task<Void> allTasks = Tasks.whenAll(tasks);
            allTasks.addOnCompleteListener(onCompleteListener::onComplete);
        }
    }

    @Override
    public void uploadMealPlanItems(List<MealPlan> mealsPlanList, OnCompleteListener<Void> onCompleteListener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        List<Task<Void>> tasks = new ArrayList<>();
        if (currentUser != null) {
            for (MealPlan mealPlanItems : mealsPlanList) {
                DocumentReference docRef = firestore.collection(MEAL_PLAN_COLLECTION)
                        .document(mealPlanItems.getIdMeal() + mealPlanItems.getUID());
                Task<Void> task = docRef.set(mealPlanItems.toMap());
                tasks.add(task);
            }
            Task<Void> allTasks = Tasks.whenAll(tasks);
            allTasks.addOnCompleteListener(onCompleteListener::onComplete);
        }
    }

    @Override
    public void getFavouriteItems(String userId, OnCompleteListener<List<MealsItem>> onCompleteListener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            firestore.collection(FAV_MEAL_COLLECTION)
                    .whereEqualTo("UID", userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            List<MealsItem> mealsItems = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MealsItem mealsItem = MealsItem.fromMap(document.getData());
                                mealsItems.add(mealsItem);
                            }
                            onCompleteListener.onComplete(Tasks.forResult(mealsItems));
                        } else {
                            onCompleteListener.onComplete(Tasks.forException(Objects.requireNonNull(task.getException())));
                        }
                    });
        }
    }
}



