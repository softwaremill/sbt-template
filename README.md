### Local development

1. Configure access to the Nexus repository
   - Request user account on #admin-nexus
   - Put credentials in `~/.sbt/.credentials_sml_nexus`
   ```
   realm=Sonatype Nexus Repository Manager
   host=nexus3.softwaremill.com
   user=xxxx
   password=yyyy
   ```
   - reload sbt console

2. Run dependencies as docker containers

    If your service has dependencies like other services or infrastructure (databases, Kafka), check for
a "docker" directory with `docker-compose.yml`. Run it with `docker-compose -up docker-compose.yml`

3. The service should now be runnable from sbt console (with the `run` command)