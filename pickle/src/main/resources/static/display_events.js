document.addEventListener('DOMContentLoaded', function () {
    const generateDetailsButton = document.getElementById('generateEventDetailsButton');
    const eventsContainer = document.getElementById('events');

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

    function createButton(text, eventId) {
        const button = document.createElement("button");
        button.textContent = text;
        button.setAttribute("type", "button");
        button.style.display = "inline-block";
        button.style.marginRight = "10px";
    
        // Add event listener to the button
        button.addEventListener("click", function() {
            // Prompt user for username and event ID
            const username = prompt("Enter your username:");
            if (username !== null) {
                const eventIdInput = prompt("Enter the event ID:", eventId);
                if (eventIdInput !== null) {
                    // Perform further actions with username and event ID
                   
                }
            }
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

            const joinlist = createButton("Join Waitlist",event.eventID)
            //joinlist.textContent = ; // Set the button text
           // joinlist.setAttribute("type", "button"); // Set button type

            const leaveList = createButton("Leave Waitlist",event.eventID)
            //leaveList.textContent = ; // Set the button text
           // leaveList.setAttribute("type", "button"); // Set button type

            const joinEvent = createButton( "Join Event" ,event.eventID);
            //joinEvent.textContent = ; // Set the button text
           // joinEvent.setAttribute("type", "button"); // Set button type

            const leaveEvent = createButton("Leave Event", event.eventID);
           // leaveEvent.textContent = "Leave Event"; // Set the button text
            //leaveEvent.setAttribute("type", "button"); // Set button type

            const inviteUser = document.createElement("button");
            inviteUser.textContent = "Invite User"; // Set the button text
            //inviteUser.setAttribute("type", "button"); // Set button type
            
            const emailInput = document.createElement("input");
    emailInput.setAttribute("type", "text");
    emailInput.setAttribute("placeholder", "Enter email")

            // Append the buttons to the eventElement
            eventElement.appendChild(joinlist);
            eventElement.appendChild(leaveList);
            eventElement.appendChild(joinEvent);
            eventElement.appendChild(leaveEvent);
            eventElement.appendChild(inviteUser);
            eventElement.appendChild(emailInput);

            eventContainer.appendChild(eventElement);
            

            const divider = document.createElement('hr');
            eventElement.appendChild(divider);

            joinlist.style.display = "inline-block";
        leaveList.style.display = "inline-block";
        joinEvent.style.display = "inline-block";
        leaveEvent.style.display = "inline-block";
        inviteUser.style.display = "inline-block"

        joinlist.style.marginRight = "10px"; // Add some spacing between buttons if needed
        leaveList.style.marginRight = "10px";
        joinEvent.style.marginRight = "10px";
        leaveEvent.style.marginRight = "10px";
        inviteUser.style.marginRight = "10px"; // Add some spacing between buttons if needed
            });
           // eventsContainer.style.display = 'block'; // Show the events container
            
            
        }

    });
