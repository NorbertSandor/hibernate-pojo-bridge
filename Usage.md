## Setup classpath ##

Hibernate-pojo-bridge should be on your classpath. If you use maven, add the following dependency to your pom.xml:

```
<dependencies>
    ...
    <dependency>
        <groupId>com.erinors</groupId>
        <artifactId>hibernate-pojo-bridge</artifactId>
        <version>1.0.5</version>
    </dependency>
</dependencies>
```

Because the library is currently not uploaded to the central maven repository, you should add the following Maven repository as well: http://hibernate-pojo-bridge.googlecode.com/svn/maven-repository/

If you do not use a Maven repository manager then add the following directly to your pom.xml:

```
<repositories>
    ...
    <repository>
        <id>hpb-release-repo</id>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <url>http://hibernate-pojo-bridge.googlecode.com/svn/maven-repository</url>
    </repository>
</repositories>
```

## Instantiate services ##

Hibernate-pojo-bridge is built on the Spring framework. You should add the followings to your Spring configuration files:

```
<import resource="classpath:/com/erinors/hpb/server/spring/hpb.spring.xml" />

<bean id="persistentObjectManager" class="com.erinors.hpb.server.impl.PersistentObjectManagerImpl">
    <property name="entityManagerFactory" ref="entityManagerFactory" />
</bean>
```

(A future release may probably include support for non-Spring applications as well. If this is important for you, please contact me.)

## Transforming your domain objects ##

A typical usage of the library is the following:

```
public class PersonServiceImpl implements PersonService
{
    @PersistenceContext
    private EntityManager entityManager;

    ...

    @Override
    @Transactional
    public Person savePerson(Person person)
    {
        Person merged = entityManager.merge(persistentObjectManager.merge(person));
        entityManager.flush();
        
        return persistentObjectManager.clone(merged);
    }
}
```

To summarize:
  * you should call PersistentObjectManager.merge() for client->server transformation
  * you should call PersistentObjectManager.clone() for server->client transformation
  * before cloning optionally call EntityManager.flush() (forcing Hibernate to increase the version number of the entities)

## GWT setup ##

Your GWT module should inherit the library's functionalities by adding the following line to your gwt.xml:

```
<inherits name="com.erinors.hpb.HibernatePojoBridge" />
```

On the client side no extra work is required, just use your domain objects as you wish, all transformation is performed on the server side.

## Instrumenting domain objects ##

By default you don't have to do anything with your domain classes.
The only exception is when the domain objects are loaded as proxies, for example in case of lazy loading. All lazily referenced domain classes should implement the [HibernateProxyPojoSupport](http://www.erinors.com/static/developer/project/hibernate-pojo-bridge/apidocs/com/erinors/hpb/client/api/HibernateProxyPojoSupport.html) interface. For a default implementation, see [HibernateProxyPojoSupportImpl](http://www.erinors.com/static/developer/project/hibernate-pojo-bridge/apidocs/com/erinors/hpb/client/api/HibernateProxyPojoSupportImpl.html).