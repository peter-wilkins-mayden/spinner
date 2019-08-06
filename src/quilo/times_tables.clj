(ns quilo.times-tables)

(defn lines [modp mf rad cX cY]
  (let [coords (fn [x]
                 (let [hAngle (/ (* 2.0 Math/PI x) modp)]
                   [(+ cX (* rad (Math/sin hAngle)))
                     (- cY (* rad (Math/cos hAngle)))]))]
          (for [x (range 0 modp)]
    [(coords x) (coords (mod (* x mf) modp))])))


(lines 10 2.1 2 0 0)

