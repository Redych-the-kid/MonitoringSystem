import junit.framework.TestCase;
import ru.redych.data.URLRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.usecases.GetDisappearedUseCase;

public class GetDisappearedTest extends TestCase {
    public void testAverageDisappeared() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/today.txt");
        GetDisappearedUseCase useCase = new GetDisappearedUseCase(yesterday, today);
        String expected = "\n- example.com";
        assertEquals(expected, useCase.getDisappeared());
    }
    public void testAllDisappeared() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/disappeared_table.txt");
        GetDisappearedUseCase useCase = new GetDisappearedUseCase(yesterday, today);
        String expected = """
                             
                             - google.com
                             - ya.ru
                             - example.com""";
        assertEquals(expected, useCase.getDisappeared());
    }
    public void testNoneDisappeared() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/yesterday.txt");
        GetDisappearedUseCase useCase = new GetDisappearedUseCase(yesterday, today);
        String expected = "";
        assertEquals(expected, useCase.getDisappeared());
    }
}
