import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Twitter {

	long clock = 0;
	
	class User{
		private HashSet<User> followees;
		
		private HashMap<Integer, Long> tweets;
		
		private int id;
		
		public User(int id){
			this.id = id;
			followees = new HashSet<>();
			tweets = new HashMap<>();
		}
		
		public HashSet<User> getFollowees(){
			return followees;
		}
		
		public void addFollowee(int followeeID){
			if(!record.containsKey(followeeID)){
				User newUser = new User(followeeID);
				record.put(followeeID, newUser);
			}
			followees.add(record.get(followeeID));
		}
		
		public void removeFollowee(int followeeID){
			followees.remove(record.get(followeeID));
		}
		
		public void addTweet(int tweetID){
			System.out.println("Adding new Tweet!");
			tweets.put(tweetID, clock);
			clock++;
		}
		
		public List<Integer> getNewsFeed(){
			HashMap<Integer, Long> feedMap = (HashMap<Integer, Long>) tweets.clone();
			for(User user : followees){
				for(Integer tweetID : user.getTweets().keySet()){
					feedMap.put(tweetID, user.getTweets().get(tweetID));
				}
			}
			
			int top10Tweets[] = new int[10];
			Long top10TweetTimes[] = new Long[10];
			
			if(feedMap.isEmpty()) return new ArrayList<>();
			
			for(int i = 0 ; i < 10 ; i++){
				top10TweetTimes[i] = -1L;
				top10Tweets[i] = Integer.MIN_VALUE;
			}
			
			for(int tweets : feedMap.keySet()){
				int atPos = -1;
				for(Long tweetTimes : top10TweetTimes){
					atPos++;
					if(tweetTimes < feedMap.get(tweets)){
						if(atPos == 9){
							top10TweetTimes[9] = feedMap.get(tweets);
							top10Tweets[9] = tweets;
						}else{
							for(int x = 9 ; x > atPos ; x--){
								top10TweetTimes[x] = top10TweetTimes[x - 1];
								top10Tweets[x] = top10Tweets[x - 1];
							}
							top10TweetTimes[atPos] = feedMap.get(tweets);
							top10Tweets[atPos] = tweets;
						}
						break;
					}
				}
			}
			ArrayList<Integer> result = new ArrayList<>();
			for(int x : top10Tweets){
				if(x == Integer.MIN_VALUE)
					break;
				result.add(x);
			}
			
			return result;
		}
		
		public HashMap<Integer, Long> getTweets(){
			return tweets;
		}
	}
	
    private HashMap<Integer, User> record;

    /** Initialize your data structure here. */
    public Twitter() {
        record = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        User user;
        if(record.containsKey(userId)){
        	user = record.get(userId);
        }else{
        	user = new User(userId);
        	record.put(userId, user);
        }
        user.addTweet(tweetId);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
    	if(record.containsKey(userId)){
    		User user = record.get(userId);
        	return user.getNewsFeed();
    	}else{
    		return new ArrayList<>();
    	}
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
    	User follower;
    	if(record.containsKey(followerId)){
    		follower = record.get(followerId);
    	}else{
    		follower = new User(followerId);
    		record.put(followerId, follower);
    	}
            	
        follower.addFollowee(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
    	User follower;
    	if(record.containsKey(followerId)){
    		follower = record.get(followerId);
    	}else{
    		return;
    	}
    	
        follower.removeFollowee(followeeId);
    }
    
    public static void DisplayCommands(){
    	System.out.println("1. Post Tweet (userID, tweetID)");
    	System.out.println("2. Get News Feed (userID)");
    	System.out.println("3. Follow (followerID, followeeID)");
    	System.out.println("4. UnFollow (followerID, followeeID)");
    	System.out.println("5. Show all Status");
    	System.out.println("6. Exit");
    	
    	System.out.println("\nEnter your choice: ");
    }
    
    public static void displaySatus(Twitter obj){
    	System.out.println("Total Users: " + obj.record.size());
    	System.out.println("----------------------------");
    	for(int id : obj.record.keySet()){
    		System.out.println("User ID: " + id);
    		System.out.println("Followees: ");
    		int totalFollowees = obj.record.get(id).getFollowees().size();
    		for(User user : obj.record.get(id).getFollowees()){
    			if(totalFollowees == 1)
    				System.out.print(user.id);
    			else
    				System.out.print(user.id + ", ");
    			totalFollowees--;
    		}
    		System.out.println("Tweets (ID, Date): ");
    		for(int tID : obj.record.get(id).getTweets().keySet()){
    			System.out.println("(" + tID + ", " + obj.record.get(id).getTweets().get(tID) + ")");
    		}
    		System.out.println("----------------------------");
    	}
    	
    }
    
    public static void main(String[] a){
    	Twitter obj = new Twitter();
    	Scanner sc = new Scanner(System.in);
    	
    	DisplayCommands();
    	
    	int x = sc.nextInt();
    	while(true){
    		switch (x) {
    		case 1:
    			System.out.println("Enter user ID: ");
    			int uID = sc.nextInt();
    			System.out.println("Enter Tweet ID: ");
    			int tID = sc.nextInt();
    			obj.postTweet(uID, tID);
    			break;
    			
    		case 2:
    			System.out.println("Enter User ID: ");
    			uID = sc.nextInt();
    			System.out.println(obj.getNewsFeed(uID));
    			break;
    			
    		case 3:
    			System.out.println("Enter Follower ID: ");
    			uID = sc.nextInt();
    			System.out.println("Enter Followee ID: ");
    			int u2ID = sc.nextInt();
    			obj.follow(uID, u2ID);
    			break;
    			
    		case 4:
    			System.out.println("Enter Follower ID: ");
    			uID = sc.nextInt();
    			System.out.println("Enter Followee ID: ");
    			u2ID = sc.nextInt();
    			obj.unfollow(uID, u2ID);
    			break;
    			
    		case 5:
    			displaySatus(obj);
    			break;
    		case 6:
    			System.exit(1);
    		default:
    			break;
    		}
    		DisplayCommands();
    		x = sc.nextInt();
    	}
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */