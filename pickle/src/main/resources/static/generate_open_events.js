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

    if (data.length === 0) {
        eventContainer.innerHTML = `
            <div class="events-empty">
                <div class="events-empty-icon">🎾</div>
                <h3 class="events-empty-title">No Open Events</h3>
                <p class="events-empty-subtitle">There are no open events available at this time.</p>
            </div>
        `;
        scrollToEventsSection();
        return;
    }

    data.forEach(event => {
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
            </div>
        `;
        
        eventContainer.appendChild(eventElement); // Append eventElement to eventContainer
    });
    scrollToEventsSection();
})
.catch((error) => {
    console.error("Error:", error);
});
});