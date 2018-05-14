# SnapTools
A reupload of the SnapTools Private Repository. Unfortunately a fresh repository is required as there were many commits with private information such as developer keys and certificates.

I will be going through the project and adding additional javadocs as the project deserve a more professional format. If there is any specific code or file that you wish for me to clear up, please feel free to [Open An Issue](https://github.com/Andrerm124/SnapTools_OpenSource/issues/new) thread and I'll do my best to provide information on the topic, however be sure to check out the Points Of Interest section below for the more important systems in the app.

It should be noted that this source is not intended to be built directly into a directly functional APK, it will require adjustments as certain required files have been redacted due to containing private information (Such as [google_services.json](https://github.com/Andrerm124/SnapTools_OpenSource/blob/master/app/google-services.json), [RedactedClasses](https://github.com/Andrerm124/SnapTools_OpenSource/tree/master/app/src/main/java/com/ljmu/andre/snaptools/RedactedClasses), and the certificates located in the [Debug](https://github.com/Andrerm124/SnapTools_OpenSource/tree/master/app/src/debug/assets) and [Release](https://github.com/Andrerm124/SnapTools_OpenSource/tree/master/app/src/release/assets) Assets folders)

# SnapTools Media
[![Shtuff](https://img.youtube.com/vi/mIkM8KTjoWs/0.jpg)](https://www.youtube.com/watch?v=mIkM8KTjoWs)

A user created promotional video by [John Luke](https://www.youtube.com/channel/UCVQavYHPmuzDu5eELNC3oWg)

# Points of Interest
### Framework/ModulePack System
[HookManager](https://github.com/Andrerm124/SnapTools_OpenSource/blob/master/app/src/main/java/com/ljmu/andre/snaptools/HookManager.java)

The entry point for Xposed based code. 
*Todo: Add more comments.*

[FrameworkManager](https://github.com/Andrerm124/SnapTools_OpenSource/blob/master/app/src/main/java/com/ljmu/andre/snaptools/Framework/FrameworkManager.java)

Responsible for managing the loading and injecting of ModulePacks.

[ModulePack](https://github.com/Andrerm124/SnapTools_OpenSource/blob/master/app/src/main/java/com/ljmu/andre/snaptools/Framework/ModulePack.java)

The interface between the framework and the dynamically loaded ModulePackImpl code.

[ModulePackImpl](https://github.com/Andrerm124/SnapTools_OpenSource/blob/master/app/src/pack/java/com/ljmu/andre/snaptools/ModulePack/ModulePackImpl.java)

An implementation of ModulePack with the loading and injection systems in place to manage the internal hooking code.

[HookResolver](https://github.com/Andrerm124/SnapTools_OpenSource/blob/master/app/src/pack/java/com/ljmu/andre/snaptools/ModulePack/HookResolver.java)

Responsible for efficiently generating and caching references to Hooks and HookClasses.

### General
[MainActivity](https://github.com/Andrerm124/SnapTools_OpenSource/blob/master/app/src/main/java/com/ljmu/andre/snaptools/MainActivity.java)

The majority of the application initialisation and EventBus subscriptions.
