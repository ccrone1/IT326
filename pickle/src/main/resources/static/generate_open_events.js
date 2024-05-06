document.querySelector(".btn-generate-open").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Send search request to the backend
    fetch("/generate_open", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({}),
    })
    .then((response) => response.json())
.then((data) => {
    const eventContainer = document.getElementById('openResults');
    eventContainer.innerHTML = ''; // Clear existing content before displaying new events

    data.forEach(event => {
        const eventElement = document.createElement('div');
        eventElement.innerHTML = `
            <h2>${event.date} ${event.time}</h2>
            <p>EventID: ${event.eventID}</p>
            <p>Location: ${event.location.location}</p>
            <p>Owner: ${event.owner.fname} ${event.owner.lname}</p>
            <p>Availability: ${event.availability}</p>
            <p>Participants: ${event.participants.length}</p>
        `;

        
        eventContainer.appendChild(eventElement); // Append eventElement to eventContainer
    });
})
.catch((error) => {
    console.error("Error:", error);
});
});