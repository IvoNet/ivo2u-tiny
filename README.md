# Ivo2U Tiny Url app

My attempt to create a bit.ly like app

## Build

```bash
mvn clean package docker:build
```

## Release

* First do the build

```bash
docker tag ivonet/tiny:latest ivonet/tiny:<version>
docker push ivonet/tiny:latest
docker push ivonet/tiny:<version>
```

## arm32v6

```bash
docker tag ivonet/arm32v6-tiny:latest ivonet/arm32v6-tiny:<version>
docker push ivonet/arm32v6-tiny:latest
docker push ivonet/arm32v6-tiny:<version>
```

### command for NAS

```bash
sudo docker run -d --name ivo2u-tiny --restart always -p 32773:8080 -v /volume1/docker:/config:rw ivonet/tiny:1.1.0
```