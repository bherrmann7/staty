# Staty McStatface

## Introduction

An application for analysis of the contents of tables.

For example,

sql> create table foobar as 
    select 1 a, 'cow' b from dual union
    select 3, 'apple' from dual union
    select null, 'cow' from dual ;

yields table foobar,

<pre>
+------+-------+
| a    | b     |
+------+-------+
|    1 | cow   |
|    3 | apple |
| NULL | cow   |
+------+-------+
</pre>

What I would like to see is the shape of the data in foobar,
 
<pre>
Table:  foobar    Rows:  3

| :column_name |               :type | :notnull_count | :max |  :min | :distinct |
|--------------+---------------------+----------------+------+-------+-----------|
|            a |          BIGINT(19) |              2 |    3 |     1 |         2 |
|            b | VARCHAR(5) NOT NULL |                |  cow | apple |         2 |
</pre>

## How

This is a web application.   It asks for connection/schema/user/password/table then you push compute.
The application then connects to the database, and executes one or more sql statements to analyze
a table.  It will use a single sql statement as its first pass, then if it needs to gather 
data counts, it will execute a separate pass to gather the Data Counts.

### Step 1 column metadata

get each column name and type, and max, min, distinct, null count for each column
  

## Build and Run

### On Mysql

    $ export STATY_DATABASE_SPEC="{:dbtype \"mysql\"  :dbname \"quiz\" }"
    $ lein uberjar
    $ java -jar target/staty-0.0.1-SNAPSHOT-standalone.jar
    
### On Oracle

    $ export STATY_DATABASE_SPEC="{:classname \"oracle.jdbc.driver.OracleDriver\" :subprotocol \"oracle\" :subname \"thin:@my-oracle-host:1521/ENGR\"}"
    $ lein uberjar
    $ java -classpath target/staty-0.0.1-SNAPSHOT-standalone.jar:somepath/ojdbc7_g-12.1.0.2.jar staty.server
    

## Future

bootstrap ui
allow many tables to be queried
remember results in database (datomic? filesystem? sql?)
analysis can take a while with big tables - email result
make client clojurescript/react - update in time as analysis is being completed
    render analysis light immediately (aka just table columns/types/size ) and make deeper analysis optional
be able to analyze full schema (aka all tables in single shot)
allow deep linking to table results (for sharing urls with other people)
results should have full text option (like tables above - for emailing / bug reports)


## Getting Started

1. Start the application: `lein run`
2. Go to [localhost:8080](http://localhost:8080/) to see: `Hello World!`
3. Read your app's source code at src/staty/service.clj. Explore the docs of functions
   that define routes and responses.
4. Run your app's tests with `lein test`. Read the tests at test/staty/service_test.clj.
5. Learn more! See the [Links section below](#links).


## Configuration

To configure logging see config/logback.xml. By default, the app logs to stdout and logs/.
To learn more about configuring Logback, read its [documentation](http://logback.qos.ch/documentation.html).


## Developing your service

1. Start a new REPL: `lein repl`
2. Start your service in dev-mode: `(def dev-serv (run-dev))`
3. Connect your editor to the running REPL session.
   Re-evaluated code will be seen immediately in the service.

## Links
* [Other examples](https://github.com/pedestal/samples)

