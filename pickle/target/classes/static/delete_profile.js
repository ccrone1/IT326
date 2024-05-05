document.getElementById('deleteProfileButton').addEventListener('click', function () {
    var username = document.getElementById('username').value;
    if (username.trim() !== '') {
        // Prompt user for confirmation
        if (confirm("Are you sure you want to delete profile with username " + username + "?")) {
            // Call deleteProfile function on confirmation
            deleteProfile(username);
        } else {
            // Do nothing if user cancels
        }
    } else {
        alert("Please enter a valid username.");
    }
});

function deleteProfile(username) {
    fetch('/deleteProfile', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify({
            username: username
        })
    })
        .then(response => response.json())
        .then(data => {
            // Show the alert message on the page
            alert(data.message);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}