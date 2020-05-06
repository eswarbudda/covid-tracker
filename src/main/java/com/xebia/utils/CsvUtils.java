package com.xebia.utils;

import org.springframework.stereotype.Component;


@Component
public class CsvUtils {
  /*private static final CsvMapper mapper = new CsvMapper();
  public <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
    CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
    ObjectReader reader = mapper.readerFor(clazz).with(schema);
    return reader.<T>readValues(stream).readAll();
  }*/
}
