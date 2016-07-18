SELECT h.Vater, count(h.ID) AS  Anzahl_der_Kinder
FROM Hund h, Ergebnis e
WHERE e.h_id = h.ID
GROUP BY h.Vater 
ORDER BY Anzahl_der_Kinder
DESC LIMIT 10;