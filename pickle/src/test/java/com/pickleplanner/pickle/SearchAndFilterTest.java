// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;

// import java.util.List;

// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;

// import com.pickleplanner.pickle.Event.Event;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class SearchAndFilterTest {

// @Autowired
// private Event eventService;

// @Test
// public void testSearchEventsByLocation() {
// String location = "New York";
// List<Event> events = eventService.getLocation();

// // Verify that the list of events is not null and contains events
// assertNotNull(events);
// assertEquals(2, events.size()); // Assuming there are 2 events with the given
// location
// }

// @Test
// public void testSearchEventsByKeyword() {
// String keyword = "birthday";
// List<Event> events = eventService.searchEventsByKeyword(keyword);

// // Verify that the list of events is not null and contains events
// assertNotNull(events);
// assertEquals(3, events.size()); // Assuming there are 3 events with the given
// keyword
// }

// @Test
// public void testFilterEventsByDate() {
// String date = "2024-05-10";
// List<Event> events = eventService.filterEventsByDate(date);

// // Verify that the list of events is not null and contains events
// assertNotNull(events);
// assertEquals(1, events.size()); // Assuming there is 1 event on the given
// date
// }

// // Additional test methods for other search and filter criteria can be added
// // here
// }
