document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('inviteButton').addEventListener('click', function () {
        var userEmail = document.getElementById('userEmail').value;
        if (userEmail.trim() !== '') {
            // Prompt user for confirmation
            if (confirm("Are you sure you want to invite " + userEmail + "?")) {
                // Call sendInvitation function on confirmation
                const button = document.getElementById('inviteButton');
                if (button.innerHTML == 'Cancel Invitation') {
                }
                else {
                    sendInvitation(userEmail);
                }
                // Update button for cancellation after confirmation
                updateButtonForCancellation(userEmail);
            } else {
                // Do nothing if user cancels
            }
        } else {
            alert("Please enter a valid email address.");
        }
    });

    function sendInvitation(userEmail) {
        fetch('/inviteUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userEmail: userEmail
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

    function cancelInvitation(userEmail) {
        // Call the controller to cancel the invitation
        fetch('/cancelInvite', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                // Provide any necessary data for canceling the invitation
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

    function updateButtonForCancellation(userEmail) {
        const button = document.getElementById('inviteButton');
        button.innerHTML = 'Cancel Invitation';
        button.removeEventListener('click', sendInvitation); // Remove sendInvitation listener
        button.addEventListener('click', () => cancelInvitation(userEmail)); // Add cancelInvitation listener
    }
});
