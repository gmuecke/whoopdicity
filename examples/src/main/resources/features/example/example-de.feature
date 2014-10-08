#language: de
@Example
Funktionalität: Kurse suchen
  Kurse sollten nach Thema gesucht werden koennen
  Suchergebnisse sollten den Kurscode enthalten

Szenario: Suche nach Thema
    Angenommen es gibt 240 Kurse die nicht das Theman "Biologie" haben
    Und es gibt 2 Kurse, A001 und B205, die jeweils "Biologie" zum Thema haben
    Wenn ich nach "Biologie" suche
    Dann sehe ich die Folgenden Kurse
      | Kurs Code |
      | A001      |
      | B205      |