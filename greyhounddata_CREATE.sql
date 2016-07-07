CREATE TABLE Hund(
	Hund_ID SERIAL PRIMARY KEY,
	Land VARCHAR(252),
	Jahr VARCHAR(252),
	Jahresrang INT,
	name VARCHAR(252),
	geschlecht VARCHAR(1),
	vater VARCHAR(252),
	mutter VARCHAR(252),
	anzahl_Rennen INT,
	kumulierte_Punkte INT,
	durch_Renndistanz_in_m INT,
	nur_name VARCHAR(252),
	geburtsland VARCHAR(5),
	geburtsjahr VARCHAR(4),
	aufenthaltsland VARCHAR(5),
	zwinger VARCHAR(252));

CREATE TABLE Rennen(
	Hund_ID SERIAL references Hund(Hund_ID),
	durch_Rennpunkte INT);
CREATE TABLE Zwinger(
	name VARCHAR(252) PRIMARY KEY);