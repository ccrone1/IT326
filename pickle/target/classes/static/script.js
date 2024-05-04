function toggleButton() {
    var button = document.getElementById('inviteButton');
    var email = document.getElementById('userEmail').value;
    if (button.innerHTML === 'Invite User') {
        // Prompt user for confirmation
        if (confirm("Are you sure you want to invite this user?")) {
            // Proceed with invitation
            inviteUser(email);
            button.innerHTML = 'Cancel Invitation';
            button.setAttribute('onclick', 'cancelInvitation()');
        } else {
            // Do nothing if user cancels
        }
    } else {
        // Prompt user for confirmation
        if (confirm("Are you sure you want to cancel this invitation?")) {
            // Proceed with invitation
            inviteUser(email);
            button.innerHTML = 'Cancel Invitation';
            button.setAttribute('onclick', 'cancelInvitation()');
        } else {
            // Do nothing if user cancels
        }
    }
}

function inviteUser(userEmail) {
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


function cancelInvitation() {
    fetch('/cancelInvite', {
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
