document.addEventListener('DOMContentLoaded', function () {
    const userEventsButton = document.getElementById('userEventsButton');
    const eventsContainer = document.getElementById('events');
    var username = document.getElementById('username1').value;

    userEventsButton.addEventListener('click', function () {
        fetch('/userEvents', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username
            })
        })
        .then(response => response.json())
        .then(data => {
            // Process data and display on your HTML page
            displayEvents(data);
           // eventsContainer.style.display = 'block';
            if (eventsContainer.style.display ==="block") {
                eventsContainer.style.display = "none";
            } else {
                eventsContainer.style.display = "block";
                eventsContainer.style.padding="20px";
                eventsContainer.style.textAlign="center";
            }

        })
        .catch(error => console.error('Error:', error));
    });

    function displayEvents(events) {
        // Assuming you have a <div> with id="eventContainer" to display events
        const eventContainer = document.getElementById('events');
        eventContainer.innerHTML = ''; // Clear existing content before displaying new events

        events.forEach(event => {
            const eventElement = document.createElement('div');
            eventElement.innerHTML = `
                <h2>${event.date} ${event.time}</h2>
                <p>Location: ${event.location.location}</p>
                <p>Owner: ${event.owner.fname} ${event.owner.lname}</p>
                <p>Availability: ${event.availability}</p>
                <p>Participants: ${event.participants.length}</p>
                <hr>
            `;

            const editEvent = document.createElement("button");
            editEvent.textContent = "Edit Event"; // Set the button text
            editEvent.setAttribute("type", "button"); // Set button type

            const deleteEvent = document.createElement("button");
            deleteEvent.textContent = "Delete Event"; // Set the button text
            deleteEvent.setAttribute("type", "button"); // Set button type
            // Append the button to a parent element (assuming eventsContainer is the parent)


            editEvent.style.display = "inline-block";
            deleteEvent.style.display = "inline-block";

            editEvent.style.marginRight = "10px"; // Add some spacing between buttons if needed
            deleteEvent.style.marginRight = "10px";

            eventElement.appendChild(editEvent);
            eventElement.appendChild(deleteEvent);

            eventContainer.appendChild(eventElement);
        });

        // Show the events container
        eventsContainer.style.display = 'block';
    }
});
