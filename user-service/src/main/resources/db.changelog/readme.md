- Run: mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data to generate data change
-> db.changelog.mysql.sql

- Run: mvn liquibase:generateChangeLog to generate schema

compare with baseline and copy change to new file and add to liquibase config
commit new baseline

