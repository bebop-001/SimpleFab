# SimpleFab

This is a simple, lightweight Floating Action Button (FAB) built for Android 6.0.1+ (API 23), Gradle 8.4, and the AppCompat theme ecosystem.

## Why 

My development targets Android 6.0.1 / Gradle 8.4 using AppCompat themes. While building a chess application, I needed a minimal FAB that fit cleanly into this specific ecosystem without bringing along heavy modern library overhead. 

Since I couldn't find a lightweight fit, I rolled my own. SimpleFab is the result—essentially a custom view implementing a layered `ImageView` with two visual layers.

## How it worksrm -rf ../g

* **Visibility Toggles:** Check `MainActivity.kt` to see the `onClick` callbacks that handle showing and hiding the view.
* **Orientation Changes:** `MainActivity` also demonstrates how to save and restore the view's visibility state across configuration changes using `onSaveInstanceState`. 

> **Note:** For production or more complex architectures, this state logic is best handled inside a `ViewModel`. It is implemented here in the Activity strictly for self-contained demo simplicity.

<p align="center">
  <video src="art/use.mp4" width="320" autoplay loop muted playsinline></video>
</p>

## License

This code is licensed under the **MIT License**. Please use it however you wish!