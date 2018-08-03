# Sybit Coding Camp 2018

Sybit Coding Camp 2018: 30.07.2018 - 03.08.2018

In den 5 Tagen haben 10 Schüler zusammen mit den Auszubldenden der Firma Sybit GmbH
auf diesem Repository gearbeitet und das webbasierte Spiel "Schiffe versenken" 
programmiert.

Das Spiel wird automatisch von einem hausinternen Jenkins-Server kompiliert und installiert.

## [▶ Jetzt spielen](http://coding-camp.sybit.de/battleship)


---


# Entwicklung

## Voraussetzungen

### Software installieren

Folgende Software muss installiert sein:

- Java 8
- git Client (z.B. [Sourcetree](https://www.sourcetreeapp.com/))
- Entwicklungsumgebung (z.B. [Netbeans](https://netbeans.org/), [Eclipse](https://www.eclipse.org/), [InelliJ](https://www.jetbrains.com/idea/), ...)
- [node.js](https://nodejs.org) (mit npm)



## Projekt einrichten

### Schritt 1: Repository clonen
Repository klonen oder als zip-Archiv herunterladen und entpacken.


### Schritt 2: Konfiguration

anhand des Skriptes auf dem USB-Stick.

## Projekt kompilieren

In der Eingabeaufforderung (Commandline) im Projektordner öffnen den Befehl 
eingeben: 

``` gradlew bootRun ```

Um auch die JavaScript-Dateien immer wieder zu aktualisieren, gibt es den Befehl:

```gradlew runParallelTasks ```

Dieser übersetzt den gesamten Quellcode und startet den Webserver.

## Applikation starten

Im Browser die Adresse [http://localhost:8080](http://localhost:8080) aufrufen.

Wird jetzt am Quellcode etwas geändert, so wird das automatisch erkannt und 
die Anwendung neu kompiliert und gestartet.

---

Das Coding Camp der Sybit GmbH findet in Kooperation mit der Stadtjugendpflege 
im Rahmen des Sommerferienprogramms der Stadt Radolfzell statt.