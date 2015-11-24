W1D1 Lesson 1 Assignment
Android java Programming
Lesson1 Assignment
Jenber Nurye 107695

1.Setting up Android development Environment
Tools and software's that we need to start developing android application projects are editors(IDE),SDK and platforms. Previously eclipse was IDE for android, now Android studio is the official IDE for Android Application development. Once we installed the Android studio then we need to download   and installed the appropriate version of android SDK(software development kit) tools The Android SDK is primarily intended to help developers create, test, and debug their Android apps, 
2.Activities
An Activity:- is an application component that provides a screen with which users can interact in order to do something, such as dial the phone, take a photo, send an email, or view a map. Each activity is given a window in which to draw its user interface. 
The window typically fills the screen, but may be smaller than the screen and float on top of other windows. An application usually consists of multiple activities that are loosely bound to each other. Typically, one activity in an application is specified as the "main" activity, which is presented to the user when launching the application for the first time. Each activity can then start another activity in order to perform different actions.
The Activity.xml file:-This is the XML layout file for the activity we added when we created the project with Android Studio. Following the New Project workflow, Android Studio presents this file with both a text view and a preview of the screen UserInterface.It includes some default setting and text view. 
The MyActivity.java :-It's a java class file that contains the class definition for the activity we created, it is something the apps done.
AndroidManifest.xml file:-It describes the fundamental characteristics of the app and defines each of its components. You'll revisit this file as you follow these lessons and add more components to your app.
3.Managing Activity Lifecycle
Managing the lifecycle of an activities can be implemented by calling callback methods is important to developing a strong and flexible android applications. The lifecycle of an activity is directly affected by its association with other activities, its task and back stack. An activity can exist in essentially three states
Resumed:- is the activity that the user currently interact with, the activity is in the foreground of the screen and has user focus. This running state of the application.
Paused:-Another activity is in the foreground and has focus, but this one is still visible. That is, another activity is visible on top of this one and that activity is partially transparent or doesn't cover the entire screen.
Stopped:-The activity is completely obscured by another activity (the activity is now in the "background"). A stopped activity is also still alive (the Activity object is retained in memory, it maintains all state and member information, but is not attached to the window manager). However, it is no longer visible to the user and it can be killed by the system when memory is needed elsewhere.
