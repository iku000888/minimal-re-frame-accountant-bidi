(ns ^:figwheel-no-load reagent.dev
  (:require
    [reagent.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
