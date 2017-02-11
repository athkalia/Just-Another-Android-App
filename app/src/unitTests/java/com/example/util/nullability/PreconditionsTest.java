package com.example.util.nullability;

import com.example.util.PreconfiguredRobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

@RunWith(PreconfiguredRobolectricTestRunner.class)
@SuppressWarnings("PMD.AvoidCatchingNPE")
public class PreconditionsTest {

    @Test
    public void check_argument_negative() {
        // Act
        try {
            Preconditions.checkArgument(false);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e).hasMessage("Argument failed check.");
        }
    }

    @Test
    public void check_argument_positive() {

        Preconditions.checkArgument(true);
    }

    @Test
    public void check_argument_with_message_negative() {
        // Act
        try {
            Preconditions.checkArgument(false, "error message");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e).hasMessage("error message");
        }
    }

    @Test
    public void check_argument_with_message_positive() {

        Preconditions.checkArgument(true, "error message");
    }

    @Test
    public void check_argument_with_message_and_arguments_positive() {
        // Act
        try {
            Preconditions.checkArgument(false, "error message %s %s", "test", "lala");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e).hasMessage("error message test lala");
        }
    }

    @Test
    public void check_argument_with_message_and_arguments_negative() {

        Preconditions.checkArgument(true, "error message %s %s", "test", "lala");
    }

    @Test
    public void check_nullity() {
        // Act
        try {
            Preconditions.checkNotNull(null);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (NullPointerException e) {
            // Assert
            assertThat(e).hasMessage("Null check failed.");
        }
    }

    @Test
    public void check_non_nullity() {

        Preconditions.checkNotNull(new Object());
    }

    @Test
    public void check_nullity_with_message() {
        // Act
        try {
            Preconditions.checkNotNull(null, "error message");
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (NullPointerException e) {
            // Assert
            assertThat(e).hasMessage("error message");
        }
    }

    @Test
    public void check_non_nullity_with_message() {

        Preconditions.checkNotNull(new Object(), "error message");
    }

    @Test
    public void check_nullity_with_message_and_arguments() {
        // Act
        try {
            Preconditions.checkNotNull(null, "error message %s %s", "test", "lala");
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (NullPointerException e) {
            // Assert
            assertThat(e).hasMessage("error message test lala");
        }
    }

    @Test
    public void check_non_nullity_with_message_and_arguments() {

        Preconditions.checkNotNull(new Object(), "error message %s %s", "test", "lala");
    }

    @Test
    public void format_test() {
        // Arrange
        String expectedResult = "lala";

        // Act
        String actulResult = Preconditions.format("%s", "lala");

        // Assert
        assertThat(actulResult).isEqualTo(expectedResult);
    }

    @Test
    public void format_test_2() {
        // Arrange
        String expectedResult = "lalayolo";

        // Act
        String actulResult = Preconditions.format("%s%s", "lala", "yolo");

        // Assert
        assertThat(actulResult).isEqualTo(expectedResult);
    }

    @Test
    public void format_test_3() {
        // Arrange
        String expectedResult = "lalayolo%s";

        // Act
        String actulResult = Preconditions.format("%s%s%s", "lala", "yolo");

        // Assert
        assertThat(actulResult).isEqualTo(expectedResult);
    }

    @Test
    public void format_test_4() {
        // Arrange
        String expectedResult = "lala [yolo]";

        // Act
        String actulResult = Preconditions.format("%s", "lala", "yolo");

        // Assert
        assertThat(actulResult).isEqualTo(expectedResult);
    }

    @Test
    public void format_test_5() {
        // Arrange
        String expectedResult = "null [lala, yolo]";

        // Act
        String actulResult = Preconditions.format(null, "lala", "yolo");

        // Assert
        assertThat(actulResult).isEqualTo(expectedResult);
    }

}
