### PostgreSQL DB

There is a daily cronjob that makes a backup of tradelog database

##### Manual Backup
`pg_dump -U postgres tradelog > /var/lib/postgresql/backup/tradelog.bak`

##### Restore
`su - postgres`

`dropdb tradelog`

`createdb dbname`

`psql tradelog < /var/lib/postgresql/backup/tradelog.bak`

or

`psql -U postgres -f tradelog_15_11_2019.bak -d tradelog --set ON_ERROR_STOP=on`
