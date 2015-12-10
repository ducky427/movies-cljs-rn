(defproject movies-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [reagent "0.5.1" :exclusions [cljsjs/react]]
                 [re-frame "0.6.0"]]
  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]
            [lein-cljfmt "0.3.0"]]
  :clean-targets ["target/" "index.ios.js" "index.android.js"]
  :aliases {"prod-build" ^{:doc "Recompile code with prod profile."}
            ["do" "clean"
             ["with-profile" "prod" "cljsbuild" "once" "ios"]]}
  :profiles {:dev {:cljsbuild    {:builds {:ios     {:source-paths ["src" "env/dev"]
                                                     :figwheel     true
                                                     :compiler     {:output-to     "target/ios/not-used.js"
                                                                    :main          "env.ios.main"
                                                                    :output-dir    "target/ios"
                                                                    :optimizations :none}}}}}
             :prod {:cljsbuild {:builds {:ios     {:source-paths ["src" "env/prod"]
                                                   :compiler     {:output-to     "index.ios.js"
                                                                  :main          "env.ios.main"
                                                                  :static-fns    true
                                                                  :output-dir    "target/ios"
                                                                  :optimizations :simple}}}}}})
