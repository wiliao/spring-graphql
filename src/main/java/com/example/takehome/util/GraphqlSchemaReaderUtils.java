package com.example.takehome.util;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: William Liao
 * @Email: liaotoca@yahoo.com
 */
@Slf4j
public final class GraphqlSchemaReaderUtils {

    private GraphqlSchemaReaderUtils() {

    }
  public static String getSchemaFromFileName(final String filename) throws IOException {
	log.info("Loading schema file: {}", filename);  
    return new String(
        GraphqlSchemaReaderUtils.class.getClassLoader().getResourceAsStream("graphql/" + filename + ".graphql").readAllBytes());

  }

}
