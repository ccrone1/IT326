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
    var modal = document.getElementById("createEventModal");
    modal.style.display = "none";
});

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
});

// Function to handle form submission
document.getElementById("searchForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get form input values
    var filters = [];
    document.querySelectorAll(".filter input[name='filter']").forEach(function (input) {
        filters.push(input.value);
    });

    // Create search parameters object
    var searchParams = {
        filters: filters,
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
            // Display search results on the page
            document.getElementById("searchResults").innerHTML = JSON.stringify(data);
        })
        .catch((error) => {
            console.error("Error:", error);
        });
});
