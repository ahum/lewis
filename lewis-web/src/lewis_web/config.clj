(ns lewis-web.config)

; TODO: Hook this into an external configuration
(defn load-config []
  "Return the application config"
  { :mongo-uri "mongodb://localhost/lewis-dev",
    :home-dir "~/.lewis"})
