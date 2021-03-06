package tuwien.ac.at.db.relational.model.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.TwitterException;

/**
 * @author Vanja Bisanovic
 *
 */
public class TwitterUserDTO {

	private String screenname;
	private int tweets;
	private int followersNumber;
	private int followeesNumber;
	private String location;
	private String description;
	private File tweetsFile; // is the file where tweets are store

	public TwitterUserDTO() {
	}

	public TwitterUserDTO(String screenname, int amountOfTweets, int followers,
			int followees, String location, String name) {
		this.screenname = screenname;
		this.tweets = amountOfTweets;
		this.followersNumber = followers;
		this.followeesNumber = followees;
		this.location = location;
		this.description = name;
	}

	public TwitterUserDTO(String screenname, int amountOfTweets, int followers,
			int followees, File tweets) {
		super();
		this.screenname = screenname;
		this.tweets = amountOfTweets;
		this.followersNumber = followers;
		this.followeesNumber = followees;
		this.tweetsFile = tweets;
	}

	public String getScreenname() {
		return screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	public int getAmountOfTweets() {
		return tweets;
	}

	public void setAmountOfTweets(int amountOfTweets) {
		this.tweets = amountOfTweets;
	}

	public int getFollowers() {
		return followersNumber;
	}

	public void setFollowers(int followers) {
		this.followersNumber = followers;
	}

	public int getFollowees() {
		return followeesNumber;
	}

	public void setFollowees(int followees) {
		this.followeesNumber = followees;
	}

	// File cane also be generated by following a special name convention
	public File getTweets() {
		return tweetsFile;
	}

	public void setTweets(File tweets) {
		this.tweetsFile = tweets;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TwitterUserDTO> getAllFollowers() {
		List<TwitterUserDTO> followers = new ArrayList<TwitterUserDTO>();
		long cursor = -1;
		IDs ids;
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		try {

			ids = twitter.getFollowersIDs(getScreenname(), cursor);
			int counter = 0;
			do {
				for (long id : ids.getIDs()) {
					if (counter > 10)
						return followers;
					User user = twitter.showUser(id);
					// System.out.println("Screen name: "+user.getScreenName()+" Followers: "+user.getFollowersCount()+" Followees: "+user.getFriendsCount()+" Tweets: "+user.getStatusesCount()+" Location: "+user.getLocation());
					TwitterUserDTO t = new TwitterUserDTO(user.getScreenName(),
							user.getFollowersCount(), user.getFriendsCount(),
							user.getStatusesCount(), user.getLocation(),
							user.getName());
					followers.add(t);
					counter++;
				}
			} while ((cursor = ids.getNextCursor()) != 0);
		} catch (TwitterException e) {
			System.out.println("Error: " + e.getErrorMessage());
		}
		return followers;
	}
}
