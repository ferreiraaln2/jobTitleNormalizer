import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.normalizer.com.models.JobTitleModel;
import org.normalizer.com.service.JobTitleNormalizerService;

import java.util.Arrays;
import java.util.List;

public class JobTitleNormalizerServiceTest {
    private JobTitleNormalizerService normalizer;

    @BeforeEach
    public void setUp() {
        List<JobTitleModel> jobTitleModels = Arrays.asList(
                new JobTitleModel("Architect"),
                new JobTitleModel("Software engineer"),
                new JobTitleModel("Quantity surveyor"),
                new JobTitleModel("Accountant")
        );
        normalizer = new JobTitleNormalizerService(jobTitleModels);
    }

    @Test
    public void testNormalize() {
        Assertions.assertEquals("Software engineer", normalizer.normalize("Java engineer"));
        Assertions.assertEquals("Software engineer", normalizer.normalize("C# engineer"));
        Assertions.assertEquals("Accountant", normalizer.normalize("Accountant"));
        Assertions.assertEquals("Accountant", normalizer.normalize("Chief Accountant"));
    }

    @Test
    public void testNormalizeCaseInsensitive() {
        Assertions.assertEquals("Software engineer", normalizer.normalize("java ENGINEER"));
        Assertions.assertEquals("Software engineer", normalizer.normalize("C# ENGINEER"));
        Assertions.assertEquals("Accountant", normalizer.normalize("accountant"));
        Assertions.assertEquals("Accountant", normalizer.normalize("chief ACCOUNTANT"));
    }

    @Test
    public void testNormalizeWithSpecialCharacters() {
        Assertions.assertEquals("Software engineer", normalizer.normalize("Java-engineer"));
        Assertions.assertEquals("Software engineer", normalizer.normalize("C# engineer!!"));
        Assertions.assertEquals("Accountant", normalizer.normalize("Chief Accountant."));
    }

    @Test
    public void testNormalizeNoMatch() {
        Assertions.assertNull(normalizer.normalize("xkzçkçzçk"));
    }

    @Test
    public void testNormalizeNullInput() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            normalizer.normalize(null);
        });
        Assertions.assertEquals("Input title cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testNormalizeEmptyInput() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            normalizer.normalize("");
        });
        Assertions.assertEquals("Input title cannot be null or empty", exception.getMessage());

        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            normalizer.normalize("   ");
        });
        Assertions.assertEquals("Input title cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testNormalizedJobTitlesListCannotBeNull() {
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> {
            new JobTitleNormalizerService(null);
        });
        Assertions.assertEquals("Normalized job titles list cannot be null", exception.getMessage());
    }
}
