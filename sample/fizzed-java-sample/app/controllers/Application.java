package controllers;

import com.fizzed.play.twitter.TwitterPlugin;

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
