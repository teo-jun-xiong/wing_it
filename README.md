# wing it!, a Travel Itinerary Planner
![App Icon](https://i.imgur.com/S46xkWp.png)

wing it! is an Android app developed on Android Studio for the fulfillment of CP2106 (Orbital) in the summer of 2019. Developed by Team Seas the Days, consisting of Jarryl Yeo Zhi Xiang and Teo Jun Xiong. Targeted level of achievement: Gemini. 

Wing it: to improvise, to do something without proper preparation. wing it! was created with the notion that time spent travelling is precious and memorable, and should not be wasted as a result of inefficient planning. It allows users to customise their trip to ensure that they are able to visit all their places of interest!


## Problem Motivations
Being avid, wanderlust-filled millennials who to love to travel, we often encounter issues whereby we have thirty places of interest, and only five days to squeeze all of them in. Due to inefficient planning, the trip often ends up in one of two ways: visiting every place of interest, but only being able to fork out a tiny amount of time at each place, or being only able to visit a handful of places. These two cases can be attributed to the same cause: exceedingly inefficient itinerary planning.


## Target Audience
This app is intended for usage on Android devices running on Android 4.4 (Kitkat) and above.  
Wing It aims at both seasoned and novice travellers who may be unsatisfied with the available itinerary planners which do not allow users to designate the duration they would like to spend at a place of interest. 


## Project Scope
This project will consist of developing an Android application largely developing using Google's API (Routes, Directions, Places API). A minimal viable product should be completed by Milestone 2. 


###### This sounds familar! Comparison with Google Trips
Disclaimer: Wing It is developed with the intention of it being a supplement - not a one-stop application service for travellers to gather information, book acommodations, and plan their itineraries with. 

One of the most widely used trip planner, is one that forms the foundation of many popular trip planners out there: Google Maps. There are many powerful features that it provides: fastest routes, shortest routes, Google Trips. It is admittedly simple to draw parallels from Google Trips and Wing It, but the difference between the two is what makes Wing It a great supplementary tool for travellers: timme. Building upon Google Trips' amazing functionalities, we want to include the ability for users to include the duration they wish to stay at a certain location, to create an itinerary that is centered around making the most of their limited time overseas. 


## Usage
| Screenshot | Explanation |
|---|---|
|![Screenshot 1](https://i.imgur.com/n7fo9rB.png)| <ul><li>A. FAQ to guide users on how to use wing it!.</li><li>B. Search field for user to search for a place of interest.</li><li>C. Button to add a place of interest after typing in the search text in C. The hotel or origin always has to be the first location added. </li><li>D. Button to show a CardView of all the added places of interest.</li><li>E. Centers the map on the user's location. </li><li>F. Zoom buttons. </li></ul> |
|![Screenshot 2](https://i.imgur.com/Xsmg4Qm.png)| <ul><li>A. FAQ to guide users on how to use wing it!.</li><li>B. Input field for the number of days of the user's trip.</li><li>C. Number of hours the user intend to spend per day during the trip.</li><li>D. Button to generate an itinerary using the user's input.</li><li>E. Delete button to remove a place of interest.</li><li>F. The text on top shows the user's search text while the bottom text shows the place of interest's address.</li><li>G. Input field for number of hours the user wishes to spend at a certain place of interest.</li><li>H. Button to enter G into the code. Must be pressed or the time spent will be assumed to be 0, which will mess up the algorithm.</li></ul>|
|![Screenshot 3](https://i.imgur.com/cDDlg1N.png)| <ul><li>A. FAQ to guide users on how to use wing it!.</li><li>B. CardView for the daily itinerary. Each card will show the day of the trip, as well as in which order to visit that day's places of interest. Note that the day always starts and end at the hotel (or the first place of interest).</li></ul>|
|![Screenshot 4](https://i.imgur.com/ZwRgFaF.png)|<ul><li>A. FAQ to guide users on how to use wing it!.</li><li>B. Should there be no way to visit all the places of interest given the user's input parameters, this text will be displayed, prompting the user to either remove places of interest or to modify some of the numbers.</li></ul>|


## User Stories and Core Features
**_As a user, I want to be able the add places of interest so that I can visit them during my trip._**

- **Core feature 1**: adding places of interest. This feature comes in 2 parts: searching for a place of interest, and adding that to a list containing all the places of interest. 

- Searching for a place of interest is implemented by storing the user's search text and passing it through a Geocoder object, which obtains a list of possible addresses. 

<details><summary>Obtaining search results from user's input</summary>
<p>
        
```java
EditText locationSearch = findViewById(R.id.editText);
String location = locationSearch.getText().toString(); // user's String input
List<Address> addressList = null;

if (location.equals("")) {
        Toast.makeText(getApplicationContext(), "Please input a valid query!", Toast.LENGTH_SHORT).show();

} else {
        Geocoder geocoder = new Geocoder(this);
        try {
                addressList = geocoder.getFromLocationName(location, 1);

        } catch (IOException e) {
                e.printStackTrace();
        }

```
</p>
</details>

- On pressing the "ADD" button, a marker is added to the Map and the most relevant address is then added to an ArrayList: ```list.add(address.getAddressLine(0))```. Once the user decides that enough places of interest have been added, they can view a list of places of interest by pressing the "LIST" button. A vertical scrolling card view is shown to the user, each with three components to it, the name of the place, its street address, a "delete" button (trash can), a "done" button (tick), and an input field for the number of hours they wish to spend at location. 

- Users are able to remove any previously added places of interest by simply pressing the delete button. Following that, users are required to type in an integer representing the number of hours they wished to spend at the place of interest, and pressing the done button will save their input.  

- Below the scrolling card view, there are 2 input fields for the number of days the trip will be, and the number of hours they wished to spend per day. These two inputs serve as a guideline in generating the most optimal route. Once the above is done, pressing the "generate" button will then compute the most optimal route.


**_As a user, I want to be able to delete places of interest that I no longer wish to visit._**

- **Core feature 2**: the delete function has a simple implementation: pressing the delete icon (trash bin) on a desired location, the user can remove that location. The deletion is also done in such a way that it removes it from being generated in the most optimal solution. However, the deletion would not remove the marker from the map.


**_As a user, I want to be able to view a list of the places I have added so that I can review them._**

- **Core feature 3**: this function is implemented using a ```RecyclerView``` and ```RecyclerViewAdapter```, which loads a CardView of the added locations. Each 'card' consists of the street address, "delete", "done" buttons, and an input field for the number of hours the user wishes to spend at that location. 

<details><summary>XML for recyclerView item for LocationListView</summary>
<p>
        
```java
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="4dp"
    app:cardBackgroundColor="#00000000"
    app:cardCornerRadius="6dp">

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="4dp"
        android:layout_marginTop="30dp"
        android:background="#DA1B3F70">

        <ImageView
            android:id="@+id/recycler_delete"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:layout_marginStart="10dp"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="8dp"
            android:foregroundGravity="center"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.0"
            app:srcCompat="@drawable/ic_delete" />

        <TextView
            android:id="@+id/recycler_textView"
            android:layout_width="180dp"
            android:layout_height="wrap_content"
            android:layout_alignParentTop="true"
            android:layout_marginStart="8dp"
            android:layout_marginTop="4dp"
            android:layout_toEndOf="@+id/recycler_delete"
            android:fontFamily="@font/montserrat"
            android:text="Line 1"
            android:textColor="@android:color/white"
            android:textSize="16sp"
            android:textStyle="bold"
            app:layout_constraintBottom_toTopOf="@+id/recycler_textView2"
            app:layout_constraintStart_toEndOf="@+id/recycler_delete"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/recycler_textView2"
            android:layout_width="150dp"
            android:layout_height="wrap_content"
            android:layout_below="@+id/recycler_textView"
            android:layout_marginEnd="28dp"
            android:layout_marginBottom="8dp"
            android:layout_toEndOf="@+id/recycler_delete"
            android:fontFamily="@font/montserrat"
            android:text="Line 2"
            android:textColor="#b5b5b5"
            android:textSize="14sp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toStartOf="@+id/recycler_hours" />

        <EditText
            android:id="@+id/recycler_hours"
            android:layout_width="100dp"
            android:layout_height="40dp"
            android:layout_marginTop="4dp"
            android:layout_marginEnd="8dp"
            android:layout_marginBottom="4dp"
            android:layout_toStartOf="@+id/recycler_done"
            android:ems="10"
            android:fontFamily="@font/montserrat"
            android:gravity="center"
            android:hint="Input hours"
            android:inputType="number"
            android:textAlignment="center"
            android:textColor="#ffffff"
            android:textColorHint="#b5b5b5"
            android:textSize="15sp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toStartOf="@+id/recycler_done"
            app:layout_constraintTop_toTopOf="parent" />

        <ImageView
            android:id="@+id/recycler_done"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:layout_alignParentEnd="true"
            android:layout_marginTop="8dp"
            android:layout_marginEnd="8dp"
            android:layout_marginBottom="8dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.0"
            app:srcCompat="@drawable/ic_done" />

    </android.support.constraint.ConstraintLayout>


</android.support.v7.widget.CardView>
```
</p>
</details>

**_As a user, I want to include the time I wish to spend at each place of interest so that I can maximise my time overseas._**

- **Core feature 4**: this is done by passing the text in the input fields in the CardView when the "generate" button is pressed. This data is then collected into an array and then passed to ```RouteView.java```. The array is then used to supplement the Travelling Salesman Problem (core feature 5).


**_As a user, I want to be able to obtain a daily itinerary that starts from my place of accomodations._**

- **Core feature 5**: after pressing on the "generate" button in ```LocationListView.java```, the Travelling Salesman Problem is solved by calling  ```ItineraryViewTSP.java```, generating a solution in the form of an ArrayList, e.g. \[0 -> 2 -> 3 -> 1 -> 0], which shows the order of travelling from a starting point, going through all other places, and returning to the starting point. 

- The solution is then supplemented by using the array of hours intended to spend at each location, number of days in trip, and number of estimated hours intended to spend per day during the trip. Then, a daily itinerary is then shown. 

- This solution, may not be the most optimal due to our general lack of experience in dynamic programming. Our solution is simply counting the accumulated hours at the locations in a day, and when that amount exceeds the intended hours spent per day, we would end the day, and begin planning for the next day. For example, if ```ItineraryViewTSP.java``` returns a solution: \[0 -> 2 -> 3 -> 1 -> 0], and on the first day, visiting 0 -> 2 -> 3 -> 1 exceeds the intended hours spent per day, the last location is swapped for the starting location, day 1: 0 -> 2 -> 3 -> 0. The itinerary for day 2 would then be going through the last location, 0 -> 1 -> 0. 


## Issues Faced
| Summary of Issue | Details of Issue |
|---|---|
| ~~Null inputs for the "add" button causes app to crash~~ | Despite having the null check in ```if (location != null or !location.equals(""))```, the app crashes when the search bar is empty, after the "ADD" button is clicked. Solved by implementing a Toast to prompt users to include a valid query: ```Toast.makeText(getApplicationContext(), "Please input a valid query!", Toast.LENGTH_SHORT).show();``` |
| Certain search text will cause app to crash | Ambiguous search text has caused the app to crash on multiple occasions, such as "ite" and "white sands". |
| Unable to obtain name of location | We were unable to obtain a landmark name of a place of interest. For example, if a user were to key in "bedok mall" in the search field, the API would accurately return the intended location, however, we were not able to obtain back "Bedok Mall". The closest substitute we found and are currently using is obtaining the street address: 311 New Upper Changi Rd, Singapore 467360, and is done using by returning the user's initial search text. As a result, the text shown is dependent on the user's input. This makes it difficult for users to identify the place of interest, especially since they are tourists, if the user did not specify the place of interest, i.e. nus rather than National University of Singapore. |
| ~~Multiple threads made adding to a list difficult~~ | When we intended to increase the scale of obtaining the result from the Google Maps API using a URL request, we encountered an issue where the GeoTask's ```execute()``` occurs in the background and not concurrently after it was called in ```ItineraryView.java```. Found out that this was a result of ```AsyncTask``` utilising threads. Previously, we used if-else to circumvent this, which proved to be too complex and confusing. Switched to a ```Queue``` to store the row and column pair, and when the background task is complete, it would pop the top of the queue. We found this to be a much simpler way to store the data in the adjacency matrix. | 
| Deleting a card off of ```LocationListView.java``` does not remove the marker ```MainActivity.java``` | The marker still remains on the map even after it is deleted. |
| After deletion of a card, and pressing the list button, the removed item is not removed from the list | The previously removed item will not have been removed, as such, it is sometimes troublesome to add and remove items. |


## Test Log
![Image of test](https://i.imgur.com/nYPyt5R.png) 


## Project Log
[LOG.md](https://github.com/waaaflesss/wing_it/blob/master/LOG.md)


## Software Engineering Principles Employed
##### **S.O.L.I.D.**
- Single Responsiblity Principle

Every class is assigned only a single functionality. ```MainActivity.java``` is responsible for loading the map which allows users to add places of interest; ```LocationListView.jkava``` is responsible for displaying the current list of places of interest to the users; ```RouteView``` is responsible for displaying a generated route for the users' itinerary. 

- Open/ Closed Principle

This principle has not been employed as we are not familiar or confident enough with Object Oriented Programming principles to code with it in mind.

- Liskov Substitution Principle

This principle is not employed as our Android app is not complicated and at most one instance of any object is created (as far as we are aware). We will employ this principle should there be increased callbacks of object instantiation in the future.

- Interface Segregation Principle 

Interface is heavily used in ```ItineraryView_GeoTask.java```, ```LocationListView_RecyclerAdapter.java```, as well as ```ItineraryView_RecyclerAdapter.java```.

- Dependency Inversion Principle

Its importance is appreciated and will be taken note of once we are more advanced in the development of the app. 

##### **D.R.Y. (Don't Repeat Yourself)**

There is no repeated code apart from calling ```Geocoder``` to obtain the information regarding the user's search input, whcih we plan to streamline. 

##### **K.I.S.S. (Keep It Simple, Stupid)**

Our code is coded such that it is simple to read without in-depth knowledge of the context, moreover, comments are inserted to clarify on methods that may be unclear. 


## Milestone 2's Development Plan towards Milestone 3
- [x] Implement **delete** functionality
- [x] Implement **daily itinerary**
- [x] Devise ~~SSSP~~ TSP algorithm
- [x] Implement time as a factor rather than distance for SSSP 
- [x] \(Optional) UI/ user-friendlyness improvement
- [x] Debugging

## Development Plan towards Splashdown
- [ ] Fix current issues
- [ ] Conduct more rigourous testing
- [x] Improve UI/UX
- [x] Create poster for Splashdown

While the app is functional, there are some issues that can cause the app to crash, and that is what we aim to fix going towards Splashdown. Moreover, the FAQ button currently does nothing, and it is something we will need to implement. 
