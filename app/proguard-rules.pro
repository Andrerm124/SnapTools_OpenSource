# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Andre\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-repackageclasses ST
-useuniqueclassmembernames
-applymapping build/outputs/mapping/release/mapping.txt
-dontshrink
-dontoptimize
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*,SourceFile,LineNumberTable

#-injars build/intermediates/transforms/proguard/release/jars/3/1f/test.jar
#-outjars build/intermediates/transforms/proguard/release/jars/3/1f/main.jar

-keepnames @org.aspectj.lang.annotation.Aspect class * {
    ajc* <methods>;
}

-keepclassmembers class com.ljmu.andre.snaptools.ModulePack.Databases.Tables.LensObject {
    public <fields>;
}

#Fabrics Stuff

#ST Stuff
-keepnames class com.ljmu.andre.snaptools.ModulePack.ModulePackImpl
-keepnames class com.ljmu.andre.snaptools.HookManager
-keepnames class com.google.gson.stream.JsonWriter
-keepnames class com.google.gson.stream.JsonReader

-keepnames class com.ljmu.andre.snaptools.FCM.MessageTypes.Message
-keepnames class * extends com.ljmu.andre.snaptools.FCM.MessageTypes.Message

#com.wang.avi:library
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

-keep class hugo.weaving.DebugLog { *; }

#com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.25
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers public class * extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(android.view.View);
}



#com.github.bumptech.glide:glide:4.0.0-RC1
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn com.googlecode.mp4parser.**
-dontwarn org.aspectj.**

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class com.google.gson.JsonPrimitive { *; }
-keep class com.google.gson.JsonArray { *; }
-keep class com.google.gson.JsonElement { *; }
-keep class com.google.gson.JsonNull { *; }
-keep class com.google.gson.JsonObject { *; }

##---------------End: proguard configuration for Gson  ----------

-dontwarn com.google.common.collect.MinMaxPriorityQueue

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

#GUAVA
#17
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

#19
-dontwarn   com.google.j2objc.annotations.**
-keep class org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement { *; }
-dontwarn   org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

#20
#-dontwarn com.google.**
-dontwarn com.google.errorprone.annotations.*

#RetroLambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*


-dontwarn java.lang.ClassValue
-dontwarn com.google.errorprone.annotations.concurrent.LazyInit
