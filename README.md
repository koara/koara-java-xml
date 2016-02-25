[![Koara](http://www.koara.io/logo.png)](http://www.koara.io)

[![Build Status](https://img.shields.io/travis/koara/koara-java-xml.svg)](https://travis-ci.org/koara/koara-java-xml)
[![Coverage Status](https://img.shields.io/coveralls/koara/koara-java-xml.svg)](https://coveralls.io/github/koara/koara-java-xml?branch=master)
[![Latest Version](https://img.shields.io/maven-central/v/io.koara/koara.svg?label=Maven Central)](http://search.maven.org/#search%7Cga%7C1%7Ckoara-xml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/koara/koara-java-xml/blob/master/LICENSE)

# koara-java-xml
[Koara](http://www.koara.io) is a modular lightweight markup language. This project is for parsing Koara to Xml.

## Getting Started
- Download [JAR file](http://repo1.maven.org/maven2/io/koara/koara-xml/0.9.1/koara-xml-0.9.1.jar)
- Gradle

  ```groovy
  dependencies {
	compile "io.koara:koara-xml:0.9.1"
  }
  ```
  
- Maven

  ```xml
  <dependency>
    <groupId>io.koara</groupId>
    <artifactId>koara-xml</artifactId>
    <version>0.9.1</version>
  </dependency>
  ```

## Usage
```java
package io.koara;

import io.koara.ast.Document;
import io.koara.xml.XmlRenderer;
import static io.koara.Module.*;

public class Demo {

	public static void main(String[] args) {
		
		Parser parser = new Parser();
		
		// Enable which modules to parse (all are parsed by default)
		parser.setModules(PARAGRAPHS, HEADINGS, LISTS, LINKS, IMAGES, FORMATTING, BLOCKQUOTES, CODE);
		
		// Parse string or file and generate AST
		Document document = parser.parse("Hello World!"); 
		
		// Render AST as Xml
		XmlRenderer renderer = new XmlRenderer();
		document.accept(renderer);
		
		System.out.println(renderer.getOutput());
	}
	
}
```