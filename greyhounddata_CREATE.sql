CREATE TABLE Zwinger(
	Name VARCHAR(252) PRIMARY KEY);
CREATE TABLE Hund(
	ID SERIAL PRIMARY KEY,
	Mama VARCHAR(252),
	Geburtsland VARCHAR(5),
	Vater VARCHAR(252),
	Name VARCHAR(252),
	Aufenthaltsland VARCHAR(5),
	Geburtsjahr INT,
	Geschlecht VARCHAR(1),
	z_name VARCHAR(252),
	CONSTRAINT fk_HundZwinger FOREIGN KEY (z_name) REFERENCES Zwinger(Name));

CREATE TABLE Ergebnis(
	ID SERIAL PRIMARY KEY,
	h_id INT,
	durchs_Renndistanz INT,
	Rang INT,
	kumulierte_Punktzahl INT,
	Land VARCHAR(252),
	Jahr INT,
	Anzahl_der_Rennen INT,
	FOREIGN KEY (h_id) REFERENCES Hund (ID)
	);