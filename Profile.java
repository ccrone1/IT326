//Profile class to allow user to create, delete, and edit profile

public class Profile{
    private String firstName; 
    private String lastName; 
    private String email; 
    private String skillLevel; //String for now, may switch to int as it might go by number


    public void Profile(String firstName, String lastName, String email, String skillLevel ){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.skillLevel=skillLevel;
    }

    //Getters and Setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    // Operations
    public void createProfile(String firstName, String lastName, String email, String skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.skillLevel = skillLevel;
    }

    public void deleteProfile() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.skillLevel = null;
    }

    public void editProfile(String firstName, String lastName, String email, String skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.skillLevel = skillLevel;
    }
}
