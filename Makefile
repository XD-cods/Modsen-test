POSTGRES_USER ?= postgres
POSTGRES_PASSWORD ?= 1111
POSTGRES_DB ?= postgres
PGADMIN_DEFAULT_EMAIL ?= postgres@gmail.com
PGADMIN_DEFAULT_PASSWORD ?= 1111
GIT_URI ?= git@your-repo.git
SSH_GIT_PRIVATE_KEY ?= your-private-key-content

up:
	docker compose up -d --build \
		POSTGRES_USER=$(POSTGRES_USER) \
		POSTGRES_PASSWORD=$(POSTGRES_PASSWORD) \
		POSTGRES_DB=$(POSTGRES_DB) \
		PGADMIN_DEFAULT_EMAIL=$(PGADMIN_DEFAULT_EMAIL) \
		PGADMIN_DEFAULT_PASSWORD=$(PGADMIN_DEFAULT_PASSWORD) \
		GIT_URI=$(GIT_URI) \
		SSH_GIT_PRIVATE_KEY=$(SSH_GIT_PRIVATE_KEY)


mavenBuild:
	mvn clean -Dmaven.test.skip  package -e
dockerComposeBuild:
	docker compose up -d --build
make:
	make mavenBuild dockerComposeBuild
