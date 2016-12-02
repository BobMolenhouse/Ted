import static org.junit.Assert.*;

import org.junit.Test;

public class EditorTest {

	// test b command.
	@Test 
    public void testInsertBefore() throws Exception {
        Editor ed = new Editor();
        ed.processCommand("b Line 1");
        ed.processCommand("b Line 0");
        assertEquals("Line 1", ed.getLine(1));
        assertEquals("Line 0", ed.getLine(0));
    }
	
	//test b command
	@Test 
    public void testInsertBefore2() throws Exception {
        Editor ed = new Editor();
        ed.processCommand("i Line 0");
        ed.processCommand("i Line 1");
        ed.processCommand("b Line 0.5");
        assertEquals("Line 0.5", ed.getLine(1));
    }
	
	// test i command
	@Test
	public void testInsertAfter() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("b Line 0.5");
		ed.processCommand("i Line 0.75");
		assertEquals("Line 0.75", ed.getLine(2));
	}

	// test r command
	@Test
	public void testRemove() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("r");
		ed.processCommand("iLine 2");
		assertEquals("Line 2", ed.getLine(1));
	}

	// test r command
	@Test
	public void testRemove2() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("b Line 1.5");
		ed.processCommand("r");
		assertEquals("Line 2", ed.getLine(2));
	}

	// test c command
	@Test
	public void testClear() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("b Line 1.5");
		ed.processCommand("s 1234");
		ed.processCommand("c");
		ed.processCommand("i clear");
		assertEquals("clear", ed.getLine(0));
	}

	// test c command
	@Test
	public void testClear2() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("b Line 0");
		ed.processCommand("e Line 1");
		ed.processCommand("e Line 2");
		ed.processCommand("u");
		ed.processCommand("r");
		ed.processCommand("c");
		ed.processCommand("i clear");
		assertEquals("clear", ed.getLine(0));
	}

	// test s and l commands
	@Test
	public void testSave() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("b    Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("e Pizza Pizza");
		ed.processCommand("s pizza");
		ed.processCommand("c");
		ed.processCommand("l pizza");
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Pizza Pizza", ed.getLine(2));

	}

	// test s and l commands
	@Test
	public void testSave2() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("b             Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("ehowdy doody folks");
		ed.processCommand("sHowdy");
		ed.processCommand("c");
		ed.processCommand("i hello");
		ed.processCommand("i there");
		ed.processCommand("l Howdy");
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("howdy doody folks", ed.getLine(2));

	}

	// test s and l commands
	@Test
	public void testInsertEnd() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("b Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i howdy doody folks");
		ed.processCommand("iHowdy");
		ed.processCommand("u");
		ed.processCommand("e hello");
		assertEquals("Howdy", ed.getLine(3));
		assertEquals("hello", ed.getLine(4));

	}
	
	// test m # command
		@Test
		public void testMoveDown() throws Exception {
			Editor ed = new Editor();
			ed.processCommand("b Line 0");
			ed.processCommand("b Line 1");
			ed.processCommand("b Line 2");
			ed.processCommand("b start");
			ed.processCommand("m 2");
			assertEquals("Line 1", ed.getCurrentLine());
		}
		
		// test m # command
		@Test
		public void testMoveDown2() throws Exception {
			Editor ed = new Editor();
			ed.processCommand("bLine 8");
			ed.processCommand("b Line 7");
			ed.processCommand("b Line 6");
			ed.processCommand("b       Line 5");
			ed.processCommand("b Line 4");
			ed.processCommand("b Line 3");
			ed.processCommand("b    Line 2");
			ed.processCommand("b Line 1");
			ed.processCommand("b start");
			ed.processCommand("m6");
			assertEquals("Line 6", ed.getCurrentLine());
		}
		
	// test u # command
	@Test
	public void testMoveUp() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("iLine 3");
		ed.processCommand("i Line 4");
		ed.processCommand("i Line 5");
		ed.processCommand("i Line 6");
		ed.processCommand("i    Line 7");
		ed.processCommand("i Line 8");
		ed.processCommand("i start");
		ed.processCommand("u5");
		assertEquals("Line 4", ed.getCurrentLine());
	}
	
	// test u # command
		@Test
		public void testMoveUp2() throws Exception {
			Editor ed = new Editor();
			ed.processCommand("i Line 1");
			ed.processCommand("i Line 2");
			ed.processCommand("i Line 3");
			ed.processCommand("i start");
			ed.processCommand("u         2");
			assertEquals("Line 2", ed.getCurrentLine());
		}
		
	// test r # command
	@Test
	public void testRemoveMulti() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("u 3");
		ed.processCommand("r 2");
		assertEquals("Line 3", ed.getCurrentLine());
		assertEquals("Line 4", ed.getLine(1));
		}
	
	// test r # command
	@Test
	public void testRemoveMulti2() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 33333333333333333333333333333333" +
		"3333333333333333333333333333333333333333333333333333333333" +
				"333333333333333333333333333333333333333333333333333");
		ed.processCommand("i Line 4");
		ed.processCommand("i Line 5");
		ed.processCommand("i Line 6");
		ed.processCommand("i Line 7");
		ed.processCommand("i Line 8");
		ed.processCommand("u 5");
		ed.processCommand("r6");
		assertEquals("Line 2", ed.getCurrentLine());
		assertEquals("Line 1", ed.getLine(0));
	}
	
	// test cut and pas command
		@Test
		public void testCutPasAll() throws Exception {
			Editor ed = new Editor();
			ed.processCommand("i Line 0");
			ed.processCommand("i Line 1");
			ed.processCommand("i Line 2");
			ed.processCommand("i Line 3");
			ed.processCommand("i Line 4");
			ed.processCommand("i Line 5");
			ed.processCommand("i Line 6");
			ed.processCommand("i Line 7");
			ed.processCommand("cut 0 7");
			ed.processCommand("i cut");
			assertEquals("cut", ed.getLine(0));
			ed.processCommand("pas");
			assertEquals("Line 0", ed.getLine(0));
			assertEquals("Line 4", ed.getLine(4));
		}
		
	// test cut and pas command
	@Test
	public void testCutPasMiddle() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("i Line 5");
		ed.processCommand("i Line 6");
		ed.processCommand("i Line 7");
		ed.processCommand("cut 3 5");
		assertEquals("Line 6", ed.getLine(3));
		ed.processCommand("u");
		ed.processCommand("pas");
		assertEquals("Line 3", ed.getLine(2));
		assertEquals("Line 5", ed.getLine(4));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(5));

	}

	// test cut and pas command
	@Test
	public void testCutPasEnd() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("i Line 5");
		ed.processCommand("i Line 6");
		ed.processCommand("i Line 7");
		ed.processCommand("cut 3 5");
		assertEquals("Line 6", ed.getLine(3));
		ed.processCommand("m");
		ed.processCommand("pas");
		assertEquals("Line 7", ed.getLine(7));
		assertEquals("Line 5", ed.getLine(6));
		assertEquals("Line 3", ed.getLine(4));
		assertEquals("Line 2", ed.getLine(2));
	}
	
	// test cut and pas command with multiple pastes.
	@Test
	public void testCutMultiPaste() throws Exception {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("i Line 5");
		ed.processCommand("i Line 6");
		ed.processCommand("i Line 7");
		ed.processCommand("cut 1 2");
		ed.processCommand("pas");
		ed.processCommand("pas");
		ed.processCommand("pas");
		assertEquals("Line 3", ed.getCurrentLine());
		assertEquals("Line 5", ed.getLine(9));
		assertEquals("Line 7", ed.getLine(11));
		assertEquals("Line 1", ed.getLine(5));
		assertEquals("Line 1", ed.getLine(3));
	}
		
	//test i command.
    @Test 
    public void testProcessCommandInsert() throws Exception {
        Editor ed = new Editor();
        ed.processCommand("i Line 0");
        ed.processCommand("i Line 1");
        assertEquals("Line 1", ed.getCurrentLine());
        assertEquals("Line 0", ed.getLine(0));
    }
    
    //test remove.
    @Test 
    public void testProcessCommandRemove() throws Exception {
       Editor ed = new Editor();
       ed.processCommand("i Line 0");
       ed.processCommand("i Line 1");
       ed.processCommand("i Line 2");
       ed.processCommand("i Line 3");
       ed.processCommand("u 2");
       ed.processCommand("r 2");
       assertEquals("Line 3", ed.getCurrentLine());
    }
}
