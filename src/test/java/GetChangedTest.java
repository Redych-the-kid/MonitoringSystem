import junit.framework.TestCase;
import ru.redych.data.URLRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.usecases.GetChangedUsecase;

public class GetChangedTest extends TestCase {
    public void testAverageChanges() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/today.txt");
        GetChangedUsecase usecase = new GetChangedUsecase(yesterday, today);
        String expected = "ya.ru";
        assertEquals(expected, usecase.getChanged());
    }
    public void testAllChanges() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/changed_table.txt");
        GetChangedUsecase usecase = new GetChangedUsecase(yesterday, today);
        String expected = "google.com, ya.ru, example.com";
        assertEquals(expected, usecase.getChanged());
    }
    public void testNoneChanges() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/yesterday.txt");
        GetChangedUsecase usecase = new GetChangedUsecase(yesterday, today);
        String expected = "";
        assertEquals(expected, usecase.getChanged());
    }
}
