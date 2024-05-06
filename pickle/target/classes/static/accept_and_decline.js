document.addEventListener('DOMContentLoaded', function () {
    function getParameterByName(name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    var eventDetailsButton = document.getElementById('generateEventDetailsButton');

    var action = getParameterByName('action');
    if (action === 'decline') {

        if (eventDetailsButton) {
            eventDetailsButton.click();
        }
        alert("Sorry, the event didn't work out. Hopefully another one will fit your needs.");

    }

    if (action === 'accept') {
        var eventId = getParameterByName('eventId');

        if (eventDetailsButton) {
            eventDetailsButton.click();
        }
        alert("We're glad to hear that you want to join an event! Join the event with the ID: " + eventId);

    }
})
