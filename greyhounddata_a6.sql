SELECT h.Mama, sum(e.kumulierte_Punktzahl)/sum(e.Anzahl_der_Rennen) AS Punkte
FROM Hund h, Ergebnis e
WHERE h.ID = e.h_ID
GROUP BY h.Mama
ORDER BY Punkte
DESC LIMIT 10;