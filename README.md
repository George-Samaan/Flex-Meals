# Fle-x - Your Personalized Meal Guide

## Features

### Daily Meal Inspiration
- **Inspiration of the Day:** Get a new meal every time to inspire your cooking.
- **Detailed Meal Information:** For each selected meal, you can view:
  - **Meal Name**
  - **Image** of the dish
  - **Country of origin**
  - **List of Ingredients** shown with images
  - **Step-by-Step Cooking Instructions**
  - **Embedded Cooking Video** Video within the app to guide you through the process
  - **Option to Mark as Favorite and Schedule** the meal for later
  - **Add to Calendar** for easy meal planning directly from the app.

### Meal Discovery
- **Search Functionality:** Find meals by searching for a country, ingredient, or category.
- **Explore Categories:** Browse various categories to discover new meals.

### Data Synchronization
- **Local and Cloud Storage:** Use Room for storing data locally and Firebase for cloud backups.
- **Offline Access:**: Access your favorite meals and scheduled plans even without an internet connection.
- **Data Backup & Restore:** Sync your meal data across devices after logging in.

### Meal Planning
- **Meal Planner:** Organize and view your weekly meals in a structured format.

### User Authentication
- **Multiple Sign-In Options:** Log in or sign up easily with options including social network authentication (Google).
- **Auto Data Retrieval:** Automatically fetch your saved data from the server when you log in.
- **Guest Mode**: Allows guests to explore meal categories and the daily meal without an account.

## Technical Overview

### Architecture
- **MVP (Model-View-Presenter)**: Ensures clean separation of concerns, making the code easier to maintain and test.

### Networking
- **Retrofit**: Handles all network communication seamlessly.

### Image Loading
- **Glide**: Delivers fast and smooth loading of images throughout the app.

### Additional Libraries
- **Firebase**: Provides robust authentication and real-time database capabilities.
- **Room**: Manages the app's local database efficiently.
- **Lottie**: Adds high-quality animations, particularly in the splash screen.
- **Alerter**: Displays eye-catching animated alerts within the app.
- **Swipe TO Refresh**: Enables users to refresh screens anytime with a simple swipe.
- **calendar**: Facilitates easy access to scheduled meals with just one click.
