function clearForm() {
    // Clear input values
    document.querySelector('input[name="username"]').value = "";
}

document.querySelector(".btn-edit").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get user input values
    var username = document.querySelector('input[name="username"]').value;

    // Send request to fetch user data
    fetch("/editProfile" + username)
        .then((response) => response.json())
        .then((userData) => {
            if (userData.error) {
                console.error("Error fetching user data:", userData.error);
                // Handle error response if needed
                return;
            }

            // Display user data for editing
            console.log("User data:", userData);
            // For example, populate form fields with user data for editing

            // Handle user input for editing
            // This part is similar to createProfile.js, but with edit functionality
            // You can use a similar approach to createProfile.js to send updated user data to the backend
        })
        .catch((error) => {
            console.error("Error fetching user data:", error);
            // Handle error response if needed
        });

    clearForm();
});
