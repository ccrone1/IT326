function clearForm() {
    // Clear input values

    document.querySelector('input[name="userName"]').value = "";
    document.querySelector('input[name="password"]').value = "";
}

document.querySelector(".btn-login").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get user input values
    var userName = document.querySelector('input[name="userName"]').value;
    var password = document.querySelector('input[name="password"]').value;

    // Create user data object
    var userData = {
        userName: userName,
        password: password
    };

    // Send user registration request to the backend
    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
    })
    .then((response) => {
        if (response.ok) {
            // Handle successful login
            alert("You have successfully logged in to profile " + userName + ".");
            // Redirect to profile page or perform other actions
        } else {
            // Handle login failure
            alert("Invalid username or password. Please try again.");
        }
    })
    .catch((error) => {
        // Handle network errors or other exceptions
        console.error("Error logging in:", error);
        alert("An error occurred while logging into your account. Please try again later.");
    });

clearForm(); // Clear input fields after form submission
});
