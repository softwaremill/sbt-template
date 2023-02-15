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

4. If you want to set environment variables for local sbt runs, use `sbt-dotenv`:
   a. Add a file named `~/.sbt/1.0/plugins/sbt-dotenv.sbt` with one line:
   ```scala
   addSbtPlugin("nl.gn0s1s" % "sbt-dotenv" % "3.0.0")
   ```
   b. Configure the plugin to handle `.envrc` files instead of `.env` to make it compatible with `envrc` tool.
   To achieve this, create a file named `~/.sbt/1.0/envrc.sbt` with one line:
   ```scala
   ThisBuild  / envFileName := ".envrc"
   ```
   c. From now one you can create a `.envrc` file in ANY Scala project (globally) and set your local
      custom env variables in it, for example:
   ```
   export MY_ENV=abc
   export MY_ENV_2=qwerty
   ```
   This file is in .gitignore and should be available for your local development only.