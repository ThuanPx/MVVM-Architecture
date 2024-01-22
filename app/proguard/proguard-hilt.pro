# Hilt ProGuard rules.
# https://github.com/google/dagger/blob/master/java/dagger/hilt/android/lifecycle/proguard-rules.pro

# Keep class names of Hilt injected ViewModels since their name are used as a multibinding map key.
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel