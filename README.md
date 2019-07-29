# Wing It!, a Travel Itinerary Planner
![App Icon](https://i.imgur.com/S46xkWp.png)
Wing It! is an Android app developed on Android Studio for the fulfillment of CP2106 (Orbital) in the summer of 2019. Developed by Team Seas the Days, consisting of Jarryl Yeo Zhi Xiang and Teo Jun Xiong. 


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


## Installation


## Usage
| Screenshot | Explanation |
|---|---|
|![Screenshot 1](https://i.imgur.com/6FeacPo.jpg)| <ul><li>1.1. Allows user to center the map on their current location.</li><li>1.2. Allows user to zoom in (and out).</li><li>1.3. Search field for users to input to search for a place of interest.</li><li>1.4. This button allows the user to add the place of interest **after** typing it in the search field (1.3).</li><li>1.5. This button shows the places of interest the user has input, and brings it to the next screen.</li></ul> |
|![Screenshot 2](https://i.imgur.com/R0AByKf.png)| <ul><li>2.1. Displays a list of the places of interest that the user has input.</li><li>2.2. Clicking this button will bring the user to the next screen. We intend to add the delete functionality in this screen as it seems rather intuitive.</li></ul>|
|![Screenshot 3](https://i.imgur.com/EVJYCYl.png)| <ul><li>This screen is underdeveloped, but the general idea is that it will display a day to day itinerary, or a map view with polylines showing which routes to go, or both.</li></ul>|


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


**_As a user, I want to include the time I wish to spend at each place of interest so that I can maximise my time overseas._**

- **Core feature 4**: this is done by passing the text in the input fields in the CardView when the "generate" button is pressed. This data is then collected into an array and then passed to ```RouteView.java```. The array is then used to supplement the Travelling Salesman Problem (core feature 5).


**_As a user, I want to be able to obtain a daily itinerary that starts from my place of accomodations._**

- **Core feature 5**: after pressing on the "generate" button in ```ListView.java```, the Travelling Salesman Problem is solved by calling  ```TspDynamicProgrammingIterative.java```, generating a solution in the form of an ArrayList, e.g. \[0 -> 2 -> 3 -> 1 -> 0], which shows the order of travelling from a starting point, going through all other places, and returning to the starting point. 

- The solution is then supplemented by using the array of hours intended to spend at each location, number of days in trip, and number of estimated hours intended to spend per day during the trip. Then, a daily itinerary is then shown. 

- This solution, may not be the most optimal due to our general lack of experience in dynamic programming. Our solution is simply counting the accumulated hours at the locations in a day, and when that amount exceeds the intended hours spent per day, we would end the day, and begin planning for the next day. For example, if ```TspDynamicProgrammingIterative.java``` returns a solution: \[0 -> 2 -> 3 -> 1 -> 0], and on the first day, visiting 0 -> 2 -> 3 -> 1 exceeds the intended hours spent per day, the last location is swapped for the starting location, day 1: 0 -> 2 -> 3 -> 0. The itinerary for day 2 would then be going through the last location, 0 -> 1 -> 0. 


## Issues Faced
| Summary of Issue | Details of Issue |
|---|---|
| ~~Null inputs for the "add" button causes app to crash~~ | Despite having the null check in ```if (location != null or !location.equals(""))```, the app crashes when the search bar is empty, after the "ADD" button is clicked. Solved by implementing a Toast to prompt users to include a valid query: ```Toast.makeText(getApplicationContext(), "Please input a valid query!", Toast.LENGTH_SHORT).show();``` |
| Deletion of a location doesn't remove it from map | Deletion works well except that it does not remove the previously placed marker on the map. |
| Unable to obtain name of location | We were unable to obtain a landmark name of a place of interest. For example, if a user were to key in "bedok mall" in the search field, the API would accurately return the intended location, however, we were not able to obtain back "Bedok Mall". The closest substitute we found and are currently using is obtaining the street address: 311 New Upper Changi Rd, Singapore 467360, and is done using: ```to_print[i] = addressList.get(0).getAddressLine(0)```. This makes it difficult for users to identify the place of interest (especially since they are tourists). |
| ~~Multiple threads made adding to a list difficult~~ | When we intended to increase the scale of obtaining the result from the Google Maps API using a URL request, we encountered an issue where the GeoTask's ```execute()``` occurs in the background and not concurrently after it was called in ```RouteView```. Found out that this was a result of ```AsyncTask``` utilising threads. Previously, we used if-else to circumvent this, which proved to be too complex and confusing. Switched to a ```Queue``` to store the row and column pair, and when the background task is complete, it would pop the top of the queue. We found this to be a much simpler way to store the data in the adjacency matrix. | 


## Program Flowchart
![Image of flowchart](https://i.imgur.com/dZyoRoC.png) 


## Software Engineering Principles Employed
##### **S.O.L.I.D.**
- Single Responsiblity Principle

Every class is assigned only a single functionality. ```MapActivity``` is responsible for loading the map which allows users to add places of interest; ```ListView``` is responsible for displaying the current list of places of interest to the users; ```RouteView``` is responsible for displaying a generated route for the users' itinerary. 

- Open/ Closed Principle

This principle has not been employed as we are not familiar or confident enough with Object Oriented Programming principles to code with it in mind.

- Liskov Substitution Principle

This principle is not employed as our Android app is not complicated and at most one instance of any object is created (as far as we are aware). We will employ this principle should there be increased callbacks of object instantiation in the future.

- Interface Segregation Principle 

No interface implemented.

- Dependency Inversion Principle

No interface implemented, although its importance is appreciated and will be taken note of once we are more advanced in the development of the app. 

##### **D.R.Y. (Don't Repeat Yourself)**

There is no repeated code apart from calling ```Geocoder``` to obtain the information regarding the user's search input, whcih we plan to streamline. We need to figure out a better way of passing data from one Java file to another, or to store it in a database using mySQL which would require extra time to learn. 

##### **K.I.S.S. (Keep It Simple, Stupid)**

Our code is coded such that it is simple to read without in-depth knowledge of the context, moreover, comments are inserted to clarify on methods that may be unclear. 


## Development Plan towards Milestone 3
- [x] Implement **delete** functionality
- [ ] Implement **daily itinerary**
- [x] Devise ~~SSSP~~ TSP algorithm
- [x] Implement time as a factor rather than distance for SSSP 
- [ ] \(Optional) UI/ user-friendlyness improvement
- [ ] Debugging

Following from the core features section, we have two core features that are yet to be implemented: deleting, viewing a daily itinerary, and including the time that a user wishes to spend at a pace of interest. These core features are listed in order of increasing priority, as we believe that the defining point of Wing It is its ability to input the intended duration. We aim to also solve the issues faced during the duration of milestone 2 in hopes to be able to make Wing It user-friendly and easy to understand. Implementation of Google's Distance Matrix has proven an issue for us close to the end of Milestone 2, but we are confident that this will be solved by Milestone 3 with deeper understanding of Google's developer guides, and under the guidance of our advisor.
