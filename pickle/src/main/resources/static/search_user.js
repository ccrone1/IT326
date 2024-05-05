document.addEventListener('DOMContentLoaded', function () {
    const findUsersButton = document.querySelector('.btn-find-users');

    findUsersButton.addEventListener('click', function () {

        const searchQuery = document.querySelector('.search-bar').value.trim();

        // Check if search query is not empty
        if (searchQuery !== '') {
            fetch('/find-users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username: searchQuery })
            })
            .then(response => response.json())
            .then(user => {
                // Process user data and display on your HTML page
                displayUser(user);
            })
            .catch(error => console.error('Error:', error));
        } else {
            alert('Please enter a username.'); // Alert user if username is empty
        }
    });

    function displayUser(user) {
        // Assuming you have a <div> with class="user-details" to display user details
        const userDetailsContainer = document.querySelector('.user-details');
        userDetailsContainer.innerHTML = ''; // Clear existing content before displaying new user details

        if (user) {
            const userElement = document.createElement('div');
            userElement.innerHTML = `
                <h2>User Details</h2>
                <p>Username: ${user.username}</p>
                <p>First Name: ${user.fname}</p>
                <p>Last Name: ${user.lname}</p>
            `;
            userDetailsContainer.appendChild(userElement);
        } else {
            userDetailsContainer.innerHTML = '<p>User not found.</p>';
        }
    }
});
