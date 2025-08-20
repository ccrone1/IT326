document.addEventListener('DOMContentLoaded', function () {
    const generateDetailsButton = document.getElementById('generateEventDetailsButton');
    const eventsContainer = document.getElementById('events');

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

    generateDetailsButton.addEventListener('click', function () {
        fetch('/events', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({})
        })
            .then(response => response.json())
            .then(data => {
                displayEvents(data);
                // Always show the events container and scroll to it
                eventsContainer.style.display = "block";
                scrollToEventsSection();






            })
            .catch(error => console.error('Error:', error));
    });

    function joinListCalled(eventId) {

        console.log("Joining list");
        const username = prompt("Enter your username:");
        if (username !== null) {
            const eventIdInput = eventId;
            console.log("got username: " + username + "   ID" + eventIdInput);

            fetch('/joinWaitlist', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ eventId: eventId, username: username })
            })
                .then(response => {
                    if (response.ok) {
                        alert("Joined waitlist successfully.");
                    } else {
                        alert("Failed to join waitlist.");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("An error occurred while joining waitlist.");
                });

        }
        else {
            alert("Please enter a valid UserName.");
        }
    }

    function leaveListCalled(eventId) {

        console.log("Leaving list");
        const username = prompt("Enter your username:");
        if (username !== null) {
            const eventIdInput = eventId;
            console.log("got username: " + username + "   ID" + eventIdInput);

            fetch('/leaveWaitlist', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ eventId: eventId, username: username })
            })
                .then(response => {
                    if (response.ok) {
                        alert("Left waitlist successfully.");
                    } else {
                        alert("Failed to leave waitlist.");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("An error occurred while leaving waitlist.");
                });

        }
        else {
            alert("Please enter a valid UserName.");
        }
    }


    function joinEventCalled(eventId) {
        console.log("Joining Event");
        const username = prompt("Enter your username:");
        if (username !== null) {
            const eventIdInput = eventId;
            console.log("got username: " + username + " .  ID" + eventIdInput);

            fetch('/joinEvent', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ eventID: eventId, username: username })
            })
                .then(response => {
                    if (response.ok) {
                        alert("Joined Event successfully.");
                    } else {
                        alert("Failed to join Event.");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("An error occurred while joining Event.");
                });

        }
        else {
            alert("Please enter a valid UserName.");
        }
    }

    function leaveEventCalled(eventId) {

        console.log("Leaving event");
        const username = prompt("Enter your username:");
        if (username !== null) {
            const eventIdInput = eventId;
            console.log("got username: " + username + " .    ID" + eventIdInput);

            fetch('/leaveEvent', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ eventID: eventId, username: username })
            })
                .then(response => {
                    if (response.ok) {
                        alert("Left event successfully.");
                    } else {
                        alert("Failed to leave event.");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("An error occurred while leaving event.");
                });

        }
        else {
            alert("Please enter a valid UserName.");
        }
    }

    function kickEventCalled(eventId) {

        console.log("Kicking user");
        const username = prompt("Enter username:");
        if (username !== null) {
            const eventIdInput = eventId;
            console.log("got username: " + username + " .    ID" + eventIdInput);

            fetch('/leaveEvent', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ eventID: eventId, username: username })
            })
                .then(response => {
                    if (response.ok) {
                        alert("Left event successfully.");
                    } else {
                        alert("Failed to leave event.");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("An error occurred while leaving event.");
                });

        }
        else {
            alert("Please enter a valid UserName.");
        }
    }




    function createJoinListButton(text, eventId) {


        const button = document.createElement("button");
        button.textContent = text;
        button.setAttribute("type", "button");
        button.style.display = "inline-block";
        button.style.marginRight = "10px";

        function handleClick() {
            console.log("The id used is : " + eventId);
            const target = eventId;


            joinListCalled(target);
        }

        button.addEventListener('click', handleClick);

        return button;
    }

    function createLeaveListButton(text, eventId) {
        const button = document.createElement("button");
        button.textContent = text;
        button.setAttribute("type", "button");
        button.style.display = "inline-block";
        button.style.marginRight = "10px";
        function handleClick() {
            console.log("The id used is : " + eventId);
            const target = eventId;

            leaveListCalled(target);

        }

        button.addEventListener('click', handleClick);


        return button;
    }
    function createJoinEventButton(text, eventId) {
        const button = document.createElement("button");
        button.textContent = text;
        button.setAttribute("type", "button");
        button.style.display = "inline-block";
        button.style.marginRight = "10px";
        function handleClick() {
            console.log("The id used is : " + eventId);
            const target = eventId;


            joinEventCalled(target);
        }

        button.addEventListener('click', handleClick);

        return button;
    }
    function createLeaveEventButton(text, eventId) {
        const button = document.createElement("button");
        button.textContent = text;
        button.setAttribute("type", "button");
        button.style.display = "inline-block";
        button.style.marginRight = "10px";

        function handleClick() {
            console.log("The id used is : " + eventId);
            const target = eventId;


            leaveEventCalled(target);
        }

        button.addEventListener('click', handleClick);

        return button;
    }
    function createKickButton(text, eventId) {


        const button = document.createElement("button");
        button.textContent = text;
        button.setAttribute("type", "button");
        button.style.display = "inline-block";
        button.style.marginRight = "10px";

        function handleClick() {
            console.log("The id used is : " + eventId);
            const target = eventId;


            joinListCalled(target);
        }

        button.addEventListener('click', handleClick);

        return button;
    }

    function displayEvents(events) {
        const eventContainer = document.getElementById('events');
        eventContainer.innerHTML = '';

        if (events.length === 0) {
            eventContainer.innerHTML = `
                <div class="events-empty">
                    <div class="events-empty-icon">📅</div>
                    <h3 class="events-empty-title">No Events Found</h3>
                    <p class="events-empty-subtitle">There are no events to display at this time.</p>
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
                        <!-- Action buttons will be appended here -->
                    </div>
                </div>
            `;

            const joinlist = createJoinListButton("Join Waitlist", event.eventID)
            joinlist.setAttribute("id", "joinWaitlistButton")


            const leaveList = createLeaveListButton("Leave Waitlist", event.eventID)
            leaveList.setAttribute("id", "leaveWaitlistButton")


            const joinEvent = createJoinEventButton("Join Event", event.eventID);
            joinEvent.setAttribute("id", "joinEventButton")



            const leaveEvent = createLeaveEventButton("Leave Event", event.eventID);
            leaveEvent.setAttribute("id", "leaveEventButton")

            const kickEvent=createKickButton("Kick from Event",event.eventID);
            kickEvent.setAttribute("id","kickButton");


            const inviteUser = document.createElement("button");
            inviteUser.textContent = "Invite User";
            inviteUser.setAttribute("type", "button");
            inviteUser.setAttribute("id", "inviteUserButton");

            const usernameInput = document.createElement("input");
            usernameInput.setAttribute("type", "text");
            usernameInput.setAttribute("placeholder", "Enter your username");
            usernameInput.setAttribute("id", "usernameInput");


            const emailInput = document.createElement("input");
            emailInput.setAttribute("type", "text");
            emailInput.setAttribute("placeholder", "Enter their email");
            emailInput.setAttribute("id", "userEmailInput");

            inviteUser.addEventListener('click', function () {
                var userEmail = emailInput.value;
                var eventID = event.eventID;
                var username = usernameInput.value;
                if (userEmail.trim() !== '') {
                    const button = document.getElementById('inviteUserButton');
                    if (button.innerHTML !== 'Cancel Invitation' && confirm("Are you sure you want to invite " + userEmail + "?")) {
                        sendInvitation(userEmail, eventID, username);
                        updateButtonForCancellation(userEmail);
                    } else if (button.innerHTML === 'Cancel Invitation' && confirm("Are you sure you want to cancel the invitation to " + userEmail + "?")) {
                    } else {
                    }
                } else {
                    alert("Please enter a valid email address.");
                }
            });

            function sendInvitation(userEmail, eventID, username) {
                fetch('/inviteUser', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        userEmail: userEmail,
                        eventID: eventID,
                        username: username
                    })
                })
                    .then(response => response.json())
                    .then(data => {
                        alert(data.message);
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }

            function cancelInvitation(userEmail, eventID, username) {
                fetch('/cancelInvite', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        userEmail: userEmail,
                        eventID: eventID,
                        username: username
                    })
                })
                    .then(response => response.json())
                    .then(data => {
                        alert(data.message);
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }

            function updateButtonForCancellation(userEmail) {
                const button = document.getElementById('inviteUserButton');
                button.innerHTML = 'Cancel Invitation';
                button.removeEventListener('click', sendInvitation);
                button.addEventListener('click', () => cancelInvitation(userEmail));
            }

            // Get the event actions container
            const actionsContainer = eventElement.querySelector('.event-actions');
            
            // Style the buttons with professional appearance
            [joinlist, leaveList, joinEvent, leaveEvent, inviteUser].forEach(button => {
                button.className = 'event-action-btn';
            });
            
            // Style the input fields
            [usernameInput, emailInput].forEach(input => {
                input.className = 'event-input';
            });
            
            // Create a grid layout for the actions
            const buttonGrid = document.createElement('div');
            buttonGrid.className = 'event-button-grid';
            
            const inviteGrid = document.createElement('div');
            inviteGrid.className = 'event-invite-section';
            
            // Add buttons to grid
            buttonGrid.appendChild(joinlist);
            buttonGrid.appendChild(leaveList);
            buttonGrid.appendChild(joinEvent);
            buttonGrid.appendChild(leaveEvent);
            
            // Add invite section
            const inviteTitle = document.createElement('h4');
            inviteTitle.textContent = 'Invite User';
            inviteTitle.className = 'invite-title';
            
            inviteGrid.appendChild(inviteTitle);
            inviteGrid.appendChild(usernameInput);
            inviteGrid.appendChild(emailInput);
            inviteGrid.appendChild(inviteUser);
            
            actionsContainer.appendChild(buttonGrid);
            actionsContainer.appendChild(inviteGrid);
            eventContainer.appendChild(eventElement);
        });


    }

});










