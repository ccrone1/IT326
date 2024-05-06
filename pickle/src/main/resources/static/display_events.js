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

    function joinListCalled(eventId){

        console.log("Joining list");
          // var userEmail = document.getElementById('userEmail').value;
   
          ///reached here
         
                   // Prompt user for username 
                   const username = prompt("Enter your username:");
                   if (username !== null) {
                       const eventIdInput = eventId;//prompt("Enter the event ID:", eventId);
                       console.log("got username: "+username+ "   ID"+eventIdInput);
   
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
           }}
     
function leaveListCalled(eventId){

    console.log("Leaving list");
      // var userEmail = document.getElementById('userEmail').value;

      ///reached here
     
               // Prompt user for username 
               const username = prompt("Enter your username:");
               if (username !== null) {
                   const eventIdInput = eventId;//prompt("Enter the event ID:", eventId);
                   console.log("got username: "+username+ "   ID"+eventIdInput);

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
       }}

         
function joinEventCalled(eventId){
    console.log("Joining Event");
   
      // var userEmail = document.getElementById('userEmail').value;

      ///reached here
     
               // Prompt user for username 
               const username = prompt("Enter your username:");
               if (username !== null) {
                   const eventIdInput = eventId;//prompt("Enter the event ID:", eventId);
                   console.log("got username: "+username+ " .  ID"+eventIdInput);

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
       }}

function leaveEventCalled(eventId){

    console.log("Leaving event");
      // var userEmail = document.getElementById('userEmail').value;

      ///reached here
     
               // Prompt user for username 
               const username = prompt("Enter your username:");
               if (username !== null) {
                   const eventIdInput =eventId//prompt("Enter the event ID:", eventId);
                   console.log("got username: "+username+ " .    ID"+eventIdInput);

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
       }}



    
    function createJoinListButton(text, eventId) {
       

        const button = document.createElement("button");
        button.textContent = text;
        button.setAttribute("type", "button");
        button.style.display = "inline-block";
        button.style.marginRight = "10px";

        function handleClick() {
            console.log("The id used is : "+eventId);
            const target=eventId;


            joinListCalled(target); // Pass the eventId to the joinListCalled function
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
            console.log("The id used is : "+eventId);
            const target=eventId;

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
            console.log("The id used is : "+eventId);
            const target=eventId;


            joinEventCalled(target); // Pass the eventId to the joinListCalled function
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
            console.log("The id used is : "+eventId);
            const target=eventId;


            leaveEventCalled(target); // Pass the eventId to the joinListCalled function
        }
    
        button.addEventListener('click', handleClick);
    
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

            const joinlist = createJoinListButton("Join Waitlist",event.eventID)
            joinlist.setAttribute("id","joinWaitlistButton")
            

            const leaveList = createLeaveListButton("Leave Waitlist",event.eventID)
            leaveList.setAttribute("id","leaveWaitlistButton")
         

            const joinEvent = createJoinEventButton( "Join Event" ,event.eventID);
            joinEvent.setAttribute("id","joinEventButton")
           


            const leaveEvent = createLeaveEventButton("Leave Event", event.eventID);
            leaveEvent.setAttribute("id","leaveEventButton")
          

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





      

 
         
            
