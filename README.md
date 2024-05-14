### Package build
```shell
mvn clean package
```

### SonarQube analyze command
```shell
mvn clean package sonar:sonar -Dsonar.token=b078a66e38350417c09ff84dd9a64d912c9269c8
```

### Jacoco command
```shell
mvn clean verify
```

### Test repository implement module
```shell
mvn clean verify -pl hicha-repository-impl
```