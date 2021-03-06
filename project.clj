(defproject staty "0.0.1-SNAPSHOT"
  :description "Provides statistics for a databases tables"
  :url "http://example.com/FIXME"
  ; If using an oracle driver, uncomment this next line and fix the repository reference
  ; :repositories [["myrepo" "http://maven.myserver/nexus/..."]]
  :repositories [["humedica" "http://maven.humedica.net/nexus/content/groups/hit-combined/"]]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.pedestal/pedestal.service "0.5.2"]

                 ;; Remove this line and uncomment one of the next lines to
                 ;; use Immutant or Tomcat instead of Jetty:
                 [io.pedestal/pedestal.jetty "0.5.2"]
                 ;; [io.pedestal/pedestal.immutant "0.5.2"]
                 ;; [io.pedestal/pedestal.tomcat "0.5.2"]

                 [ch.qos.logback/logback-classic "1.1.8" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.22"]
                 [org.slf4j/jcl-over-slf4j "1.7.22"]
                 [org.slf4j/log4j-over-slf4j "1.7.22"]

                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [mysql/mysql-connector-java "5.1.6"]
; also add a repository above
                 [com.oracle.jdbc/ojdbc7_g "12.1.0.2"]
                 [hiccup "1.0.5"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  ;; If you use HTTP/2 or ALPN, use the java-agent to pull in the correct alpn-boot dependency
  ;:java-agents [[org.mortbay.jetty.alpn/jetty-alpn-agent "2.0.5"]]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "staty.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.5.2"]]}
             :uberjar {:aot [staty.server]}}
  :plugins [[lein-cljfmt "0.5.6"]]
  :main ^{:skip-aot true} staty.server)

