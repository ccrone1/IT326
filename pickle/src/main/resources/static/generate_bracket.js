document.querySelector("#generateBracketBtn").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent default form submission

    // Get the value of the input field (eventID)
    const eventID = document.getElementById('eventIDBracket').value;

    // Send request to the backend to generate the bracket
    fetch("/generate_bracket", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ eventID: eventID }), // Include the eventID in the request body
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.text(); // Assuming the response is plain text representing the generated bracket
    })
    .then((data) => {
        // Display the generated bracket in a specific HTML element
        const bracketContainer = document.getElementById('bracketContainer');
        bracketContainer.textContent = data; // Assuming data contains the generated bracket string
    })
    .catch((error) => {
        console.error("Error:", error);
    });
});
