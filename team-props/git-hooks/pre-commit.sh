#!/usr/bin/env bash

echo "Start running ktlint"
./gradlew ktlintCheck --daemon
status0=$?
if [[ "$status0" = 0 ]] ; then
    echo "ktlint found no problems."
else
    echo 1>&2 "ktlint found violations it could not fix. Please check ktlint report"
    exit 1
fi
