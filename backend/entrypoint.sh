#!/bin/bash

WAIT_TRIES=10

host=$1
port=$2

echo "Trying host $host:$port";

while ! nc -z $host $port;

do
    if [[ $WAIT_TRIES -le 0 ]]; then
        echo "Timeout: host is not up";
        exit 1;
    fi

    echo "Host is not up. Retrying in 5 seconds..."

    WAIT_TRIES=$((WAIT_TRIES-1))

    sleep 5
done

echo "Host was successfully found. Running the app..."

java -jar /app.jar