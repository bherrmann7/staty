# Staty McStatface

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

||Column||Datatype||Contraints||MAX||MIN|| Distinct || Data Counts||
|b|char| |cow|apple| 2 | 1:cow 1:apple |


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

