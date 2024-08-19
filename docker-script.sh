#!/bin/bash

mvn clean package -DskipTests

projects=(
    "../email-tinqin/pom.xml"
    "../admin-tinqin/pom.xml"
    "../bff-tinqin/pom.xml"
    "../authentication-api/pom.xml"
    "../comments-api-tinqin/pom.xml"
)

for project in "${projects[@]}"
do
    echo "Building project: $project"
    mvn clean package -f "$project" -DskipTests
    if [ $? -ne 0 ]; then
        echo "Build failed for $project. Exiting."
        exit 1
    fi
done

docker-compose down

docker-compose up -d --build