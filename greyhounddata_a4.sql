SELECT h.Mama, h.Vater, (sum(e.kumulierte_Punktzahl)/sum(e.Anzahl_der_Rennen)) AS Kinderpunktzahl
FROM Hund h, Ergebnis e
WHERE h.ID = e.h_id
GROUP BY h.Mama, h.Vater
ORDER BY Kinderpunktzahl 
DESC LIMIT 1;