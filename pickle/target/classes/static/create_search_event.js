// Include jQuery
document.write('<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>');

// Include Bootstrap JS
document.write('<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>');

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

// Function to handle adding a new filter
document.getElementById("addFilter").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default button behavior

    // Create a new filter input field
    var filterContainer = document.getElementById("filterContainer");
    var newFilter = document.createElement("div");
    newFilter.classList.add("filter");
    newFilter.innerHTML = '<label>Filter:</label> <input type="text" name="filter"><br>';

    // Append the new filter input field to the container
    filterContainer.appendChild(newFilter);
});

// Function to handle adding a new event filter
document.getElementById("addEventFilter").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default button behavior

    // Create a new event filter input field
    var eventFiltersContainer = document.getElementById("eventFiltersContainer");
    var newEventFilter = document.createElement("div");
    newEventFilter.classList.add("eventFilter");
    newEventFilter.innerHTML = '<label>Filter:</label> <input type="text" name="eventFilter"><br>';

    // Append the new event filter input field to the container
    eventFiltersContainer.appendChild(newEventFilter);
});

// Modal management - wait for DOM to be ready
document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM loaded, setting up modal listeners");
    
    const createEventButton = document.getElementById("createEvent");
    const createEventModal = document.getElementById("createEventModal");
    const closeButton = createEventModal?.querySelector(".close");
    
    console.log("Create Event Button:", createEventButton);
    console.log("Create Event Modal:", createEventModal);
    console.log("Close Button:", closeButton);
    
    // Create Event button functionality
    if (createEventButton) {
        createEventButton.addEventListener("click", function () {
            console.log("Create event button clicked");
            if (createEventModal) {
                createEventModal.style.display = "block";
                console.log("Modal should be visible now");
            }
        });
    }
    
    // Close button functionality
    if (closeButton) {
        console.log("Setting up close button listener");
        closeButton.addEventListener("click", function (e) {
            console.log("Close button clicked!");
            e.preventDefault();
            e.stopPropagation();
            closeModal();
        });
    }
    
    // Alternative close button setup - try multiple selectors
    const allCloseButtons = document.querySelectorAll('#createEventModal .close');
    console.log("Found close buttons:", allCloseButtons.length);
    allCloseButtons.forEach((btn, index) => {
        console.log(`Setting up close button ${index}:`, btn);
        btn.addEventListener("click", function(e) {
            console.log(`Alternative close button ${index} clicked`);
            e.preventDefault();
            e.stopPropagation();
            
            // Try direct modal hiding
            const modal = document.getElementById("createEventModal");
            console.log("Direct modal access:", modal);
            if (modal) {
                console.log("Current modal display style:", modal.style.display);
                modal.style.display = "none";
                modal.style.visibility = "hidden";
                modal.classList.add("modal-hidden");
                console.log("After setting display none:", modal.style.display);
            }
            
            closeModal();
        });
    });
    
    // Close modal when clicking outside of it
    if (createEventModal) {
        window.addEventListener("click", function(event) {
            if (event.target === createEventModal) {
                console.log("Clicked outside modal, closing");
                closeModal();
            }
        });
    }
    
    // Close modal with Escape key
    document.addEventListener("keydown", function(event) {
        if (event.key === "Escape" && createEventModal && createEventModal.style.display === "block") {
            console.log("Escape key pressed, closing modal");
            closeModal();
        }
    });
});

// Function to close the modal and reset the form and filter fields
function closeModal() {
    console.log("closeModal function called");
    var modal = document.getElementById("createEventModal");
    console.log("Modal element:", modal);
    if (modal) {
        console.log("Hiding modal");
        modal.style.display = "none";
        resetForm();
        resetFilterFields();
        console.log("Modal should be hidden now");
    } else {
        console.log("Modal element not found!");
    }
}

// Function to reset the filter fields
function resetFilterFields() {
    var eventFiltersContainer = document.getElementById("eventFiltersContainer");
    if (eventFiltersContainer) {
        eventFiltersContainer.innerHTML = `
            <div class="eventFilter">
                <label>Filter:</label>
                <input type="text" name="eventFilter" class="form-input" />
            </div>
        `;
    }
}

// Function to reset the form
function resetForm() {
    const form = document.getElementById("eventForm");
    if (form) {
        form.reset();
    }
}

function generateRandomId() {
    return parseInt(Math.floor(Math.random() * 100000));
}

// Function to handle event creation form submission
document.getElementById("eventForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get event input values
    var eventDate = document.getElementById("eventDate").value;
    var eventTime = document.getElementById("eventTime").value;
    var availability = document.getElementById("availability").value;
    var userName = document.getElementById("username").value;
    var location = document.getElementById("location").value;
    // Get event filters
    var eventFilters = [];
    document.querySelectorAll(".eventFilter input[name='eventFilter']").forEach(function (input) {
        eventFilters.push(input.value);
    });

    // Create event parameters object
    var eventData = {
        id: generateRandomId(),
        date: eventDate,
        time: eventTime,
        availability: availability,
        userName: userName,
        location: location,
        tags: eventFilters // Include event filters in the data
    };

    // Debug logging
    console.log("Event data being sent:", eventData);
    console.log("Username value:", '"' + userName + '"');
    console.log("Username length:", userName.length);

    // Send event creation request to the backend
    fetch("/createEvent", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(eventData),
    })
        .then((response) => response.text())
        .then((data) => {
            // Handle response - check if it's a success or error message
            if (data.includes("Username invalid") || data.includes("Failed")) {
                // Show error message to user
                alert("Error creating event: " + data);
                console.error("Event creation failed:", data);
            } else {
                // Handle success response
                console.log("Event created successfully:", data);
                alert("Event created successfully!");
                var modal = document.getElementById("createEventModal");
                modal.style.display = "none";
                // Reset the form
                resetForm();
                resetFilterFields();
            }
        })
        .catch((error) => {
            console.error("Network error:", error);
            alert("Network error occurred while creating event");
        });

    closeModal();
});

// Function to handle form submission
document.getElementById("searchForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get form input values for keyword and all filters
    var keyword = document.querySelector("input[name='keyword']").value;
    var filters = [];
    document.querySelectorAll(".filter input[name='filter']").forEach(function (input) {
        filters.push(input.value);
    });

    // Create search parameters object
    var searchParams = {
        keyword: keyword,
        filters: filters
    };

    // Send search request to the backend
    fetch("/search", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(searchParams),
    })
    .then((response) => response.json())
.then((data) => {
    const eventContainer = document.getElementById('searchResults');
    eventContainer.innerHTML = ''; // Clear existing content before displaying new events

    if (data.length === 0) {
        eventContainer.innerHTML = `
            <div class="events-empty">
                <div class="events-empty-icon">🔍</div>
                <h3 class="events-empty-title">No Results Found</h3>
                <p class="events-empty-subtitle">No events match your search criteria.</p>
            </div>
        `;
        closeSearchModal();
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
    closeSearchModal();
    scrollToEventsSection();

})
.catch((error) => {
    console.error("Error:", error);
});

});

function closeSearchModal() {
    var modal = document.getElementById("searchModal");
    modal.style.display = "none";
    resetForm();
    resetFilterFields();
}

// Function to reset the filter fields
function resetFilterFields() {
    var filterContainer = document.getElementById("filterContainer");
    filterContainer.innerHTML = ''; // Clear all filter fields
    // Add back the initial filter input field
    var newFilter = document.createElement("div");
    newFilter.classList.add("filter");
    newFilter.innerHTML = '<label for="keyword">Keyword:</label> <input type="text" name="keyword"><br>';
    filterContainer.appendChild(newFilter);
}

// Function to reset the form
function resetForm() {
    document.getElementById("searchForm").reset();
}


// JavaScript to open modal
$(document).ready(function(){
  $('#openModalBtn').click(function(){
    $('#searchModal').modal('show');
  });
});