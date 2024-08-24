package com.iti.flex_meals.network.firebase;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.planFragment.model.MealPlan;

import java.util.ArrayList;
import java.util.List;

public class FireBaseAuthImpl implements IFirebaseAuth {
    private final FirebaseAuth mAuth;
    private static final String FAV_MEAL_COLLECTION = "FavMealCollection";
    private static final String MEAL_PLAN_COLLECTION = "MealPlanCollection";
    private static final String TAG = "TAG";
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Repository repository;


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
        if (currentUser != null) {
            String userUID = currentUser.getUid();
            CollectionReference favMealCollection = firestore.collection(FAV_MEAL_COLLECTION);
            // Query to find all documents where the UID matches the current user's UID
            favMealCollection.whereEqualTo("UID", userUID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<Task<Void>> deleteTasks = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        Task<Void> deleteTask = document.getReference().delete();
                        deleteTasks.add(deleteTask);
                    }
                    Tasks.whenAll(deleteTasks).addOnCompleteListener(deleteTask -> {
                        if (deleteTask.isSuccessful()) {
                            List<Task<Void>> uploadTasks = new ArrayList<>();
                            for (MealsItem favMealItem : mealsItemList) {
                                DocumentReference docRef = favMealCollection.document(favMealItem.getIdMeal() + userUID);
                                Task<Void> uploadTask = docRef.set(favMealItem.toMap());
                                uploadTasks.add(uploadTask);
                            }
                            Tasks.whenAll(uploadTasks).addOnCompleteListener(onCompleteListener::onComplete);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void uploadMealPlanItems(List<MealPlan> mealsPlanList, OnCompleteListener<Void> onCompleteListener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userUID = currentUser.getUid();
            CollectionReference mealPlanCollection = firestore.collection(MEAL_PLAN_COLLECTION);
            // Query to find all documents where the UID matches the current user's UID
            mealPlanCollection.whereEqualTo("UID", userUID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<Task<Void>> deleteTasks = new ArrayList<>();

                    for (DocumentSnapshot document : task.getResult()) {
                        Task<Void> deleteTask = document.getReference().delete();
                        deleteTasks.add(deleteTask);
                    }

                    Tasks.whenAll(deleteTasks).addOnCompleteListener(deleteTask -> {
                        if (deleteTask.isSuccessful()) {
                            List<Task<Void>> uploadTasks = new ArrayList<>();
                            for (MealPlan mealPlanItem : mealsPlanList) {
                                DocumentReference docRef = mealPlanCollection.document(mealPlanItem.getIdMeal() + userUID + mealPlanItem.getDaySelected());
                                Task<Void> uploadTask = docRef.set(mealPlanItem.toMap());
                                uploadTasks.add(uploadTask);
                            }
                            Tasks.whenAll(uploadTasks).addOnCompleteListener(onCompleteListener::onComplete);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void getFavouriteItems(String userId, OnCompleteListener<List<MealsItem>> onCompleteListener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            firestore.collection(FAV_MEAL_COLLECTION)
                    .whereEqualTo("UID", uid)
                    .get()
                    .addOnCompleteListener(task -> {
                        Log.d("TAGGGGGGGG", "getFavouriteItems: " + uid);
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d("TAGGGGGGGG", "getFavouriteItems: " + task.getResult().size());
                            List<MealsItem> mealsItems = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MealsItem mealsItem = MealsItem.fromMap(document.getData());
                                Log.d("TAGGGGGGGG", "getFavouriteItems: " + mealsItem.getStrMeal());
                                mealsItems.add(mealsItem);
                            }
                            onCompleteListener.onComplete(Tasks.forResult(mealsItems));
                        } else {
//                            onCompleteListener.onComplete(Tasks.forException(Objects.requireNonNull(task.getException())));
                        }
                    });
        }
    }

    @Override
    public void getMealPlanItems(String userId, OnCompleteListener<List<MealPlan>> onCompleteListener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            firestore.collection(MEAL_PLAN_COLLECTION)
                    .whereEqualTo("UID", uid)
                    .get()
                    .addOnCompleteListener(task -> {
                        Log.d("TAGGGGGGGG", "getFavouriteItems: " + uid);
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d("TAGGGGGGGG", "getFavouriteItems: " + task.getResult().size());
                            List<MealPlan> mealPlans = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MealPlan mealPlan = MealPlan.fromMap(document.getData());
                                Log.d("TAGGGGGGGG", "getFavouriteItems: " + mealPlan.getStrMeal());
                                mealPlans.add(mealPlan);
                            }
                            onCompleteListener.onComplete(Tasks.forResult(mealPlans));
                        } else {
//                            onCompleteListener.onComplete(Tasks.forException(Objects.requireNonNull(task.getException())));
                        }
                    });
        }

    }
}



