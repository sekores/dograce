SELECT h.ID, h.Name, h.z_name, e.Jahr, e.Land, 
(e.kumulierte_Punktzahl/e.Anzahl_der_Rennen)/durchs_Renndistanz AS durchs_Punktzahl_pro_Meter
FROM Hund h, Ergebnis e
WHERE h.ID = e.h_id
ORDER BY durchs_Punktzahl_pro_Meter
DESC LIMIT 10;