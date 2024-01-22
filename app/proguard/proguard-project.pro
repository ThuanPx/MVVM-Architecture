# AndroidAnnotations
-dontwarn org.androidannotations.api.rest.*


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''

-dontwarn android.support.**
-dontwarn com.atinternet.**
-dontwarn org.apache.**
-dontwarn javax.annotation.**
-dontwarn com.google.protobuf.**

-keepattributes InnerClasses
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes EnclosingMethod

-dontwarn com.sothree.**
-keep class com.sothree.**
-keep interface com.sothree.**

-dontwarn org.xmlpull.v1.**

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontwarn java.awt.**
-dontwarn **CompatHoneycomb
-keep class android.support.v4.** { *; }

-dontwarn uk.co.senab.photoview.**
-keep class uk.co.senab.photoview.** { *; }

-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile,LineNumberTable
-dontwarn com.crashlytics.**
-keep public class * extends java.lang.Exception

-keep class android.support.v4.view.ViewPager
-keepclassmembers class android.support.v4.view.ViewPager$LayoutParams { *; }
-keep class android.support.v4.app.Fragment { *; }

-keep class com.mixpanel.android.mpmetrics.MixpanelAPI { *;}
-keep class com.google.android.gms.analytics.Tracker { *; }
-keep class com.google.analytics.tracking.android.Tracker { *; }
-keep class com.flurry.android.FlurryAgent { *; }
-keep class com.omniture.AppMeasurementBase { *;}
-keep class com.adobe.adms.measurement.ADMS_Measurement { *;}

##---------------Begin: proguard configuration common for all Android apps ----------

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class net.itify.cookpad.model.** { *; }
-keep class net.itify.cookpad.base.network.** { *; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# https://github.com/google/gson/issues/2069
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type

##---------------End: proguard configuration for Gson  ----------


##---------------Begin: proguard configuration for appboy  ----------
-dontwarn com.amazon.device.messaging.**
-dontwarn bo.app.**
-dontwarn com.google.android.gms.**
-dontwarn com.appboy.ui.**
-keep class bo.app.** { *; }
-keep class com.appboy.** { *; }
##---------------End: proguard configuration for appboy  ----------

##---------------Begin: proguard configuration for Tealium  ----------
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}

# Allow obfuscation of android.support.v7.internal.view.menu.**
# to avoid problem on Samsung 4.2.2 devices with appcompat v21
# see https://code.google.com/p/android/issues/detail?id=78377
-keep class !android.support.v7.internal.view.menu.**,android.support.** {*;}
-keep interface !android.support.v7.internal.view.menu.**,android.support.** {*;}

# Config for Google Play Services: http://developer.android.com/google/play-services/setup.html#Setup
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @ccom.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-dontwarn com.google.android.gms.**

-keep class com.tealium.library.* {
    public <init>(...);
    <methods>;
}

-dontwarn com.tealium.**
##---------------End: proguard configuration for Tealium  ----------

##---------------Begin: proguard configuration for Newrelic  ----------

-keepattributes Exceptions, Signature, InnerClasses, LineNumberTable

-keep class com.google.firebase.** { *; }
#-dontwarn com.google.j2objc.annotations.**

# moshi
-keep class com.squareup.moshi.** { *; }
-keep interface com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**
-dontwarn okio.**

# ThreeTen-Backport
-keep class org.threeten.bp.zone.*
-dontwarn org.threeten.bp.chrono.JapaneseEra

###### Fix exception: Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android'
-dontwarn kotlinx.atomicfu.AtomicBoolean
# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Amplitude
-keep class com.google.android.gms.ads.** { *; }
-dontwarn okio.**

#######

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
# -keepresourcexmlelements manifest/application/meta-data@value=GlideModule
-dontwarn com.chotot.vn.dashboard.fragments.**

-dontwarn org.json.JSONStringer

#Jsoup
-keep public class org.jsoup.** {
public *;
}
-keeppackagenames org.jsoup.nodes
#zalo sign-in
-keep class com.zing.zalo.**{ *; }
-keep enum com.zing.zalo.**{ *; }
-keep interface com.zing.zalo.**{ *; }
#google sign-in
-keep class com.google.googlesignin.** { *; }
-keepnames class com.google.googlesignin.* { *; }

-keep class com.google.android.gms.auth.** { *; }

# Protobuf
-keep public class * extends com.google.protobuf.GeneratedMessageLite { *; }
-keep class CxSvcProto.**  { *; }
