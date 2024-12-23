mBuild:
	mvn clean -Dmaven.test.skip  package -e
mDocker:
	docker compose up -d --build

make:
	make mBuild mDocker