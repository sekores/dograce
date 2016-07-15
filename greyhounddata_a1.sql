SELECT h.ID, h.Name, z_name
FROM Hund h, Ergebnis e
WHERE h.ID = e.h_id
GROUP BY h.ID 
ORDER BY (sum(e.kumulierte_Punktzahl)/sum(e.Anzahl_der_Rennen))
DESC LIMIT 20;