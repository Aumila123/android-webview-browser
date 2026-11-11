A minimal in-app web browser built around a single WebView, with an address bar that remembers the last page you visited.

What the app does
1. Type an address in the field and press Užkrauti tinklapį (Load the page). If the address has no http:// or https://, https:// is added.
2. Double-tap the address field to fill it with https://www as a starting point. On the very first launch the page area shows a short hint about this.
3. Links stay inside the app, and JavaScript is enabled.
4. Back button: goes back through the page history. When there is nothing left to go back to, a Toast asks you to press Back again within 2 seconds to close the app.
5. Remembers the last page. The current URL is saved to SharedPreferences when the app goes to the background (onStop) and loaded again on the next start.

Tech stack
- Java 11, Android SDK (min 33, target and compile 36)
- WebView / WebViewClient, SharedPreferences, GestureDetector
- AndroidX AppCompat 1.8.0, Activity 1.13.0, Material 1.14.0, ConstraintLayout 2.2.2
- Gradle 8.13 and Android Gradle Plugin 8.13.2
- Android Studio

How to build and run
Requirements
- Android Studio (a recent release that supports Android Gradle Plugin 8.13) with Android SDK Platform 36 installed
- JDK 17 or newer (the JDK bundled with Android Studio is fine)
- An emulator or phone running Android 13 (API 33) or newer, because minSdk is 33
Steps
- git clone https://github.com/Aumila123/android-webview-browser.git
- Open the folder in Android Studio and wait for the Gradle sync to finish.
- Pick a device in the device selector and press Run.

The device or emulator needs an internet connection.
