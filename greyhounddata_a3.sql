SELECT h.ID , h.Name, h.z_name, e.Jahr
FROM Hund h, Ergebnis e
WHERE h.ID = 1 AND h.ID = e.h_ID
ORDER BY e.Jahr
ASC LIMIT 1;