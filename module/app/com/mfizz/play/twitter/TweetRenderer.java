package com.mfizz.play.twitter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import com.twitter.Autolink;

public class TweetRenderer {
	
	public static final long MIN_MILLIS = 60*1000;
	public static final long HOUR_MILLIS = 60*MIN_MILLIS;
	public static final long DAY_MILLIS = 24*HOUR_MILLIS;
	/** Jul 4, 2013 */
	public static final DateTimeFormatter FORMAT_MMM_d_YYYY_FORMAT = DateTimeFormat.forPattern("MMM d, YYYY").withZone(DateTimeZone.UTC);
	
	static public String maybePlural(int value, String single, String plural) {
		if (value == 1) {
			return value + " " + single;
		} else {
			return value + " " + plural;
		}
	}
	
	// 5 secs ago, 40 mins ago, 2 hours ago, Apr 23 2012
	static public String renderTimestamp(long ts) {
		DateTime dt = new DateTime(ts, DateTimeZone.UTC);
		DateTime now = new DateTime(DateTimeZone.UTC);
		
		long diffms = now.getMillis() - dt.getMillis();
		
		if (diffms <= MIN_MILLIS) {
			return maybePlural(Seconds.secondsBetween(dt, now).getSeconds(), "sec", "secs") + " ago";
		} else if (diffms <= HOUR_MILLIS) {
			return maybePlural(Minutes.minutesBetween(dt, now).getMinutes(), "min", "mins") + " ago";
		} else if (diffms <= DAY_MILLIS) {
			return maybePlural(Hours.hoursBetween(dt, now).getHours(), "hour", "hours") + " ago";
		} else {
			return FORMAT_MMM_d_YYYY_FORMAT.print(dt);
		}
	}
	
	// <p><a href="">@@htmlstream</a> At vero eos major web browsers <a href="#">http://t.co/sBav7dm</a> <span>5 hours ago</span></p>
	static public String renderHtml(Status status) {
		// TODO: not the most efficient rendering but works in a jam
		
		StringBuilder html = new StringBuilder();
		
		html.append("<p>");
		
		User user = status.getUser();
		html.append("<a href=\"https://twitter.com/" + user.getScreenName() + "\">@" + user.getScreenName() + "</a>: ");
		
		String text = status.getText();
		
		// auto link everything
		Autolink al = new Autolink();
		String textHtml = al.autoLink(text);
		
		// replace links with actual underlying link
		for (URLEntity ue : status.getURLEntities()) {
			//logger.debug("{}", ue);
			textHtml = textHtml.replace(">"+ue.getURL()+"<", ">"+ue.getDisplayURL()+"<");
		}
		
		html.append(textHtml);
		
		html.append("<span>" + renderTimestamp(status.getCreatedAt().getTime()) + "</span>");
		
		html.append("</p>");
		
		return html.toString();
	}
	
}
