# Wing It
Wing It is an Android app developed on Android Studio for the fulfillment of CP2106 (Orbital) in the summer of 2019. 

## Problem Motivations
Being avid, wanderlust-filled, millennials who to love to travel, we often encounter issues whereby we have thirty places of interest, and only five days to squeeze all of them in. Due to inefficient planning, the trip often ends up in one of two ways: visiting every place of interest, but only being able to fork out a tiny amount of time at each place, or being only able to visit a handful of places. These two cases can be attributed to the same cause: exceedingly inefficient itinerary planning.

## Target Audience
This app is intended for usage on Android devices running on Android 4.4 (Kitkat) and above.  
Wing It aims at both seasoned and novice travellers who may be unsatisfied with the available itinerary planners which do not allow users to designate the duration they would like to spend at a place of interest. 

## Project Scope
This project will consist of developing an Android application largely developing using Google's API (Routes, Directions, Places API). A minimal viable product should be completed by Milestone 2. 

###### This sounds familar!
Disclaimer: Wing It is developed with the intention of it being a supplement - not a one-stop application service for travellers to gather information, book acommodations, and plan their itineraries with. 

One of the most widely used trip planner, is one that forms the foundation of many popular trip planners out there is Google Maps. There are many powerful features that it provides: fastest routes, shortest routes, Google Trips. It is admittedly simple to draw parallels from Google Trips and Wing It, but the difference between the two is what makes Wing It a great supplementary tool for travellers: timme. Building upon Google Trips' amazing functionalities, we want to include the ability for users to include the duration they wish to stay at a certain location, to create an itinerary that is centered around making the most of their limited time overseas. 

## Installation

## Usage
__to include screenshots__ 

## User stories and Core Features
- As a user, I want to be able the add places of interest so that I can visit them during my trip.
Core feature 1: This feature comes in 2 parts: searching for a place of interest, and adding that to a list storing all the addded places of interest. 

As of Milestone 2, searching for a place of interest is implemented by storing the user's search text and passing it through a Geocoder object, which obtains a list of possible addresses. 
```
EditText locationSearch = findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
```
The most relevant address is  then added to an ArrayList: ```     list.add(address.getAddressLine(0))``` 

**Issue faced #1**: Despite having the null check in ```if (location != null || !location.equals(""))```, the app crashes when the search bar is empty, and the "ADD" button is clicked. While this issue is does not hinder the functionality of the app, it can cause some unintended crashes when the "ADD" button is accidentally pressed. Low priority. 

- As a user, I want to be able to delete places of interest that I no longer wish to visit.
As of Milestone 2, this basic feature has yet to be implemented, however, a possible way would be to use a hashset instead, and then remove the placce of interest from the hashset. A consideration would be that the current implementation of passing the list of places of interests to other Java classes are as such:
```
Intent intent = new Intent(this, ListView.class);
intent.putExtra("name", list);
startActivity(intent);
``` 
The current understanding is that any list ADTs passed using ```intent.putExtra("name", list)``` would automatically be converted into an ArrayList, and that there is no such method to pass a hashset. However, at our current implementation using an ArrayList, it is still possible for us to remove, albeit being slightly inefficient. 

- As a user, I want to be able to view a list of the places I have added so that I can review them.
Core feature 2: the current implementation of this feature is simple and naive as of Milestone 2, we simply iterated every location stored in the ArrayList and extracted the address of each location through reverse geocoding (obtaining a readable street address from a pair of latitude and longitude). Each of the street addresses is then added to a String variable, which is then used to change the text of the TextView widget: ```text.setText(final_text)```. 

**Issue faced #2**: We were unable to obtain a landmark name of a place of interest. For example, if a user were to key in "bedok mall" in the search field, the API would accurately return the intended location, however, we were not able to obtain back "Bedok Mall". The closest substitute we found and are currently using is obtaining the street address: 311 New Upper Changi Rd, Singapore 467360, and is done using: ```to_print[i] = addressList.get(0).getAddressLine(0)```. This makes it difficult for users to identify the place of interest (especially since they are tourists). 

- As a user, I want to be able to obtain a daily itinerary that starts from my place of accomodations.

- As a user, I want to include the time I wish to spend at each place of interest so that I can maximise my time overseas. 

## Program Flowchart
![Image of flowchart](https://i.imgur.com/dZyoRoC.png) 

## Software Development Principles

## Development Plan towards Milestone 3
