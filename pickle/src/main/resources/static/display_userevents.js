// Function to smoothly scroll to events section
function scrollToEventsSection() {
    const eventsSection = document.querySelector('.events-display-section');
    if (eventsSection) {
        eventsSection.scrollIntoView({ 
            behavior: 'smooth',
            block: 'start'
        });
    }
}

// Code for the user events button
document.getElementById('userEventsButton').addEventListener('click', function () {
    const userEventsButton = document.getElementById('userEventsButton');
    const eventsContainer = document.getElementById('events');
    var username = document.getElementById('username1').value.trim();

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
            displayUserEvents(data);
            // Always show the events container and scroll to it
            eventsContainer.style.display = "block";
            scrollToEventsSection();
        })
        .catch(error => console.error('Error:', error));
});

// Function to display user events with professional card layout
function displayUserEvents(events) {
    const eventContainer = document.getElementById('events');
    eventContainer.innerHTML = '';

    if (events.length === 0) {
        eventContainer.innerHTML = `
            <div class="events-empty">
                <div class="events-empty-icon">👤</div>
                <h3 class="events-empty-title">No User Events Found</h3>
                <p class="events-empty-subtitle">This user has no events associated with their account.</p>
            </div>
        `;
        return;
    }

    events.forEach(event => {
        const eventElement = document.createElement('div');
        eventElement.className = 'event-card';
        
        const isFullCapacity = event.participants.length >= event.availability;
        
        eventElement.innerHTML = `
            <div class="event-card-header">
                <div class="event-date-time">${event.date} ${event.time}</div>
                <div class="event-id">Event ID: ${event.eventID}</div>
            </div>
            <div class="event-card-body">
                <div class="event-info">
                    <div class="event-info-row">
                        <div class="event-info-icon">📍</div>
                        <div class="event-info-label">Location:</div>
                        <div class="event-info-value">${event.location.location}</div>
                    </div>
                    <div class="event-info-row">
                        <div class="event-info-icon">👥</div>
                        <div class="event-info-label">Capacity:</div>
                        <div class="event-info-value">${event.participants.length}/${event.availability}</div>
                    </div>
                </div>
                
                <div class="event-owner">
                    <div class="event-owner-title">Event Owner</div>
                    <div class="event-owner-name">${event.owner.fname} ${event.owner.lname}</div>
                </div>
                
                <div class="event-availability ${isFullCapacity ? 'full' : ''}">
                    <div class="event-availability-icon">${isFullCapacity ? '🔴' : '🟢'}</div>
                    <div class="event-availability-text">
                        ${isFullCapacity ? 'Event Full' : 'Spots Available'}
                    </div>
                </div>
                
                <div class="event-actions">
                    <div class="event-button-grid">
                        <button class="event-action-btn" onclick="joinWaitlist('${event.eventID}')">Join Waitlist</button>
                        <button class="event-action-btn" onclick="leaveWaitlist('${event.eventID}')">Leave Waitlist</button>
                        <button class="event-action-btn" onclick="joinEvent('${event.eventID}')">Join Event</button>
                        <button class="event-action-btn" onclick="leaveEvent('${event.eventID}')">Leave Event</button>
                    </div>
                    
                    <div class="event-invite-section">
                        <h4 class="invite-title">Invite User</h4>
                        <input type="text" class="event-input" placeholder="Enter your username" id="username-${event.eventID}">
                        <input type="email" class="event-input" placeholder="Enter their email" id="email-${event.eventID}">
                        <button class="event-action-btn" onclick="inviteUserToEvent('${event.eventID}')">Send Invitation</button>
                    </div>
                </div>
            </div>
        `;
        
        eventContainer.appendChild(eventElement);
    });
}

// Helper functions for event actions
function joinWaitlist(eventId) {
    const username = prompt("Enter your username:");
    if (username) {
        fetch('/joinWaitlist', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ eventId: eventId, username: username })
        })
        .then(response => response.ok ? alert("Joined waitlist successfully.") : alert("Failed to join waitlist."))
        .catch(error => alert("An error occurred while joining waitlist."));
    }
}

function leaveWaitlist(eventId) {
    const username = prompt("Enter your username:");
    if (username) {
        fetch('/leaveWaitlist', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ eventId: eventId, username: username })
        })
        .then(response => response.ok ? alert("Left waitlist successfully.") : alert("Failed to leave waitlist."))
        .catch(error => alert("An error occurred while leaving waitlist."));
    }
}

function joinEvent(eventId) {
    const username = prompt("Enter your username:");
    if (username) {
        fetch('/joinEvent', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ eventID: eventId, username: username })
        })
        .then(response => response.ok ? alert("Joined event successfully.") : alert("Failed to join event."))
        .catch(error => alert("An error occurred while joining event."));
    }
}

function leaveEvent(eventId) {
    const username = prompt("Enter your username:");
    if (username) {
        fetch('/leaveEvent', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ eventID: eventId, username: username })
        })
        .then(response => response.ok ? alert("Left event successfully.") : alert("Failed to leave event."))
        .catch(error => alert("An error occurred while leaving event."));
    }
}

function inviteUserToEvent(eventId) {
    const username = document.getElementById(`username-${eventId}`).value;
    const email = document.getElementById(`email-${eventId}`).value;
    
    if (username && email) {
        fetch('/inviteUser', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userEmail: email, eventID: eventId, username: username })
        })
        .then(response => response.json())
        .then(data => alert(data.message))
        .catch(error => console.error('Error:', error));
    } else {
        alert("Please enter both username and email.");
    }
}

// Function to close the modal and reset the form and filter fields
function closeModal() {
    var modal = document.getElementById("editEventModal");
    modal.style.display = "none";
    resetForm();
}

// Function to handle edit event form submission
document.getElementById("editEventForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get event input values
    var eventId = document.getElementById("eventId2").value;
    var eventDate = document.getElementById("eventDate2").value;
    var eventTime = document.getElementById("eventTime2").value;
    var availability = document.getElementById("availability2").value;
    var location = document.getElementById("location2").value;

    // Create event parameters object
    var eventData = {
        eventId: eventId,
        date: eventDate,
        time: eventTime,
        availability: availability,
        location: location
    };

    // Send event creation request to the backend
    fetch("/editEvent", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(eventData),
    })
        .then((response) => response.json())
        .then((data) => {
            // Handle success response if needed
            console.log("Event edited successfully:", data);
            var modal = document.getElementById("editEventModal");
            modal.style.display = "none";
        })
        .catch((error) => {
            console.error("Error:", error);
            // Handle error response if needed
        });

    closeModal();
});

function createEditEventButton(text) {

    const button = document.createElement("button");
    button.textContent = text;
    button.setAttribute("type", "button");
    button.style.display = "inline-block";
    button.style.marginRight = "10px";
    button.setAttribute("id", "editEventButton");

    button.addEventListener('click', function () {
        var modal = document.getElementById("editEventModal");
        modal.style.display = "block";
    });
    return button;
}

// Function to close the modal and reset the form and filter fields
function closeModal1() {
    var modal = document.getElementById("deleteEventModal");
    modal.style.display = "none";
    resetForm();
}

// Function to handle delete event form submission
document.getElementById("deleteEventForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get event input values
    var eventId = document.getElementById("eventId3").value;

    // Send event creation request to the backend
    fetch("/deleteEvent", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            eventId: eventId
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            // Handle success response if needed
            console.log("Event deleted successfully:", data);
            var modal = document.getElementById("deleteEventModal");
            modal.style.display = "none";
        })
        .catch((error) => {
            console.error("Error:", error);
            // Handle error response if needed
        });

    closeModal1();
});


function createDeleteEventButton(text) {
    const button = document.createElement("button");
    button.textContent = text;
    button.setAttribute("type", "button");
    button.style.display = "inline-block";
    button.style.marginRight = "10px";
    button.setAttribute("id", "deleteEventButton");

    button.addEventListener('click', function () {
        var modal = document.getElementById("deleteEventModal");
        modal.style.display = "block";
    });
    return button;
}


function displayEvents(events) {
    // Assuming you have a <div> with id="eventContainer" to display events
    const eventContainer = document.getElementById('events');
    eventContainer.innerHTML = ''; // Clear existing content before displaying new events

    events.forEach(event => {
        const eventElement = document.createElement('div');
        eventElement.innerHTML = `
                <h2>${event.date} ${event.time}</h2>
                <p>EventID: ${event.eventID}</p>
                <p>Location: ${event.location.location}</p>
                <p>Owner: ${event.owner.fname} ${event.owner.lname}</p>
                <p>Availability: ${event.availability}</p>
                <p>Participants: ${event.participants.length}</p>
                
            `;

        const editEvent = createEditEventButton("Edit Event");

        const deleteEvent = createDeleteEventButton("Delete Event");


        eventElement.appendChild(editEvent);
        eventElement.appendChild(deleteEvent);

        eventContainer.appendChild(eventElement);

        const divider = document.createElement('hr');
        eventElement.appendChild(divider);

        editEvent.style.display = "inline-block";
        deleteEvent.style.display = "inline-block";

        editEvent.style.marginRight = "10px"; // Add some spacing between buttons if needed
        deleteEvent.style.marginRight = "10px";
    });
}



// // Create Event button functionality
// document.getElementById("editEventButton").addEventListener("click", function () {
//     var modal = document.getElementById("editEventModal");
//     modal.style.display = "block";
// });


// // Function to handle edit event form submission
// document.getElementById("editEventForm").addEventListener("submit", function (event) {
//     event.preventDefault(); // Prevent default form submission

//     // Get event input values
//     var eventDate = document.getElementById("eventDate").value;
//     var eventTime = document.getElementById("eventTime").value;
//     var availability = document.getElementById("availability").value;
//     var location = document.getElementById("location").value;

//     // Create event parameters object
//     var eventData = {
//         id:
//             date: eventDate,
//         time: eventTime,
//         availability: availability,
//         location: location
//     };

//     // Send event creation request to the backend
//     fetch("/editEvent", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify(eventData),
//     })
//         .then((response) => response.json())
//         .then((data) => {
//             // Handle success response if needed
//             console.log("Event edited successfully:", data);
//             var modal = document.getElementById("editEventModal");
//             modal.style.display = "none";
//         })
//         .catch((error) => {
//             console.error("Error:", error);
//             // Handle error response if needed
//         });

//     closeModal();
// });
