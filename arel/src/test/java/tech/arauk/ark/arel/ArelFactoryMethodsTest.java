package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOn;

public class ArelFactoryMethodsTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateJoin() {
        Object join = ArelFactoryMethods.createJoin("one", "two");

        assertSame(ArelNodeInnerJoin.class, join.getClass());
        assertEquals("two", ((ArelNodeInnerJoin) join).right);
    }

    public void testCreateOn() {
        Object on = ArelFactoryMethods.createOn("one");

        assertSame(ArelNodeOn.class, on.getClass());
        assertEquals("one", ((ArelNodeOn) on).expr);
    }

    public void testCreateTrue() {
        Object aTrue = ArelFactoryMethods.createTrue();

        assertSame(ArelNodeTrue.class, aTrue.getClass());
    }

    public void testCreateFalse() {
        Object aFalse = ArelFactoryMethods.createFalse();

        assertSame(ArelNodeFalse.class, aFalse.getClass());
    }

    public void testLower() {
        Object lower = ArelFactoryMethods.lower("one");

        assertSame(ArelNodeNamedFunction.class, lower.getClass());
        assertEquals("LOWER", ((ArelNodeNamedFunction) lower).name);
        assertSame(ArelNodeQuoted.class, ((Object[]) ((ArelNodeNamedFunction) lower).expressions)[0].getClass());
        assertEquals("one", ((ArelNodeQuoted) ((Object[]) ((ArelNodeNamedFunction) lower).expressions)[0]).expr);
    }
}
