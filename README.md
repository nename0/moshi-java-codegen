moshi-java-codegen
=====

This annotation processor generates small and fast [JsonAdapters](https://square.github.io/moshi/1.x/moshi/com/squareup/moshi/JsonAdapter.html)
for Moshi from Java classes, just like the [moshi-kotlin-codegen](https://github.com/square/moshi#codegen) for Kotlin.
To use it just annotate your class like this:

```java
@JsonClass(generateAdapter = true, generator = "java")
class BlackjackHand {
  public final Card hidden_card;
  public final List<Card> visible_cards;
  ...
}
```

and add the annotation processor to your project. For Gradle:

```groovy   
repositories {
    maven {
        name = "GitHubPackages - moshi-java-codegen"
        url = uri("https://maven.pkg.github.com/nename0/moshi-java-codegen")
    }
    mavenCentral()
    ...
}

dependencies {
    annotationProcessor "io.github.nename0:moshi-java-codegen:1.0.0-SNAPSHOT" 
}
``` 

License
--------
The source code is based on:

- The [logansquare-compiler](https://github.com/bluelinelabs/LoganSquare/tree/6c5ec5281fb58d85a99413b7b6f55e9ef18a6e06/processor)
- The [moshi-kotlin-codegen](https://github.com/square/moshi/tree/b413423d0575849db7bdc6fcfbdb7d99f7e3a2c3/kotlin/codegen)

<br></br>

    Copyright 2015 BlueLine Labs, Inc.
    Copyright (C) 2018 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.