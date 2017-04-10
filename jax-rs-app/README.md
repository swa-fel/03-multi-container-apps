### Java EE application packaged with docker
In this lab you will automate steps necessary to deploy a web application together with a database using the [docker compose tool](https://docs.docker.com/compose/).
At first you will deploy the app and db manually, using the docker command line tool. Then, once you understand the tasks needed to deploy such app, you will automate things with docker compose.

#### Prepare the environment
Install `git`, `docker` and `docker-compose` on your `centos 7` machine (probably running in Google Compute Engine).
```
# install docker and git
sudo yum install -y git docker
sudo systemctl start docker

# install docker-compose
sudo yum install epel-release
sudo yum install -y python-pip
sudo pip install docker-compose
sudo yum upgrade python*

# Check whether installation succeeded
sudo docker version
docker-compose version

```

#### Manual deployment
In this section you will deploy the application and database on your centos7 machine manually. This means that you will deploy your database and wait till it's ready by yourself, you may proceed to deploying the web application only when you're sure the database is ready.
```
# download materials for this lab
git clone https://github.com/swa-fel/03-multi-container-apps

# let's get to directory containing the lab
cd 03-multi-container-apps/jax-rs-app/

# run the database, in this case postgresql
docker run --name db -it --rm -p 5432:5432 \
  -e POSTGRESQL_USER=pguser \
  -e POSTGRESQL_PASSWORD=pgpasswd \
  -e POSTGRESQL_DATABASE=pgdb  \
  registry.access.redhat.com/openshift3/postgresql-92-rhel7

# check whether you can connect to the database
docker exec -it db bash
GPASSWORD=pgpasswd psql -h localhost -U pguser pgdb
# list all databases, 'pgdb' should exist now
\l
exit

# build docker image for the web application, don't forget to check the [source code](https://github.com/swa-fel/03-multi-container-apps/tree/master/jax-rs-app/app)
docker build -t jax-rs-app .

docker run --name web --link db:db -it -p 80:8080 --rm jax-rs-app
```
If everything worked, the app will be accessible online. On the root URI you will find the wildfly welcome page and the app is on `helloworld-rs`

#### Deployment with docker-compose
In this task you will automate steps from previous task with docker-compose. Explore files in this repository, if you haven't done already, consult the documentation and ask questions/use Google if there's something you don't understand.
```
# delete artefacts from previous task
docker rm -rv `docker ps -aq`
docker rmi -f jax-rs-app

sudo docker-compose up
```
