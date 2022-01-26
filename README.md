# ROTODO - Projet To Do List

## Android Natif - Kotlin

Fonctionnalités:

- Données stockées en local via **Room** (cache pour mode hors-ligne)
- Communication avec **Firebase Firestore** afin d'acceder aux données en lignes
- Gestion du **Up Button**  (dans MainActivity.kt)
- Menu d'option accessible seulement sur les fragments pertinents (ici, seulement le MainFragment) 
- Theme d'application persionnalisable (light/dark theme selectionnable dans le fragment ThemesFragment accessible via le **menu d'options**)  
- Un **repository** permettant la gestion des modes avec et sans connexion
- Implémentation d'un **swipe left** pour la suppression des ToDos
- Implémentation d'un **custom adapter** avec evenement personnalisé pour les ToDos
- Utilisation des **fragments** pour la navigation entre les différents écrans
- Application de **l'architecture MVVM** via les différents ViewModels pour chaque fragment
- Utilisation du **databinding**, fonctionnalité recommandée par google pour la liaison entre les
  layouts html et la logique
- Icon d'application custom
- Utilisation des ConstraintLayout (Bonne pratique Google pour les Layouts complexes)