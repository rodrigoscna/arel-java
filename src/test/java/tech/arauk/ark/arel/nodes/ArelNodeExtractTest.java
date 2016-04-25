package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.Objects;

public class ArelNodeExtractTest {
    public static abstract class Base extends TestCase {
        static {
            ArelTable.engine = new FakeRecord.Base();
        }

        @Override
        protected void setUp() throws Exception {
            super.setUp();
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }
    }

    public static class As extends Base {
        public void testAs() {
            ArelTable table = new ArelTable("users");
            ArelAttribute attribute = table.get("timestamp");
            ArelNode extract = attribute.extract("date").as("foo");

            assertEquals("EXTRACT(DATE FROM \"users\".\"timestamp\") AS foo", extract.toSql());
        }

        public void testAsMutation() {
            ArelTable table = new ArelTable("users");
            ArelAttribute attribute = table.get("timestamp");

            ArelNodeExtract before = attribute.extract("date");
            ArelNodeExtract extract = attribute.extract("date");
            extract.as("foo");

            assertEquals(before, extract);
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelTable("users").get("attr").extract("foo"), new ArelTable("users").get("attr").extract("foo"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelTable("users").get("attr").extract("foo"), new ArelTable("users").get("attr").extract("bar")));
        }
    }

    public static class Extract extends Base {
        public void testExtract() {
            ArelTable table = new ArelTable("users");
            ArelAttribute attribute = table.get("timestamp");
            ArelNode extract = attribute.extract("date");

            assertEquals("EXTRACT(DATE FROM \"users\".\"timestamp\")", extract.toSql());
        }
    }
}
