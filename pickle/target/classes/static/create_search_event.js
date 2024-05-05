// Include jQuery
document.write('<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>');

// Include Bootstrap JS
document.write('<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>');

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

// Create Event button functionality
document.getElementById("createEvent").addEventListener("click", function () {
    var modal = document.getElementById("createEventModal");
    modal.style.display = "block";
});

// Close the modal when the user clicks on the close button
document.getElementsByClassName("close")[0].addEventListener("click", function () {
    closeModal();
});

// Function to close the modal and reset the form and filter fields
function closeModal() {
    var modal = document.getElementById("createEventModal");
    modal.style.display = "none";
    resetForm();
    resetFilterFields();
}

// Function to reset the filter fields
function resetFilterFields() {
    var eventFiltersContainer = document.getElementById("eventFiltersContainer");
    eventFiltersContainer.innerHTML = ''; // Clear all filter fields
    // Add back the initial event filter input field
    var newEventFilter = document.createElement("div");
    newEventFilter.classList.add("eventFilter");
    newEventFilter.innerHTML = '<label>Filter:</label> <input type="text" name="eventFilter"><br>';
    eventFiltersContainer.appendChild(newEventFilter);
}

// Function to reset the form
function resetForm() {
    document.getElementById("eventForm").reset();
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

    // Send event creation request to the backend
    fetch("/createEvent", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(eventData),
    })
        .then((response) => response.json())
        .then((data) => {
            // Handle success response if needed
            console.log("Event created successfully:", data);
            var modal = document.getElementById("createEventModal");
            modal.style.display = "none";
        })
        .catch((error) => {
            console.error("Error:", error);
            // Handle error response if needed
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
    closeSearchModal();

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