## Sample of adoption steps for kotlin-ddd-hex-architecture

1. Edit `reference-project/pom.xml` and replace the root Maven coordinates:
   `org.pemacy.keystone` and `keystone`.
2. Edit every module `pom.xml` under `reference-project/**/pom.xml` and replace
   `org.pemacy.keystone` in parent and dependency coordinates.
3. Edit `reference-project/system-adapters/persistence-jooq/pom.xml` and replace
   the jOOQ package name `org.pemacy.keystone.jooq`.
4. Edit every Kotlin source and test file under
   `reference-project/**/src/{main,test}/kotlin/**/*.kt` and replace package
   declarations and imports starting with `org.pemacy.keystone`.
5. Edit `reference-project/system-api/boot/src/main/resources/application.yaml`
   and replace `app.name` if you keep the API adapter.
6. Edit
   `reference-project/system-client/boot/src/main/resources/application.yaml`
   and replace `app.name` if you keep the client adapter.
7. Edit
   `reference-project/system-infrastructure/autoconfigure/src/main/resources/boot-defaults.yaml`
   and replace `app.title-full`, `app.title-short`, and every
   `KEYSTONE_POSTGRES_*` environment variable name.
8. Edit
   `reference-project/system-infrastructure/dev-support/src/main/resources/boot-defaults-dev.yaml`
   and replace the local Postgres database name, usernames, and passwords.
9. Edit `reference-project/compose.yaml` and replace the Postgres database name,
   Docker image names, and datasource URLs.
10. Edit `reference-project/docs/postgres-local-setup.sql` and replace the
    Postgres database name, application user, migrator user, grants, and local
    passwords.
11. Edit `reference-project/.idea/runConfigurations/API.xml` and replace the run
    configuration name and `MAIN_CLASS_NAME` if you keep the API adapter.
12. Edit `reference-project/.idea/runConfigurations/Client.xml` and replace the
    run configuration name and `MAIN_CLASS_NAME` if you keep the client adapter.
