import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Bracket {

    @InjectMocks
    private BracketController bracketController;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EventService eventService;

    @Test
    public void testGenerateBracketForEvent() throws IOException {
        // Prepare test data
        String eventID = "123456";
        List<Event> events = new ArrayList<>();
        Event mockEvent = new Event();
        mockEvent.setEventID(eventID);
        mockEvent.setParticipants(Arrays.asList(new User("user1"), new User("user2")));
        events.add(mockEvent);
        File file = mock(File.class);

        // Mock ObjectMapper behavior
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(events);

        // Mock event service behavior
        when(eventService.getEventByID(eventID)).thenReturn(mockEvent);

        // Mock bracket generation
        Bracket bracket = new Bracket();
        bracket.setBracketId("789");
        bracket.setParticipants(Arrays.asList("user1", "user2"));
        when(eventService.generateBracket(anyList(), eq(mockEvent))).thenReturn(bracket);

        // Perform the request
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("eventID", eventID);
        String response = bracketController.generateBracketForEvent(requestBody);

        // Verify the response
        assertNotNull(response);
        assertTrue(response.contains("user1 vs user2")); // Check if the generated bracket is included in the response
    }
}
