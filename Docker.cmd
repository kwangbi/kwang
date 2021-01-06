# Docker Image Build
docker build --tag kwang:1.0 .

# Docker run
docker run --rm --name kwangService -p 8090:8080 -e PROFILE=prod kwang:1.0