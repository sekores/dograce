SELECT h.ID, h.Name, h.z_name, sum(e.kumulierte_Punktzahl)/sum(e.Anzahl_der_Rennen) AS Punktzahl
FROM Hund h, Ergebnis e
WHERE h.ID = e.h_id
GROUP BY h.ID
ORDER BY Punktzahl
ASC;