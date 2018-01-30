(defproject fulcro-datatable "0.1.2"
  :description "A table component for fulcro"
  :license {:name "MIT" :url "https://opensource.org/licenses/MIT"}
  :min-lein-version "2.7.0"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [fulcrologic/fulcro "2.1.2"]
                 [fulcrologic/fulcro-spec "2.0.0-beta3" :scope "test" :exclusions [fulcrologic/fulcro]]]

  :uberjar-name "fulcro_datatable.jar"

  :source-paths ["src/main"]
  :test-paths ["src/test"]
  :clean-targets ^{:protect false} ["target"])
