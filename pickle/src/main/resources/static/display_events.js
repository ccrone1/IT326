document.addEventListener('DOMContentLoaded', function () {
    const generateDetailsButton = document.getElementById('generateEventDetailsButton');

    generateDetailsButton.addEventListener('click', function () {
        fetch('/events', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({}) // You can pass any necessary data here
        })
            .then(response => response.json())
            .then(data => {
                // Process data and display on your HTML page
                displayEvents(data);
            })
            .catch(error => console.error('Error:', error));
    });

    function displayEvents(events) {
        // Assuming you have a <div> with id="eventContainer" to display events
        const eventContainer = document.getElementById('eventContainer');
        eventContainer.innerHTML = ''; // Clear existing content before displaying new events

        events.forEach(event => {
            const eventElement = document.createElement('div');
            eventElement.innerHTML = `
                <h2>${event.date} ${event.time}</h2>
                <p>Location: ${event.location.location}</p>
                <p>Owner: ${event.user.userName}</p>
                <p>Availability: ${event.availability}</p>
                <p>Participants: ${event.participants.length}</p>
                <hr>
            `;
            eventContainer.appendChild(eventElement);
        });
    }
});
