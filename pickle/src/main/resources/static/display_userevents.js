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
            displayEvents(data);
            // eventsContainer.style.display = 'block';
            if (eventsContainer.style.display === "block") {
                eventsContainer.style.display = "none";
            } else {
                eventsContainer.style.display = "block";
                eventsContainer.style.padding = "20px";
                eventsContainer.style.textAlign = "center";
            }

        })
        .catch(error => console.error('Error:', error));
});

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
