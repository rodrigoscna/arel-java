package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.attributes.*;
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

    public void testBooleanColumnTypes() {
        ArelAttribute.Type column = new ArelAttribute.Type() {
            @Override
            public String type() {
                return "boolean";
            }
        };

        assertSame(ArelAttributeBoolean.class, Attributes.forColumn(column));
    }

    public void testDecimalColumnTypes() {
        ArelAttribute.Type column = new ArelAttribute.Type() {
            @Override
            public String type() {
                return "decimal";
            }
        };

        assertSame(ArelAttributeDecimal.class, Attributes.forColumn(column));
    }

    public void testFloatColumnTypes() {
        ArelAttribute.Type column = new ArelAttribute.Type() {
            @Override
            public String type() {
                return "float";
            }
        };

        assertSame(ArelAttributeFloat.class, Attributes.forColumn(column));
    }

    public void testIntegerColumnTypes() {
        ArelAttribute.Type column = new ArelAttribute.Type() {
            @Override
            public String type() {
                return "integer";
            }
        };

        assertSame(ArelAttributeInteger.class, Attributes.forColumn(column));
    }

    public void testStringColumnTypes() {
        String[] columnTypes = new String[]{"binary", "string", "text"};
        for (final String columnType : columnTypes) {
            ArelAttribute.Type column = new ArelAttribute.Type() {
                @Override
                public String type() {
                    return columnType;
                }
            };

            assertSame(ArelAttributeString.class, Attributes.forColumn(column));
        }
    }

    public void testTimeColumnTypes() {
        String[] columnTypes = new String[]{"date", "datetime", "time", "timestamp"};
        for (final String columnType : columnTypes) {
            ArelAttribute.Type column = new ArelAttribute.Type() {
                @Override
                public String type() {
                    return columnType;
                }
            };

            assertSame(ArelAttributeTime.class, Attributes.forColumn(column));
        }
    }

    public void testUnknownColumnTypes() {
        ArelAttribute.Type column = new ArelAttribute.Type() {
            @Override
            public String type() {
                return "crazy";
            }
        };

        assertSame(ArelAttributeUndefined.class, Attributes.forColumn(column));
    }
}
