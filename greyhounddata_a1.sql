SELECT *
FROM Hund h, Ergebnis e
WHERE e.h_id=h.id
ORDER BY e.kumulierte_Punktzahl/e.Anzahl_der_Rennen
LIMIT 20;