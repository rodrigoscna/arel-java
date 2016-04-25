package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArelNodeInsertStatementTest {
    public static abstract class Base extends TestCase {
        @Override
        protected void setUp() throws Exception {
            super.setUp();
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            List<Object> columns1 = new ArrayList<>();
            columns1.add("a");
            columns1.add("b");
            columns1.add("c");

            List<Object> values1 = new ArrayList<>();
            values1.add("x");
            values1.add("y");
            values1.add("z");

            ArelNodeInsertStatement insertStatement1 = new ArelNodeInsertStatement();
            insertStatement1.columns(columns1);
            insertStatement1.values(values1);

            List<Object> columns2 = new ArrayList<>();
            columns2.add("a");
            columns2.add("b");
            columns2.add("c");

            List<Object> values2 = new ArrayList<>();
            values2.add("x");
            values2.add("y");
            values2.add("z");

            ArelNodeInsertStatement insertStatement2 = new ArelNodeInsertStatement();
            insertStatement2.columns(columns2);
            insertStatement2.values(values2);

            assertEquals(insertStatement1, insertStatement2);
        }

        public void testEqualityWithDifferentValues() {
            List<Object> columns1 = new ArrayList<>();
            columns1.add("a");
            columns1.add("b");
            columns1.add("c");

            List<Object> values1 = new ArrayList<>();
            values1.add("x");
            values1.add("y");
            values1.add("z");

            ArelNodeInsertStatement insertStatement1 = new ArelNodeInsertStatement();
            insertStatement1.columns(columns1);
            insertStatement1.values(values1);

            List<Object> columns2 = new ArrayList<>();
            columns2.add("a");
            columns2.add("b");
            columns2.add("c");

            List<Object> values2 = new ArrayList<>();
            values2.add("1");
            values2.add("2");
            values2.add("3");

            ArelNodeInsertStatement insertStatement2 = new ArelNodeInsertStatement();
            insertStatement2.columns(columns2);
            insertStatement2.values(values2);

            assertFalse(Objects.equals(insertStatement1, insertStatement2));
        }
    }
}
