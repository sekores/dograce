SELECT h.ID, h.Name, h.z_name, e.Jahr, e.Land, 
(e.kumulierte_Punktzahl/e.Anzahl_der_Rennen) AS durchs_Punktzahl_pro_Rennen
FROM Hund h, Ergebnis e
WHERE h.ID = e.h_id
ORDER BY durchs_Punktzahl_pro_Rennen
DESC LIMIT 10;