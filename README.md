[![Master Build Status](https://img.shields.io/travis/IMSGlobal/caliper-java.svg?label=master)](https://travis-ci.org/IMSGlobal/caliper-java)
[![Develop Build Status](https://img.shields.io/travis/IMSGlobal/caliper-java/develop.svg?label=develop)](https://travis-ci.org/IMSGlobal/caliper-java/develop)

# IMS Global Learning Consortium, Inc.

# caliper-java
The [Caliper Analytics® Specification](https://www.imsglobal.org/caliper/v1p1/caliper-spec-v1p1) 
provides a structured approach to describing, collecting and exchanging learning activity data at 
scale. Caliper also defines an application programming interface (the Sensor API™) for marshalling 
and transmitting event data from instrumented applications to target endpoints for storage, 
analysis and use.  

*caliper-java* is a reference implementation of the Sensor API™ written in Javas.

## Branches
* __master__: stable, deployable branch that stores the official release history.  
* __develop__: unstable development branch.  Current work that targets a future release is merged 
to this branch.

## Tags
*caliper-java* releases are tagged and versioned MAJOR.MINOR.PATCH\[-label\] (e.g., 1.1.1). 
Pre-release tags are identified with an extensions label (e.g., "1.2.0-RC01").  The tags are stored 
in this repository.

## Contributing
We welcome the posting of issues by non IMS Global Learning Consortium members (e.g., feature 
requests, bug reports, questions, etc.) but we *do not* accept contributions in the form of pull 
requests from non-members. See [CONTRIBUTING.md](./CONTRIBUTING.md) for more 
information.

## Getting Started
Fork the IMS Global *caliper-java* project to your Github account and then clone your copy to your 
local development machine.  

### Prerequisites
*caliper-java* uses Apache [Maven](https://maven.apache.org/) 3.x to manage the build process, 
documentation and reporting.

### Building
First, clone the *caliper-common-fixtures* project to your development machine and add symbolic 
links: 

```
ln -s /Path/to/caliper-common-fixtures/v1p0 src/test/resources/fixtures/v1p0
ln -s /Path/to/caliper-common-fixtures/v1p1 src/test/resources/fixtures/v1p1
ln -s /Path/to/caliper-common-fixtures/v1p2 src/test/resources/fixtures/v1p2
``` 

Then from your local *caliper-java* directory run:

```
./mvnw clean install
```

You can also build a jar with all the dependencies compiled by invoking the `uber-jar` build 
profile:

```
mvn clean -P uber-jar install
```

This will create a jar in: `target/caliper-java-{version}.jar`

### Dependency Management
You can specify *caliper-java* as a project or module dependency in the appropriate `pom.xml` file:

```
<dependency>
    <groupId>org.imsglobal.caliper</groupId>
    <artifactId>caliper-java</artifactId>
    <version>1.2.0</version>
</dependency>
```  

## License
This project is licensed under the terms of the GNU Lesser General Public License (LGPL), version 3. 
See the [LICENSE](./LICENSE) file for details. For additional information on licensing options for 
IMS members, please see the [NOTICE](./NOTICE.md) file.