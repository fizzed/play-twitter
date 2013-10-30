package com.mfizz.play.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import play.Application;
import play.Configuration;
import play.Logger;
import play.Plugin;
import play.libs.Akka;
import scala.concurrent.duration.FiniteDuration;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import akka.dispatch.MessageDispatcher;

public class TwitterPlugin extends Plugin {

	public static final String ACCESS_TOKEN = "twitter.access-token";
	public static final String ACCESS_SECRET = "twitter.access-secret";
	public static final String CONSUMER_KEY = "twitter.consumer-key";
	public static final String CONSUMER_SECRET = "twitter.consumer-secret";
	public static final String REFRESH_INTERVAL = "twitter.refresh-interval";
	
	private String accessToken;
	private String accessSecret;
	private String consumerKey;
	private String consumerSecret;
	private long refreshInterval;
	// available once plugin starts
	private TwitterFactory factory;
	private AccessToken atoken;
	private CachedObj<List<Status>> tweets;
	
    private final Application application;

    public TwitterPlugin(Application application) {
        this.application = application;
        // create an empty list by default
        this.tweets = new CachedObj<List<Status>>(new ArrayList<Status>());
    }

    /**
     * Reads the configuration file, verifies configuration will make api calls, and
     * starts job that will refresh tweets periodically.
     */
    @Override
    public void onStart() {
        Configuration configuration = application.configuration();
        
        // configure plugin from application.conf
        this.accessToken = configuration.getString(ACCESS_TOKEN);
        this.accessSecret = configuration.getString(ACCESS_SECRET);
        this.consumerKey = configuration.getString(CONSUMER_KEY);
        this.consumerSecret = configuration.getString(CONSUMER_SECRET);
        this.refreshInterval = configuration.getMilliseconds(REFRESH_INTERVAL, 30000L);
        
        // TODO: verify all required settings were set
        
        // create factory and initial access token
        this.factory = new TwitterFactory();
    	this.atoken = new AccessToken(this.accessToken, this.accessSecret);

    	try {
    		// verify twitter config works by excuting api call (fetching screen name)
    		Twitter twitter = createTwitter();
    		String handle = twitter.getScreenName();
    		Logger.info("twitter plugin: verified configuration - authenticated as [@" + handle + "]");
    	} catch (Exception e) {
    		Logger.warn("Unable to verify twitter plugin will work", e);
    	}
        
    	if (this.refreshInterval <= 0) {
    		Logger.info("twitter plugin: refreshInterval <= 0; skipping refresh job");
    	} else {
	    	// create job that will be run to update
	    	RefreshJob job = new RefreshJob(this);
	    	// force it to run at startup so tweets are immediately available
	    	job.run();
	
	    	// create job to run every X milliseconds
	    	String dispatcherName = "TwitterUpdateJob";
	        Logger.info("Scheduling job [" + dispatcherName + "] to run every [" + this.refreshInterval + " ms]");
	        Akka.system().scheduler().schedule(
	        	FiniteDuration.create(0, TimeUnit.MILLISECONDS),
	        	FiniteDuration.create(this.refreshInterval, TimeUnit.MILLISECONDS),
	        	job,
	        	Akka.system().dispatcher()
	        );
    	}
        
        Logger.info("twitter plugin: started");
    }
    
    public Twitter createTwitter() {
    	// fetch fresh tweets
		Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer(this.consumerKey, this.consumerSecret);
        twitter.setOAuthAccessToken(this.atoken);
        return twitter;
    }
    
    public List<Status> tweets() {
    	return tweets.value();
    }
    
    public List<Status> tweets(int max) {
    	List<Status> t = tweets.value();
    	if (t.size() < max) {
    		return t;
    	} else {
    		return t.subList(0, max);
    	}
    }
    
    public void setTweets(List<Status> tweets) {
    	this.tweets.set(tweets);
    }

	public String accessToken() {
		return accessToken;
	}

	public String accessSecret() {
		return accessSecret;
	}

	public String consumerKey() {
		return consumerKey;
	}

	public String consumerSecret() {
		return consumerSecret;
	}

	public long refreshInterval() {
		return refreshInterval;
	}
	
}
