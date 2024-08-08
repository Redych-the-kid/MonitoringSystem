import junit.framework.TestCase;
import ru.redych.data.URLRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.usecases.GetDisappearedUsecase;

public class GetDisappearedTest extends TestCase {
    public void testAverageDisappeared() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/today.txt");
        GetDisappearedUsecase usecase = new GetDisappearedUsecase(yesterday, today);
        String expected = "\n- example.com";
        assertEquals(expected, usecase.getDisappeared());
    }
    public void testAllDisappeared() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/disappeared_table.txt");
        GetDisappearedUsecase usecase = new GetDisappearedUsecase(yesterday, today);
        String expected = """
                             
                             - google.com
                             - ya.ru
                             - example.com""";
        assertEquals(expected, usecase.getDisappeared());
    }
    public void testNoneDisappeared() {
        IURLRepository yesterday = new URLRepository("src/test/resources/yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/yesterday.txt");
        GetDisappearedUsecase usecase = new GetDisappearedUsecase(yesterday, today);
        String expected = "";
        assertEquals(expected, usecase.getDisappeared());
    }
}
