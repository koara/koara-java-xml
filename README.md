[![Koara](http://www.koara.io/logo.png)](http://www.koara.io)

[![Build Status](https://img.shields.io/travis/koara/koara-java-xml.svg)](https://travis-ci.org/koara/koara-java-xml)
[![Coverage Status](https://img.shields.io/coveralls/koara/koara-java-xml.svg)](https://coveralls.io/github/koara/koara-java-xml?branch=master)
[![Latest Version](https://img.shields.io/maven-central/v/io.koara/koara-xml.svg?label=Maven Central)](http://search.maven.org/#search%7Cga%7C1%7Ckoara-xml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/koara/koara-java-xml/blob/master/LICENSE)

# Koara-java-xml
[Koara](http://www.koara.io) is a modular lightweight markup language. This project can render the koara AST to Xml in Java.  
The AST is created by the [core koara parser](https://github.com/koara/koara-java).

## Getting started
- Download [JAR file](http://repo1.maven.org/maven2/io/koara/koara/0.14.0/koara-xml-0.14.0.jar)
- Gradle

  ```groovy
  dependencies {
	compile "io.koara:koara-xml:0.14.0"
  }
  ```
  
- Maven

  ```xml
  <dependency>
    <groupId>io.koara</groupId>
    <artifactId>koara-xml</artifactId>
    <version>0.14.0</version>
  </dependency>
  ```

## Usage
```java
package demo;

import io.koara.Parser;
import io.koara.ast.Document;
import io.koara.xml.XmlRenderer;

public class App {

	public static void main(String[] args) {
		Parser parser = new Parser();
		Document result = parser.parse("Hello World!");
		XmlRenderer renderer = new XmlRenderer();
		result.accept(renderer);
		System.out.println(renderer.getOutput());
	}
	
}
```

## Configuration
You can configure the Renderer:
-  **parser.setHardWrap(boolean hardWrap)**  
   Default: `false`
   
   Specify if newlines should be hard-wrapped (return-based linebreaks) by default.
   
-  **renderer.setDeclarationTag(String declarationTag)**  
   Default:	`null`
   
   Add an XML Declaration Tag add the top of the generated output.  