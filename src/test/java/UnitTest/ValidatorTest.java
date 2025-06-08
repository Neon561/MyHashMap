package UnitTest;

import UserStorage.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ValidatorTest {

    private final Validator validator = new Validator();

    @Mock
    private Scanner mockScanner;

    @Test
    void getValidFullName_ShouldReturnValidName() {
        when(mockScanner.nextLine())
                .thenReturn("  Иванов Иван Иванович  ");


        String result = validator.getValidFullName(mockScanner);


        assertEquals("Иванов Иван Иванович", result);
    }

    @Test
    void getValidEmail_ShouldReturnValidEmail() {

        when(mockScanner.nextLine())
                .thenReturn("  test.example@mail.com  ");


        String result = validator.getValidEmail(mockScanner);


        assertEquals("test.example@mail.com", result);
    }

    @Test
    void getValidAge_ShouldReturnValidAge() {

        when(mockScanner.nextLine())
                .thenReturn("  25  ");

        int result = validator.getValidAge(mockScanner);


        assertEquals(25, result);
    }

    @Test
    void getValidAge_ShouldRetryWhenInvalidInput() {

        when(mockScanner.nextLine())
                .thenReturn("abc")
                .thenReturn("300")
                .thenReturn("30");


        int result = validator.getValidAge(mockScanner);


        assertEquals(30, result);
        verify(mockScanner, times(3)).nextLine();
    }

    @Test
    void getValidId_ShouldReturnPositiveLong() {
        when(mockScanner.nextLine())
                .thenReturn("  123  ");

        long result = validator.getValidId(mockScanner);

        assertEquals(123L, result);
    }

    @Test
    void getValidId_ShouldRejectNegativeNumbers() {

        when(mockScanner.nextLine())
                .thenReturn("-5")
                .thenReturn("10");

        long result = validator.getValidId(mockScanner);

        assertEquals(10L, result);
        verify(mockScanner, times(2)).nextLine();
    }
}