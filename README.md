Mfizz play-module-twitter
===========================================================

Play framework 2.x module to fetch, cache, and display tweets from Twitter

http://mfizz.com/oss/play-module-twitter

How-to guide for creating Play 2.x modules using this project as an example:
http://mfizz.com/blog/2013/07/play-framework-module-maven-central

## Installation

Play framework 2.x:

* add ```"com.mfizz" %% "mfz-play-module-twitter" % "1.0"``` to your dependencies (```project/Build.scala```)

* add ```1000:com.mfizz.play.twitter.TwitterPlugin``` to your ```conf/play.plugins```

The following parameters can be configured in ```conf/application.conf```

```
twitter.access-token = "required: replace with twitter access token"
twitter.access-secret =  "required: replace with twitter access secret"
twitter.consumer-key = "required: replace with twitter consumer key"
twitter.consumer-secret = "required: replace with twitter consumer secret"
# optional
twitter.refresh-interval = 60m
```

## Using it from Java: 

```java
package controllers;

import com.mfizz.play.twitter.TwitterPlugin;

import play.*;
import play.mvc.*;

public class Application extends Controller {
  
    static public TwitterPlugin TWITTER_PLUGIN = Play.application().plugin(TwitterPlugin.class);
	
    public static Result index() {
        return ok(views.html.index.render());
    }
    
    public static TwitterPlugin twitter() {
    	return TWITTER_PLUGIN;
    }
  
}
```

## Licence

This software is licensed under the Apache 2 license, quoted below.

Copyright 2013 Mfizz Inc (http://mfizz.com).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
