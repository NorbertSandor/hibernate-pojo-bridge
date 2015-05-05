[Gilead](http://noon.gilead.free.fr/gilead/) is widely used for achieving similar goals as this library, so you may ask "why another library"?

I have been working on an enterprise-class, complex application, using Gilead for entity transformation. We found that:

  * Gilead is too complex for our needs, we needed only Hibernate support with a minimal number of dependencies (no beanlib, no jboss-serialization).
  * sometimes - in case of very complex objects graphs - merging and cloning was not performed properly, causing hard-to-find bugs.
  * We developed a stateless server application, in which case Gilead stores the [proxy informations](http://noon.gilead.free.fr/javadoc/net/sf/gilead/pojo/java5/LightEntity.html) in the entities. Sometimes very huge amount of information is stored here!

So I decided to implement the features we needed:

  * Simple implementation for merging and cloning object graphs, and leaving Hibernate to do the hard work.
  * Supporting only Hibernate, therefore making the implementation even more simple.
  * Not storing serialized proxy informations in the objects, instead storing the information in the object graph itself.

This means that this library is not a replacement for Gilead, it is an alternative only if you use Hibernate.