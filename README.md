# Ivo2U Tiny Url app

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

