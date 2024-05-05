function clearForm() {
    // Clear input values
    //document.querySelector('input[name="firstName"]').value = "";
    //document.querySelector('input[name="lastName"]').value = "";
    document.querySelector('input[name="userName"]').value = "";
    //document.querySelector('input[name="skillLevel"]').value = "";
    // document.querySelector('input[name="email"]').value = "";
    document.querySelector('input[name="password"]').value = "";
}

document.querySelector(".btn").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get user input values
    //var firstName = document.querySelector('input[name="firstName"]').value;
    //var lastName = document.querySelector('input[name="lastName"]').value;
    var userName = document.querySelector('input[name="userName"]').value;
    //var skillLevel = document.querySelector('input[name="skillLevel"]').value;
    //var email = document.querySelector('input[name="email"]').value;
    var password = document.querySelector('input[name="password"]').value;

    // Create user data object
    var userData = {
        //firstName: firstName,
        //lastName: lastName,
        userName: userName,
        //skillLevel: skillLevel,
        //email: email,
        password: password
    };

    // Send user registration request to the backend
    fetch("/loginProfile", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
    })
        .then((response) => response.json())
        .then((data) => {
            // Handle success response if needed
            console.log("User registered successfully:", data);
            // For example, redirect to profile page, show success message, etc.
        })
        .catch((error) => {
            console.error("Error registering user:", error);
            // Handle error response if needed
        });

    clearForm();
});
