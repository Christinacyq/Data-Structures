import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListTest2 {
    private ArrayList<String> stringList;
    @Before public void setUp() {
        stringList = new ArrayList<>();
    }
    @org.junit.Test
    public void addAtIndex() {
        stringList.addAtIndex(0,"string");
        assertEquals(1, stringList.size());
        assertEquals("string",stringList.get(0));
    }

    @org.junit.Test
    /**
     * Checks if you resize with addAtIndex.
     */
    public void addAtIndexSizeCheck() {
        for (int i = 0; i != 19; i++) {
            stringList.addAtIndex(i, "H");
        }
        assertEquals(19, stringList.size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void addAtIndexIllegalArgumentExceptionCheck() {
        stringList.addAtIndex(0,null);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void addAtIndexIndexOutOfBoundsException(){
        stringList.addAtIndex(876543,"Hi");
        stringList.addAtIndex(-876543, "don't work");
    }
    @Test
    public void addToFrontCheck() {
        stringList.addToFront("T");
        stringList.addToFront("H");
        assertEquals("H",stringList.get(0));
    }
    /**
     * Checks if you resize with addToFront.
     */
    @org.junit.Test
    public void addToFrontSizeCheck() {
        for (int i = 0; i != 19; i++) {
            stringList.addToFront("H");
        }
        assertEquals(19,stringList.size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void addToFrontIllegalArgumentExceptionCheck() {
        stringList.addToFront(null);
    }
    @Test
    public void addToBackTest() {
        stringList.addToBack("H");
        String[] temp = new String[9];
        temp[0] = "H";
        assertArrayEquals(temp, stringList.getBackingArray());
    }

    /**
     * Checks if you resize with addToBack.
     */
    @org.junit.Test
    public void addToBackSizeCheck() {
        for (int i = 0; i != 19; i++) {
            stringList.addToBack("H");
        }
        assertEquals(19,stringList.size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void addToBackIllegalArgumentExceptionCheck() {
        stringList.addToBack(null);
    }

    @org.junit.Test
    public void removeAtIndex() {
        stringList.addToFront("hi");
        stringList.addToBack("t");
        stringList.addAtIndex(1,"hello");
        stringList.addToBack("h");
        stringList.addToBack("F");
        stringList.removeAtIndex(1);
        String[] temp = new String[9];
        temp[0] = "hi";
        temp[1]= "t";
        temp[2] ="h";
        temp[3] = "F";
        temp[4] = null;
        assertArrayEquals(temp,stringList.getBackingArray());
    }
    @Test
    public void removeAtindex2() {
        String[] temp = new String[9];
        for (int i = 0; i != 9; i++) {
            stringList.addToBack("H");
            temp[i] = "H";
        }
        temp[8] = null;
        stringList.removeAtIndex(3);
        assertArrayEquals(temp, stringList.getBackingArray());

    }
    @org.junit.Test
    public void removeAtIndexSizeCheck() {
        for (int i = 0; i != 19; i++) {
            stringList.addToBack("H");
        }
        stringList.removeAtIndex(4);
        assertEquals(18, stringList.size());
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtIndexIndexOutOfBoundsExceptionCheck() {
        stringList.addToFront("h");
        stringList.removeAtIndex(1);
        stringList.removeAtIndex(2);
        stringList.removeAtIndex(-98765432);
    }
    @org.junit.Test
    public void removeFromFront() {
        stringList.addToBack("H");
        stringList.addToBack("T");
        stringList.addToBack("R");
        stringList.removeFromFront();
        String[] temp = new String[9];
        temp[0] = "T";
        temp[1] = "R";
        assertEquals("T",stringList.get(0));
        assertArrayEquals(temp, stringList.getBackingArray());
    }
    @Test
    public void removeFromFront2() {
        String[] temp = new String[9];
        for (int i = 0; i != 9; i++) {
            stringList.addToBack("H");
            temp[i] = "H";
        }
        temp[8] = null;
        stringList.removeFromFront();
        assertArrayEquals(temp, stringList.getBackingArray());
    }
    @Test(expected = NoSuchElementException.class)
    public void removeFromFrontNoSuchElementException() {
        stringList.removeFromFront();
    }

    @org.junit.Test
    public void removeFromBack() {
        stringList.addToBack("H");
        stringList.addToBack("T");
        stringList.addToBack("R");
        stringList.addAtIndex(0,"Y");
        stringList.removeFromBack();
        String[] temp = new String[9];
        temp[0] = "Y";
        temp[1] = "H";
        temp[2] = "T";
        temp[3] = null;
        assertArrayEquals(temp,stringList.getBackingArray());
    }
    @Test
    public void removeFromBack2() {
        String[] temp = new String[9];
        for (int i = 0; i != 9; i++) {
            stringList.addToBack("H");
            temp[i] = "H";
        }
        temp[8] = null;
        stringList.removeFromBack();
        assertArrayEquals(temp, stringList.getBackingArray());
    }
    @Test(expected = NoSuchElementException.class)
    public void removeFromBackNoSuchElementException() {
        stringList.removeFromBack();
    }

    @org.junit.Test
    public void get() {
        stringList.addToBack("H");
        stringList.addToBack("T");
        assertEquals("H",stringList.get(0));
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void getIndexOutOfBoundsExceptionCheck() {
        stringList.addToFront("T");
        stringList.get(987654);
        stringList.get(-8765432);
    }

    @org.junit.Test
    public void isEmpty() {
        stringList.addToBack("hi");
        assertFalse(stringList.isEmpty());
    }
    @org.junit.Test
    public void isEmpty2() {
        stringList.clear();
        assertTrue(stringList.isEmpty());
    }

    @org.junit.Test
    public void clear() {
        for (int i = 0; i != 20; i++) {
            if(i%2 == 0) {
                stringList.addToBack("Even");
            } else {
                stringList.addToBack("Odd");
            }
        }
        stringList.clear();
        String[] temp = new String[9];
        assertArrayEquals(temp, stringList.getBackingArray());
        assertEquals(0, stringList.size());
    }
}