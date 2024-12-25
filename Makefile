POSTGRES_USER ?= postgres
POSTGRES_PASSWORD ?= 1111
POSTGRES_DB ?= postgres
PGADMIN_DEFAULT_EMAIL ?= postgres@gmail.com
PGADMIN_DEFAULT_PASSWORD ?= 1111
GIT_URI ?= git@your-repo.git
SSH_GIT_PRIVATE_KEY ?= your-private-key-content

up:
	export POSTGRES_USER=${POSTGRES_USER}
	export POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
	export POSTGRES_DB=${POSTGRES_DB}
	export PGADMIN_DEFAULT_EMAIL=${PGADMIN_DEFAULT_EMAIL}
	export PGADMIN_DEFAULT_PASSWORD=${PGADMIN_DEFAULT_PASSWORD}
	export GIT_URI=${GIT_URI}
	export SSH_GIT_PRIVATE_KEY=${SSH_GIT_PRIVATE_KEY}
	docker-compose up -d --build


up-env:
	@echo "Running docker compose with .env file"
	docker compose --env-file .env up -d --build

package:
	mvn clean -Dmaven.test.skip  package -e

build-env:
	make package up-env

build:
	make package up