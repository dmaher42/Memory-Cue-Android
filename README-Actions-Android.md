# Memory Cue Android CI Setup

This project contains a minimal Android app for a "Memory Cue" reminder tool along with GitHub Actions workflows for building APKs in the cloud.

## Contents

- `app/`: Android application module written in Kotlin. It shows a list of memory cues, allows the user to add a new cue and schedule a notification reminder in five minutes with a snooze action.
- `.github/workflows/android-ci-debug.yml`: GitHub Actions workflow that builds a debug APK on every push to the `main` branch or when manually triggered.
- `.github/workflows/android-release-signed.yml`: Workflow for generating a release APK. You should configure signing if you intend to distribute the app.

## Building in GitHub Actions

The provided workflows set up Java and Gradle, generate a Gradle wrapper, build the APK, and publish it as an artifact. To use them:

1. Commit this repository to GitHub.
2. Navigate to the **Actions** tab of your repository.
3. Run **Android CI (Debug APK)** to produce an unsigned debug APK.
4. Download the artifact named `debug-apk`.

For signed release builds you will need to add secrets for a keystore and update the `android-release-signed.yml` workflow accordingly.

## Local Development

To work locally with this project:

1. Open the project in Android Studio (Arctic Fox or later).
2. When prompted, allow Android Studio to update the Gradle wrapper.
3. Use the **Run** button to install the app on an Android device or emulator.

This project enables ViewBinding and uses RecyclerView for its list of cues. Notification scheduling uses `AlarmManager` and two `BroadcastReceiver`s for reminder and snooze actions.