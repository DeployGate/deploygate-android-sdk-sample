#!/usr/bin/env bash

set -eux

cd $(git rev-parse --show-toplevel)

while read path; do
    md5sum $path
done < <(find . -name "build.gradle" | sort)

while read path; do
    md5sum $path
done < <(find . -name "build.gradle.kts" | sort)

while read path; do
    md5sum $path
done < <(find . -name "gradle.properties" | sort)

while read path; do
    md5sum $path
done < <(find buildSrc -name "*.kt" | sort)