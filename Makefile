mavenBuild:
	mvn -q clean -Dmaven.test.skip  package -e
dockerComposeBuild:
	docker compose up -d --build
make:
	make mavenBuild dockerComposeBuild