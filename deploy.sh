#!/usr/bin/env bash

VERSION=1.2.1

if mvn clean package docker:build; then
   docker tag ivonet/tiny:latest ivonet/tiny:${VERSION}
   docker push ivonet/tiny:${VERSION}
fi