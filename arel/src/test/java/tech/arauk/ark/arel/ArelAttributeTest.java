package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.attributes.ArelAttributeUndefined;
import tech.arauk.ark.arel.nodes.ArelNodeNamedFunction;

import java.util.Arrays;

public class ArelAttributeTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLower() {
        ArelTable relation = new ArelTable("users");

        ArelAttribute attribute = relation.get("foo");

        Object namedFunction = attribute.lower();

        assertSame(ArelNodeNamedFunction.class, namedFunction.getClass());
        assertEquals("LOWER", ((ArelNodeNamedFunction) namedFunction).name);
        assertTrue(Arrays.equals(new Object[]{attribute}, (Object[]) ((ArelNodeNamedFunction) namedFunction).expressions));
    }
}
