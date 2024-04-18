#!/bin/bash

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

until curl -sSf -o /dev/null -w "%{http_code}" "$host:$port" | grep "200" &> /dev/null; do
  >&2 echo "Keycloak is unavailable - sleeping"
  sleep 1
done

>&2 echo "Keycloak is up - executing command"
exec $cmd