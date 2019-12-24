# Marketing API for Java (unofficial)

[![Build Status](https://travis-ci.org/yassineazimani/SnapAds4J.svg?branch=develop&kill_cache=1)](https://travis-ci.org/yassineazimani/SnapAds4J) [![Coverage Status](https://coveralls.io/repos/github/yassineazimani/SnapAds4J/badge.svg?branch=develop&kill_cache=1)](https://coveralls.io/github/yassineazimani/SnapAds4J?branch=develop) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This is unofficial SnapChat Marketing API binding library allows you to integrate into your Java application (or Web Application Java).

URL Marketing API SnapChat : https://developers.snapchat.com/ads/

## Features / Roadmap for current version

* Authentication
* User
* Organizations
* Funding Sources
* Ad Accounts
* Campaigns
* Ad Squads
* Ads
* Media
* Creatives
* Creative Elements
* Targeting
* Snap Audience Match
* SAM - Lookalikes
* Audience Size 
* Bid Estimate
* Audit Logs
* Snap Pixel
* Measurements

## Requirements

You need to have minimum JDK/JRE Java 8 to use SnapAds4J.
To download JDK/JRE, go here : 

https://adoptopenjdk.net/

## Get Started

### Download and install SnapAds4J

#### Maven integration

Coming soon...

#### Gradle integration

Coming soon...

#### Download

Latest stable version : 

Coming soon...

Add the jar file to your application classpath.

### Tutorial

#### Step 1 : Get access token

In first time, we have to get the access token to use SnapChat API Ads
functionnalities, without the access token, we can't do anything. So we'll follow this Web flow :

https://developers.snapchat.com/api/docs/#full-web-flow-example 

Let's consider we have a controller Web (Spring/Servlet JEE...). In this controller, we'll write
two endpoints, one to open the authorize link and another which represents a redirect url
used by SnapChat after successful authorize link. In this redirect url, we'll take the parameter code
given by SnapChat to get access token.   

In a controller of your choice, we'll prepare the authorize link to get access token. This link
will be opened in a browser... 

```java
    // Initialize Client ID, Redirect URI and Client Secret :
    SnapConfiguration config = new SnapConfiguration.Builder()
                    .setClientId("fake_client_id")
                    .setRedirectUri("fake_redirect_uri")
                    .setClientSecret("fake_client_secret")
                    .build();

    SnapAuthorization auth = new SnapAuthorization(config);

    // 1) Get the authorize link :

    String url = null;
    try {
        url = auth.getOAuthAuthorizationURI();
    } catch (SnapAuthorizationException e) {
        e.printStackTrace();
    }
            
    if(url != null){
        // Redirect the user to url given by auth.getOAuthAuthorizationURI();
    }

```

Note that if you have in your project, a properties file named snapads4j.properties (folder resources) with the entries client.id, client.secret, redirect.uri filled, you don't have to use SnapAuthorization with SnapConfiguration. Instead, do like this : 

```java
    // Client ID, Redirect URI and Client Secret initialized in snapads4j.properties
    SnapAuthorization auth = new SnapAuthorization();
```

Content of snapads4j.properties : 

```java
client.id={your_client_id}
client.secret={your_client_secret}
redirect.uri={your_redirect_url}
```

Now, in the other endpoint (Our redirect url for snapchat), catch the parameter code,
and call the function to get the access token...

```java
    String code = functionGetCodeParameter(); // Get code parameter from url
    if(code != null) {
        TokenResponse tokenResponse;
        try {
            tokenResponse = auth.getOAuthAccessToken(code);
            System.out.println("Access Token : " + tokenResponse.getAccessToken());
            // Let's save the access token in a cookie/session/database :
            saveMyAccessToken(tokenResponse.getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
```
We have the access token, so we can call any functions of the API... If your
access token expires, you can refresh it with refreshtoken :

```java
    try {
        TokenResponse tokenResponse = auth.refreshToken("token");
        String accessToken = tokenResponse.getAccessToken();
        // Then, do what do you want with this token
    } catch (Exception e) {
        e.printStackTrace();
    }
```
#### Step 2 : Create Organization, Funding Source and Ad Account

We have now access token :), let's create [Organization](https://ads.snapchat.com/getstarted?referral_code=IndigoPixie), [Funding Source](https://business.snapchat.com/) and [Ad Account](https://business.snapchat.com/). Once these entities created, we can use SnapAds4J for our tutorial !

#### Step 3 : Media

Now, we'll create a media which will be using for a creative object.

```java
    // Create a CreativeMedia which will represents the future file media :
    CreativeMedia media = new CreativeMedia();
    media.setAdAccountId(adAccountID);
    media.setName(name);
    media.setType(MediaTypeEnum.VIDEO);

    try {
        Optional<CreativeMedia> mediaCreated = snapMedia.createMedia(oAuthAccessToken, media);
        if(mediaCreated.isPresent()){
            File fileVideo = new File("video.mp4");
            snapMedia.uploadMediaVideo(oAuthAccessToken, mediaCreated.get().getId(), fileVideo);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
```

#### Step 4 : Creative

Coming soon...

#### Step 5 : Create Campaign

Coming soon...

#### Step 6 : Create Ad Squad

Coming soon...

#### Step 7 : Create Ad

Coming soon...

#### And now ?

It's not enough for you ? You want to see advanced functionnalities ? Go see Code examples :)

## Code examples

Coming soon...

## Wanna join the project ?

### Signal bugs

Coming soon...

### Give new ideas to improve SnapAds4J

Coming soon...

### Help us to develop some features

Coming soon...

## License

Apache License, Version 2.0

https://opensource.org/licenses/Apache-2.0

This projects uses Mockito : [License Mockito](https://github.com/mockito/mockito/wiki/License), 

AssertJ : [License AssertJ](https://github.com/joel-costigliola/assertj-core/blob/master/LICENSE.txt), 

Lombok : [License MIT Lombok](https://projectlombok.org/), 

Jackson : [License Jackson](https://github.com/FasterXML/jackson-core/blob/master/src/main/resources/META-INF/LICENSE), 

Log4J 2: [License Log4J 2](https://logging.apache.org/log4j/2.x/license.html), 

Apache commons (lang3, codec, collections, httpcomponents): [License Apache Commons](https://commons.apache.org/proper/commons-bsf/license.html), 

cobertura-maven-plugin: [License Corbertura Maven Plugin](http://www.mojohaus.org/cobertura-maven-plugin/license.html)


