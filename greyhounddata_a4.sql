SELECT h.Mama, h.Vater
FROM Hund h, Ergebnis e
WHERE e.h_id = h.ID
GROUP BY h.Mama, h.Vater
ORDER BY sum(e.kumulierte_Punktzahl)/sum(e.Anzahl_der_Rennen)
DESC LIMIT 1;