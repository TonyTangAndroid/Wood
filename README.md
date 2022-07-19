# Wood

## What is this library about?
Wood is a simple in-app Timber log recorder. Wood records and persists all Timber log into Room Database , and provides a UI for reviewing their content.


## Features
1. Apps using Wood will display a notification showing a summary of ongoing Timber log activity. Tapping on the notification launches the full Wood UI. Apps can optionally suppress the notification, and launch the Wood UI directly from within their own interface. Log can be copied into clipboard or exported via a share intent.
2. Search Log by any keyword.
3. The main Wood activity is launched in its own task, allowing it to be displayed alongside the host app UI using Android 7.x multi-window support.

Wood requires Android 4.1+ and Timber.

**Warning**: The data generated and stored when using this Timber logger may contain sensitive information such as tokens or password. It is intended for use during development, and not in release builds or other production deployments.

## Setup

### Download

Based on your IDE you can import library in one of the following ways

##### Gradle:
1, Add JitPack at your root level `build.gradle` file.
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

//com.github.UsherAndroid:external

2, Add the dependency in your `build.gradle` file. Add it alongside the `no-op` variant to isolate Wood from release builds as follows:
```gradle
debugImplementation 'com.github.TonyTangAndroid.Wood:wood:0.9.9.1'
releaseImplementation 'com.github.TonyTangAndroid.Wood:wood-no-op:0.9.9.1'
```
If you want this in library in both release and compile, then try this : 
```gradle
implementation 'com.github.TonyTangAndroid.Wood:wood:0.9.9.1'
```

### Usage

In your application code, create an instance of `WoodTree` and add it as an Timber tree when planting your Timber tree:

```java
Timber.plant(new WoodTree(this)
                .retainDataFor(WoodTree.Period.FOREVER)
                .maxLength(100000).showNotification(true));
```

That's it! Wood will now record all Timber log.

##### Show Sticky/Normal Notification
Sticky => true and Normal => false
```java
new WoodTree(context).showNotification(true/false)
```

### Other Settings
##### Check stored data
Launch the Wood UI directly within your app with the intent from `Wood.getLaunchIntent()`.
```java
startActivity(Wood.getLaunchIntent(this));
```

##### Add app shortcut to your app
```java
Wood.addAppShortcut(this);
```

##### Max Length
Set Response Max length to store
```java
new WoodTree(context).maxContentLength(10240L)//the maximum length (in bytes)
```

##### Retention Period
Set the retention period for Timber log data captured
```java
new WoodTree(context).retainDataFor(Period.ONE_WEEK)
```
 
## Acknowledgements
Gander (parent repo)
- [Gander][ganderLink] - Copyright Ashok Varma, Inc.
##### Thanks to [Ashok Varma][ashokVarmaLink] for his amazing library [Gander][ganderLink]. This repo is inspired from Gander at the very beginning.

Wood uses the following open source libraries:
- [Timber][timberLink] - Copyright Jake Wharton.

License
-------

    Copyright (C) 2019 Tony Tang.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[ganderLink]: https://github.com/Ashok-Varma/Gander
[ashokVarmaLink]: https://github.com/Ashok-Varma
[timberLink]: https://github.com/JakeWharton/timber
