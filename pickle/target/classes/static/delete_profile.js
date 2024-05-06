function clearForm() {
    // Clear input values
    document.querySelector('input[name="firstName"]').value = "";
    document.querySelector('input[name="lastName"]').value = "";
    document.querySelector('input[name="userName"]').value = "";
    document.querySelector('input[name="skillLevel"]').value = "";
    document.querySelector('input[name="email"]').value = "";
    document.querySelector('input[name="password"]').value = "";
}

document.getElementById("btn-delete").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default form submission
    var userName = document.querySelector('input[name="userName"]').value;
    console.log("Username: "+ userName);//log the username to verify 
    if (confirm("Are you sure you want to delete your profile "+userName +"?")) {
        // Get user input values
        //var userName = document.querySelector('input[name="userName"]').value;
      
        // Send user registration request to the backend
        fetch("/deleteProfile", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username:userName}
            ),
        })
            .then((response) => response.json())
            .then((data) => {
                // Handle success response if needed
                console.log("User deleted successfully:", data);
                // For example, redirect to profile page, show success message, etc.
                alert("Your profile "+ userName +" has been deleted successfully.");
            })
            .catch((error) => {
                console.error("Error deleting user:", error);
                // Handle error response if needed
                alert("An error occurred while deleting your profile. Please try again later.");
            });

        clearForm();
}
});
