## Provider Core

The Provider can be used to generate generic methods only through this core module.
For examples, refer to the test code in this project.

For example, one of the test examples.

### Interface definition

```java
/**
 * @author liuzh
 */
public interface SelectAllMapper<T> {

  @Lang(ProviderLanguageDriver.class)
  //Providers are used here to allow support for implementations from a variety of strategies.
  @SelectProvider(type = Providers.class, method = "dynamicSQL")
  List<T> selectAll();

}
```

Note the two annotations on this method.

### General method implementation

The following classes need to be in the same package as the above interface.

```java
import org.apache.ibatis.builder.annotation.ProviderContext;
import Providers;

import javax.persistence.Table;
import java.util.Map;

public class SelectAllMapperProvider {

  public String selectAll(Map<String, Object> params, ProviderContext context) {
    Class entityClass = Providers.getReturnType(context);
    Table table = (Table) entityClass.getAnnotation(Table.class);
    String script = "<select id=\"selectAll\" resultType=\"" + entityClass.getCanonicalName()
        + "\"> select * from " + table.name() + "</select>";
    return script;
  }

}
```

### define interface usage

```java
/**
 * @author liuzh
 */
public interface CountryMapper extends SelectAllMapper<Country> {
  
}
```

### Test Methods

```java
@Test
public void testSelect() {
  SqlSession session = getSqlSession();
  try {
    CountryMapper mapper = session.getMapper(CountryMapper.class);
    List<Country> countries = mapper.selectAll();
    Assert.assertEquals(183, countries.size());
  } finally {
    session.rollback();
  }
}
```