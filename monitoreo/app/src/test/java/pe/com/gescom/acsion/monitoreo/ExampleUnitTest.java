package pe.com.gescom.acsion.monitoreo;

import org.junit.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void text_is_expected() {
        String text = "test1";
        assertThat(text, is("test1"));
    }

    // Demonstrates the return of multiple values
    @Test
    public void testMoreThanOneReturnValue()  {
        Iterator i= mock(Iterator.class);
        when(i.next()).thenReturn("Mockito").thenReturn("rocks");
        String result=i.next()+" "+i.next();
        //assert
        assertEquals("Mockito rocks", result);
    }

    // this test demonstrates how to return values based on the input
    @Test
    public void testReturnValueDependentOnMethodParameter()  {
        Comparable c= mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        //assert
        assertEquals(2,c.compareTo("Mockito"));
    }

    // Demonstrates the return of multiple values
    @Test
    public void testMultipleValues() {
        List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        Iterator<Integer> iterator = Mockito.mock(Iterator.class);
        when(iterator.next()).thenReturn(0).thenReturn(1);
        assertEquals(list.get(0), list.get(iterator.next()));
        assertEquals(list.get(1), list.get(iterator.next()));
    }

}