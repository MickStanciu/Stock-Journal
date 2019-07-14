#!/bin/bash

now=$(date +"%d_%m_%Y")
pg_dump -U postgres tradelog > /var/lib/postgresql/backup/tradelog_$now.bak