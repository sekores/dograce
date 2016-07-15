SELECT z_name, (sum(e.kumulierte_Punktzahl)/sum(e.Anzahl_der_Rennen)) AS Gesamtpunktzahl
FROM Hund h, Ergebnis e, Zwinger z
WHERE z.Name = h.z_name AND e.h_id = h.ID
GROUP BY z_name
ORDER BY Gesamtpunktzahl
DESC LIMIT 5;
