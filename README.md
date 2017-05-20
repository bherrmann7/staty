# Staty McStatface

## Introduction

An application for analysis of the contents of tables.

For example,

sql> create table foobar as select 1 a, 'cow' b from dual union select 3, 'apple' from dual union select null, null from dual;

yields table foobar,

<pre>
+------+-------+
| a    | b     |
+------+-------+
|    1 | cow   |
|    3 | apple |
| NULL | NULL  |
+------+-------+
</pre>

What I would like to see is the shape of the data in foobar,
 
Analysis for Table FOOBAR - with 3 rows.

| Column | Datatype | Null | Min   | Max   | Distinct | Data Counts          | 
| ------ | -------- | ---- | ----- | ---   | -------- | -------------------- |
| a      | Number   | Y    | 1     | apple | 2        | 1:NULL 1:cow 1:apple |
| b      | Char     | Y    | apple | cow   | 2        | 1:NULL 1:1 1:3       |


## How

This is a web application.   It asks for connection/schema/user/password/table 


## Future

bootstrap ui
allow many tables to be queried
remember results in database (datomic? filesystem? sql?)
analysis can take a while with big tables - email result
make client clojurescript/react - react to analysis being completed
render analysis light immediately (aka just table columns/types/size ) and make deeper analysis optional
be able to analyze full schema
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

