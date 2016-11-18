#!/usr/bin/env bash

docker run --rm -p 8080:8080 -v $(pwd)/target:/config ivonet/tiny