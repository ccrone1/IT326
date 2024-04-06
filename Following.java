public class Following {
    private Profile follower;
    private Profile following;

    public Following(Profile follower, Profile following) {
        this.follower = follower;
        this.following = following;
    }

    // Get and set for following and follower
    public Profile getFollower() {
        return follower;
    }

    public void setFollower(Profile follower) {
        this.follower = follower;
    }

    public Profile getFollowing() {
        return following;
    }

    public void setFollowing(Profile following) {
        this.following = following;
    }

    // methods this is just a draft of what the method will do
    public void addUser(Profile profile) { // need to incorporate user class
        /*
         * if (profile != null) {
         * if (follower == null) {
         * follower = profile;
         * } else if (following == null) {
         * following = profile;
         * } else {
         * System.out.println("cant add user");
         * }
         * } else {
         * System.out.println("user cant be added");
         * }
         */
    }

    public void removeUser(Profile profile) { // need to incorporate user class
        /*
         * if (profile != null) {
         * if (follower != null && follower.equals(profile)) {
         * follower = null;
         * } else if (following != null && following.equals(profile)) {
         * following = null;
         * } else {
         * System.out.println("user not found");
         * }
         * } else {
         * System.out.println("cant be removed");
         * }
         */
    }
}
