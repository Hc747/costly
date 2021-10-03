## Micronaut 3.0.4-SNAPSHOT Documentation

- [User Guide](https://docs.micronaut.io/snapshot/guide/index.html)
- [API Reference](https://docs.micronaut.io/snapshot/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/snapshot/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Push To Docker Registry Workflow

Workflow file: [`.github/workflows/gradle.yml`](.github/workflows/gradle.yml)

### Workflow description
For pushes to the `master` branch, the workflow will:
1. Setup the build environment with respect to the selected java/graalvm version.
2. Login to docker registry based on provided configuration.
3. Build, tag and push Docker image with Micronaut application to the Docker container image.

### Dependencies on other GitHub Actions
- [Docker login](`https://github.com/docker/login-action`)(`docker/login`)
- [Setup GraalVM](`https://github.com/DeLaGuardo/setup-graalvm`)(`DeLaGuardo/setup-graalvm`)

### Setup
Add the following GitHub secrets:

| Name | Description |
| ---- | ----------- |
| DOCKER_USERNAME | Username for Docker registry authentication. |
| DOCKER_PASSWORD | Docker registry password. |
| DOCKER_REPOSITORY_PATH | Path to the docker image repository inside the registry, e.g. for the image `foo/bar/micronaut:0.1` it is `foo/bar`. |
| DOCKER_REGISTRY_URL | Docker registry url. |
#### Configuration examples
Specifics on how to configure public cloud docker registries like DockerHub, Google Container Registry (GCR), AWS Container Registry (ECR),
Oracle Cloud Infrastructure Registry (OCIR) and many more can be found in [docker/login-action](https://github.com/docker/login-action)
documentation.

#### DockerHub

- `DOCKER_USERNAME` - DockerHub username
- `DOCKER_PASSWORD` - DockerHub password or personal access token
- `DOCKER_REPOSITORY_PATH` - DockerHub organization or the username in case of personal registry
- `DOCKER_REGISTRY_URL` - No need to configure for DockerHub

> See [docker/login-action for DockerHub](https://github.com/docker/login-action#dockerhub)

#### Google Container Registry (GCR)
Create service account with permission to edit GCR or use predefined Storage Admin role.

- `DOCKER_USERNAME` - set exactly to `_json_key`
- `DOCKER_PASSWORD` - content of the service account json key file
- `DOCKER_REPOSITORY_PATH` - `<project-id>/foo`
- `DOCKER_REGISTRY_URL` - `gcr.io`

> See [docker/login-action for GCR](https://github.com/docker/login-action#google-container-registry-gcr)

#### AWS Elastic Container Registry (ECR)
Create IAM user with permission to push to ECR (or use AmazonEC2ContainerRegistryFullAccess role).

- `DOCKER_USERNAME` - access key ID
- `DOCKER_PASSWORD` - secret access key
- `DOCKER_REPOSITORY_PATH` - no need to set
- `DOCKER_REGISTRY_URL` - set to `<aws-account-number>.dkr.ecr.<region>.amazonaws.com`

> See [docker/login-action for ECR](https://github.com/docker/login-action#aws-elastic-container-registry-ecr)

#### Oracle Infrastructure Cloud Registry (OCIR)
[Create auth token](https://www.oracle.com/webfolder/technetwork/tutorials/obe/oci/registry/index.html#GetanAuthToken) for authentication.

- `DOCKER_USERNAME` - username in format `<tenancy>/<username>`
- `DOCKER_PASSWORD` - account auth token
- `DOCKER_REPOSITORY_PATH` - `<tenancy>/<registry>/foo`
- `DOCKER_REGISTRY_URL` - set to `<region>.ocir.io`

> See [docker/login-action for OCIR](https://github.com/docker/login-action#oci-oracle-cloud-infrastructure-registry-ocir)

## Feature kafka-streams documentation

- [Micronaut Kafka Streams documentation](https://micronaut-projects.github.io/micronaut-kafka/latest/guide/index.html#kafkaStream)

## Feature elasticsearch documentation

- [Micronaut Elasticsearch Driver documentation](https://micronaut-projects.github.io/micronaut-elasticsearch/latest/guide/index.html)

## Feature management documentation

- [Micronaut Management documentation](https://docs.micronaut.io/latest/guide/index.html#management)

## Feature views-handlebars documentation

- [Micronaut Handlebars views documentation](https://micronaut-projects.github.io/micronaut-views/latest/guide/index.html#handlebars)

- [https://jknack.github.io/handlebars.java/](https://jknack.github.io/handlebars.java/)

## Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)

## Feature graphql documentation

- [Micronaut GraphQL documentation](https://micronaut-projects.github.io/micronaut-graphql/latest/guide/index.html)

## Feature cache-caffeine documentation

- [Micronaut Caffeine Cache documentation](https://micronaut-projects.github.io/micronaut-cache/latest/guide/index.html)

- [https://github.com/ben-manes/caffeine](https://github.com/ben-manes/caffeine)

## Feature jdbc-hikari documentation

- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)

## Feature hibernate-validator documentation

- [Micronaut Hibernate Validator documentation](https://micronaut-projects.github.io/micronaut-hibernate-validator/latest/guide/index.html)

## Feature kafka documentation

- [Micronaut Kafka Messaging documentation](https://micronaut-projects.github.io/micronaut-kafka/latest/guide/index.html)

## Feature github-workflow-docker-registry documentation

- [https://docs.github.com/en/free-pro-team@latest/actions](https://docs.github.com/en/free-pro-team@latest/actions)

## Feature liquibase documentation

- [Micronaut Liquibase Database Migration documentation](https://micronaut-projects.github.io/micronaut-liquibase/latest/guide/index.html)

- [https://www.liquibase.org/](https://www.liquibase.org/)

## Feature aws-v2-sdk documentation

- [Micronaut AWS v2 SDK documentation](https://micronaut-projects.github.io/micronaut-aws/latest/guide/)

- [https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/welcome.html](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/welcome.html)

## Feature r2dbc documentation

- [Micronaut R2DBC documentation](https://micronaut-projects.github.io/micronaut-r2dbc/latest/guide/)

- [https://r2dbc.io](https://r2dbc.io)

## Feature security-jwt documentation

- [Micronaut Security JWT documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

## Feature security documentation

- [Micronaut Security documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

## Feature rxjava3 documentation

- [Micronaut RxJava 3 documentation](https://micronaut-projects.github.io/micronaut-rxjava3/snapshot/guide/index.html)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

