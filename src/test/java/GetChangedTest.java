import junit.framework.TestCase;
import ru.redych.data.URLRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.usecases.GetChangedUseCase;

public class GetChangedTest extends TestCase {
    public void testAverageChanges() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/today.txt");
        GetChangedUseCase useCase = new GetChangedUseCase(yesterday, today);
        String expected = "\n- ya.ru";
        assertEquals(expected, useCase.getChanged());
    }
    public void testAllChanges() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/changed_table.txt");
        GetChangedUseCase useCase = new GetChangedUseCase(yesterday, today);
        String expected = """
                
                - google.com
                - ya.ru
                - example.com""";
        assertEquals(expected, useCase.getChanged());
    }
    public void testNoneChanges() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/yesterday.txt");
        GetChangedUseCase useCase = new GetChangedUseCase(yesterday, today);
        String expected = "";
        assertEquals(expected, useCase.getChanged());
    }
}
