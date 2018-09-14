# WeatherFox

Application Android de Test Météo via l'API https://stdlib.com/@thisdavej/lib/weather/

<div>
<img src="website/static/current.jpg" data-canonical-src="website/static/current.jpg" width="200" height="400" />
<img src="website/static/forecast.jpg" data-canonical-src="website/static/forecast.jpg" width="200" height="400" />
</div>

Deux modes sont disponibles : 

- Current : Pour la météo du jour
- Forecast : Pour les prévisions des prochains jours

Internationalisation globale de l'application, internationalisation des réponses de l'API soon ...

## Installation

1. Clone du projet.
2. Télécharger et installer le JDK approprié pour votre système. Actuellement en JDK 7.
3. Installer [Android Studio](https://developer.android.com/sdk/index.html).
4. Télécharger et installer l'API 28.
4. Ouvrir le projet.
5. Lancer l'application via `Run > Run 'app'. 
6. Lancement via emulator ou appareil connecté.

## Utilisation

L'application nécessite une permission de "Location" pour géolocaliser l'utilisateur.

L'initialisation va prendre en compte si possible, la ville de l'utilisateur pour faire une première recherche.

Libre à l'utilisateur de regarder la météo / prévisions pour d'autre ville.

## Librairies

- Retrofit 2 https://github.com/square/retrofit
- Picasso https://github.com/square/picasso
- Google Play Services Location

