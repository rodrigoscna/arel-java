package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.attributes.*;
import tech.arauk.ark.arel.nodes.ArelNodeNamedFunction;

import java.util.Arrays;
import java.util.Objects;

public class ArelAttributeTest {
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

    public static class Attribute extends Base {
        public void testLower() {
            ArelTable relation = new ArelTable("users");

            ArelAttribute attribute = relation.get("foo");

            Object namedFunction = attribute.lower();

            assertSame(ArelNodeNamedFunction.class, namedFunction.getClass());
            assertEquals("LOWER", ((ArelNodeNamedFunction) namedFunction).name);
            assertTrue(Arrays.equals(new Object[]{attribute}, (Object[]) ((ArelNodeNamedFunction) namedFunction).expressions));
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelAttribute("foo", "bar"), new ArelAttribute("foo", "bar"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelAttribute("foo", "bar"), new ArelAttribute("foo", "baz")));
        }
    }

    public static class ForColumn extends Base {
        public void testForColumnWithBooleanType() {
            ArelAttribute.Type column = new ArelAttribute.Type() {
                @Override
                public String type() {
                    return "boolean";
                }
            };

            assertSame(ArelAttributeBoolean.class, Attributes.forColumn(column));
        }

        public void testForColumnWithDecimalType() {
            ArelAttribute.Type column = new ArelAttribute.Type() {
                @Override
                public String type() {
                    return "decimal";
                }
            };

            assertSame(ArelAttributeDecimal.class, Attributes.forColumn(column));
        }

        public void testForColumnWithFloatType() {
            ArelAttribute.Type column = new ArelAttribute.Type() {
                @Override
                public String type() {
                    return "float";
                }
            };

            assertSame(ArelAttributeFloat.class, Attributes.forColumn(column));
        }

        public void testForColumnWithIntegerType() {
            ArelAttribute.Type column = new ArelAttribute.Type() {
                @Override
                public String type() {
                    return "integer";
                }
            };

            assertSame(ArelAttributeInteger.class, Attributes.forColumn(column));
        }

        public void testForColumnWithStringType() {
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

        public void testForColumnWithTimeType() {
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

        public void testForColumnWithUnknownType() {
            ArelAttribute.Type column = new ArelAttribute.Type() {
                @Override
                public String type() {
                    return "crazy";
                }
            };

            assertSame(ArelAttributeUndefined.class, Attributes.forColumn(column));
        }
    }
}
