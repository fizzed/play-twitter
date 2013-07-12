package com.mfizz.play.twitter;

import play.Logger;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;

public class RefreshJob implements Runnable {
	
	private final TwitterPlugin plugin;
	
	public RefreshJob(TwitterPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public void run() {
		Logger.info("Starting twitter update...");
		try {
			Twitter twitter = plugin.createTwitter();
        	ResponseList<Status> tweets = twitter.getUserTimeline();
        	// cache the tweets!
        	plugin.setTweets(tweets);
        	Logger.info("Completed update of tweets from twitter");
        } catch (Exception e) {
        	Logger.error("Unable to cleanly fetch new tweets", e);
        }
	}

}
