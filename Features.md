# Features #

## Supported types ##

  * Immutable types
    * Boolean
    * Byte
    * Character
    * Double
    * Float
    * Integer
    * Long
    * Short
    * String
    * BigDecimal
    * BigInteger
  * java.util.Date
  * persistent Set, List, Map - lazy and eager as well
  * arrays

## Lazy entity references ##

Lazy entity references are supported as well, but only for classes implementing [HibernateProxyPojoSupport](http://www.erinors.com/static/developer/project/hibernate-pojo-bridge/apidocs/com/erinors/hpb/client/api/HibernateProxyPojoSupport.html).
This means if you have a class which may be loaded as a proxy object (for example a lazy many-to-one association references the given entity), then it must implement the HibernateProxyPojoSupport interface, otherwise a runtime exception is throws when transforming the object.

# Currently unsupported features #

  * Property-level lazy fetching for simple properties
  * Some special collection types (sorted sets/maps, bag, idbag, etc.)