document.getElementById('addFollowerButton').addEventListener('click', function () {
    var modal = document.getElementById("addFollowerModal");
    modal.style.display = "block";
});


// Function to close the modal and reset the form and filter fields
function closeModal() {
    var modal = document.getElementById("addFollowerModal");
    modal.style.display = "none";
    resetForm();
}

// Function to handle delete event form submission
document.getElementById("addFollowerForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get event input values
    var user_username = document.getElementById("user_username").value;
    var follower_username = document.getElementById("follower_username").value;

    // Send event creation request to the backend
    fetch("/addFollower", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            user_username: user_username,
            follower_username: follower_username
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            // Handle success response if needed
            console.log("Event deleted successfully:", data);
            var modal = document.getElementById("addFollowerModal");
            modal.style.display = "none";
        })
        .catch((error) => {
            console.error("Error:", error);
            // Handle error response if needed
        });

    closeModal();
});




document.getElementById('removeFollowerButton').addEventListener('click', function () {
    var modal = document.getElementById("removeFollowerModal");
    modal.style.display = "block";
});


// Function to close the modal and reset the form and filter fields
function closeModal1() {
    var modal = document.getElementById("removeFollowerModal");
    modal.style.display = "none";
    resetForm();
}

// Function to handle delete event form submission
document.getElementById("removeFollowerForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get event input values
    var user_username = document.getElementById("user_username2").value;
    var follower_username = document.getElementById("follower_username2").value;

    // Send event creation request to the backend
    fetch("/removeFollower", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            user_username: user_username,
            follower_username: follower_username
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            // Handle success response if needed
            console.log("Event deleted successfully:", data);
            var modal = document.getElementById("removeFollowerModal");
            modal.style.display = "none";
        })
        .catch((error) => {
            console.error("Error:", error);
            // Handle error response if needed
        });

    closeModal1();
});
