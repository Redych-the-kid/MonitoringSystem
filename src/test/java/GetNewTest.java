import junit.framework.TestCase;
import ru.redych.data.URLRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.usecases.GetNewUseCase;

public class GetNewTest extends TestCase {
    public void testAverageNew() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/today.txt");
        GetNewUseCase useCase = new GetNewUseCase(yesterday, today);
        String expected = "\n- mail.ru";
        assertEquals(expected, useCase.getNew());
    }
    public void testAllNew() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/new_table.txt");
        GetNewUseCase useCase = new GetNewUseCase(yesterday, today);
        String expected = """
                             
                             - yandex.ru
                             - gorillaz.com
                             - mail.ru""";
        assertEquals(expected, useCase.getNew());
    }
    public void testNoneNew() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/yesterday.txt");
        GetNewUseCase useCase = new GetNewUseCase(yesterday, today);
        String expected = "";
        assertEquals(expected, useCase.getNew());
    }
}
