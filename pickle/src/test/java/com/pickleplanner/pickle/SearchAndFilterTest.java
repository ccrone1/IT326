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

// @autowired
// private Event eventService;

// @Test
// public void testSearchEventsByLocation() {
//     String location = "New York";
//     List<Event> events = eventService.searchEventsByLocation(location);

//     assertNotNull(events);
//     // Update the expected size based on your actual data
//     assertEquals(2, events.size());
// }

// @Test
// public void testSearchEventsByKeyword() {
//     String keyword = "birthday";
//     List<Event> events = eventService.searchEventsByKeyword(keyword);

//     assertNotNull(events);
//     // Update the expected size based on your actual data
//     assertEquals(3, events.size());
// }

// @Test
// public void testFilterEventsByDate() {
//     String date = "2024-05-10";
//     List<Event> events = eventService.filterEventsByDate(date);

//     assertNotNull(events);
//     // Update the expected size based on your actu
//     assertEquals(1, events.size());
// }
