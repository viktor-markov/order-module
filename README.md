### Setup ###

1. Download repository
1. Open your project in Android Studio
1. Go to *"File / New / Import Module..."*
1. Select *"myorder-shared-android / orderModule"*
1. Add new dependence to your *"build.gradle"* file:

```
#!Groovy
 compile project(path: ':orderModule')

```


### Usage ###

**Initializtion:**
```
#!java
try {
    OrderKit kit = new OrderKit("auth token", "location external id", business id, "address", "signboard");
} catch (IllegalArgumentException e) {}

```

** Open default ui module:**

```
#!java
kit.startOrdering(contex);

```

**Get all skus related to business id:**

```
#!java
kit.getSkus();

```
### Module Info ###
* buildToolsVersion "25.0.2"
* minSdkVersion 9
* targetSdkVersion 25
* com.android.support:appcompat-v7:25.2.0
* com.android.support:recyclerview-v7:25.2.0
* com.squareup.okhttp3:okhttp:3.2.0
* com.google.code.gson:gson:2.6.2