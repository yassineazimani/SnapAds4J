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

#### Download

##### Maven

I choose to don't use Maven Central Repository due to the policy, indeed developers can't
have full control of the jar published in the central repository, for example : delete jar.
Meanwhile, i use Github packages. To download and install SnapAds4J with Github packages, you
have to use a github token (with read packages permission) because we can't download with
maven without token : 

[See Reason](https://github.community/t5/GitHub-API-Development-and/Download-from-Github-Package-Registry-without-authentication/m-p/35501#M3312) 

```xml
 <dependency>
   <groupId>org.snapads4j</groupId>
   <artifactId>snapads4j</artifactId>
   <version>1.0.1</version>
 </dependency>
```

##### Jar 

Latest stable version : 

[Download](https://github.com/yassineazimani/SnapAds4J/releases/tag/v1.0.1)

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

![diagram-access-token](https://github.com/yassineazimani/SnapAds4J/blob/develop/img/diagram-access-token.png)

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

```java
    // Create a Creative using the media created :
    SnapCreative snapCreative = new SnapCreative();
    Creative creative = new Creative();
    creative.setAdAccountId(adAccountID);
    creative.setBrandName("Maxiumum Brand");
    creative.setName("Creative Creative");
    creative.setShareable(true);
    creative.setHeadline("Healthy Living");
    creative.setType(CreativeTypeEnum.SNAP_AD);
    creative.setTopSnapMediaId(mediaCreated.get().getId());
    try {
        Optional<Creative> creativeCreated = snapCreative.createCreative(oAuthAccessToken, creative);
    } catch (Exception e) {
        e.printStackTrace();
    }
```

#### Step 5 : Create Campaign

```java
// Create campaign :
SnapCampaigns sc = new SnapCampaigns();
Campaign c = new Campaign();
c.setName("Cool Campaign");
c.setAdAccountId(adAccountID);
c.setStatus(StatusEnum.PAUSED);
c.setStartTime(new Date());
try {
    Optional<Campaign> campaignCreated = sc.createCampaign(oAuthAccessToken, c);
} catch (Exception e) {
    e.printStackTrace();
}
```

#### Step 6 : Create Ad Squad

```java
    // Create AdSquad :
    SnapAdSquads sAdSquads = new SnapAdSquads();
    List<GeoLocation> geos = new ArrayList<>();
    geos.add(new GeoLocation.Builder().setCountryCode("us").build());
    AdSquad adSquad = new AdSquad();
    adSquad.setOptimizationGoal(OptimizationGoalEnum.IMPRESSIONS);
    adSquad.setPlacement(PlacementEnum.SNAP_ADS);
    adSquad.setType(AdSquadTypeEnum.SNAP_ADS);
    adSquad.setCampaignId(campaignCreated.getId());
    adSquad.setBidMicro(1000000.);
    adSquad.setDailyBudgetMicro(1000000000.);
    adSquad.setLifetimeBudgetMicro(300.);
    adSquad.setName("AdSquad2019");
    adSquad.setStatus(StatusEnum.PAUSED);
    adSquad.setTargeting(new Targeting.Builder().setGeolocation(geos).build());
    try {
        Optional<AdSquad> optAdSquad = sAdSquads.createAdSquad(oAuthAccessToken, adSquad);
    } catch (Exception e) {
        e.printStackTrace();
    }
```

#### Step 7 : Create Ad

```java
    // Create Ad :
    SnapAd snapAd = new SnapAd();
    Ad ad = new Ad();
    ad.setAdSquadId(optAdSquad.get().getId());
    ad.setCreativeId(creativeCreated.get().getId());
    ad.setName("Ad one");
    ad.setStatus(StatusEnum.ACTIVE);
    try {
        Optional<Ad> adCreated = snapAd.createAd(oAuthAccessToken, ad);
    } catch (Exception e) {
        e.printStackTrace();
    }
```

#### And now ?

It's not enough for you ? You want to see advanced functionnalities ? Go see Code examples :)

## Code examples

[Click here to access our examples](https://github.com/yassineazimani/SnapAds4J-examples)

## Wanna join the project ?

### Signal bugs

A project is never perfect, we can found any bugs at any moments. If you find a bug, you could write an issue by following some rules to create a issue.

### Give new ideas to improve SnapAds4J

You think SnapAds4J can have more functionnalities ?! It's great, it'll make SnapAds4J more stronger than ever ! Just create a issue with your idea by following some rules to create a issue.

### Rules to create a issue to signal a bug

When you create a issue for a bug, make sure you selected label bug. You have to describe your problem, how to reproduct it, the OS used and of course keep good behaviour.

### Rules to create a issue for giving a new idea

When you create a issue to give a new idea to improve SnapAds4J, make sure you selected label enhancement. You have to describe your idea with some examples if you could and then write the reason to develop this new idea.

### Help us to develop some features

This project is open source, so you could contribute by develop some issues. Just respect some rules before to develop these issues.

### Rules to develop some features

1. Check if the issue which contains the feature exists, if it doesn't exist, create it.

2. Create a branch from develop branch

3. Make your feature with complete unit tests

4. Don't forget to write your name in contributor.md

5. Once it's done, ask a pull request to merge your branch into develop branch

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


